package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.ArcadeDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Chassis extends Subsystem {

    private TalonSRX lMaster, lFrontSlave, lBackSlave, rMaster, rFrontSlave, rBackSlave;
    
    private double deadband = 0.02;
    
    public Chassis() {
    	lMaster = new TalonSRX(RobotMap.leftTopDriveMotor);
    	lFrontSlave = new TalonSRX(RobotMap.leftFrontDriveMotor);
    	lFrontSlave.follow(lMaster);
    	lBackSlave = new TalonSRX(RobotMap.leftBackDriveMotor);
    	lBackSlave.follow(lMaster);
    	
    	rMaster = new TalonSRX(RobotMap.rightTopDriveMotor);
    	rFrontSlave = new TalonSRX(RobotMap.rightFrontDriveMotor);
    	rFrontSlave.follow(rMaster);
    	rBackSlave = new TalonSRX(RobotMap.rightBackDriveMotor);
    	rBackSlave.follow(rMaster);
    }

    public void initDefaultCommand() {
    	setDefaultCommand(new ArcadeDrive());
    }
    
    public void setMotorValues(double left, double right) {
    	lMaster.set(ControlMode.PercentOutput, left);
    	rMaster.set(ControlMode.PercentOutput, right);
    }
    
    public void directionalDrive(double throttle, double turn) {
    	throttle = Math.copySign(throttle * throttle, throttle);
        turn = Math.copySign(turn * turn, turn);
        
        throttle = limit(throttle);
        throttle = applyDeadband(throttle, deadband);

        turn = limit(turn);
        turn = applyDeadband(turn, deadband);
        
        double leftMotorOutput;
        double rightMotorOutput;

        double maxInput = Math.copySign(Math.max(Math.abs(throttle), Math.abs(turn)), throttle);

        if (throttle >= 0.0) {
          // First quadrant, else second quadrant
          if (turn >= 0.0) {
            leftMotorOutput = maxInput;
            rightMotorOutput = throttle - turn;
          } else {
            leftMotorOutput = throttle + turn;
            rightMotorOutput = maxInput;
          }
        } else {
          // Third quadrant, else fourth quadrant
          if (turn >= 0.0) {
            leftMotorOutput = throttle + turn;
            rightMotorOutput = maxInput;
          } else {
            leftMotorOutput = maxInput;
            rightMotorOutput = throttle - turn;
          }
        }
        
        setMotorValues(limit(leftMotorOutput), limit(rightMotorOutput));
    }
    
    private double limit(double value) {
    	return Math.max(Math.min(1, value), -1);
    }
    
    private double applyDeadband(double value, double db) {
    	if(Math.abs(value) > db) {
    		if(value > 0.0) {
    			return (value - db) / (1.0 - db);
    		} else {
    			return (value + db) / (1.0 - db);
    		}
    	} else {
    		return 0.0;
    	}
    }
}


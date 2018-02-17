package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.DriveWrist;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Wrist extends Subsystem {
	
	TalonSRX wristMotor;

	public Wrist() {
		wristMotor = new TalonSRX(RobotMap.wristMotor);
		wristMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWrist());
    }
    
    public void setOutput(double output) {
    	wristMotor.set(ControlMode.PercentOutput, output);
    }
    
    public void PIDWrist(double setpoint) {
    	wristMotor.set(ControlMode.Position, setpoint);
    }
    
    public void setPID(int slot, double p, double i, double d) {
    	wristMotor.config_kP(slot, p, 0);
    	wristMotor.config_kI(slot, i, 0);
    	wristMotor.config_kD(slot, d, 0);
    }
    
    public boolean onTarget(double setpoint, double tolerance) {
    	return Math.abs(setpoint - wristMotor.getSelectedSensorPosition(0)) < tolerance;
    }
}


package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.DriveWrist;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Wrist extends Subsystem {
	
	TalonSRX wristMotor;
	//private DigitalInput wristUp;
	//private DigitalInput wristDown;
	

	public Wrist() {
		wristMotor = new TalonSRX(RobotMap.wristMotor);
		wristMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		
		//wristUp = new DigitalInput(RobotMap.wristUpId);
		//wristDown = new DigitalInput(RobotMap.wristDownId);
		
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWrist());
    }
    
    public void setOutput(double output) {
    	/*if (wristDown.get() && output<=0)
    		output=0;
    	else if(wristUp.get() && output>=0)
    		output=0;*/
    	wristMotor.set(ControlMode.PercentOutput, output);
    }
    
    /*public boolean isWristDown() {
    	return wristDown.get();
    }
    
    public boolean isWristUp() {
    	return wristUp.get();
    }*/
    
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


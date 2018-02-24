package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.CommandBase;
import org.usfirst.frc.team1339.robot.commands.WristToggle;
import org.usfirst.frc.team1339.utils.WristConversions;

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
	private DigitalInput wristUp;
	private DigitalInput wristDown;
	public int toggle = -1;
	

	public Wrist() {
		toggle = 0;
		wristMotor = new TalonSRX(RobotMap.wristMotor);
		wristMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		wristMotor.setInverted(true);
		
		wristUp = new DigitalInput(RobotMap.wristUpId);
		wristDown = new DigitalInput(RobotMap.wristDownId);
	}
	
    public void initDefaultCommand() {
        //setDefaultCommand(new WristToggle());
    }
    
    public void publishWebServer() {
    	CommandBase.server.valueDisplay.putValue("Wrist Degrees",
    			WristConversions.clicksToDegrees(wristMotor.getSelectedSensorPosition(0)));
//    	CommandBase.server.valueDisplay.putValue("Wrist Up Limit", value);
    	CommandBase.server.valueDisplay.putValue("wrist up limit", wristUp.get());
    	CommandBase.server.valueDisplay.putValue("wrist down limit", wristDown.get());
    	
    }
    
    public void setOutput(double output) {
    	if (isWristDown())
    		output = Math.max(0, output);
    	else if(isWristUp()) {
    		output = Math.min(0, output);
    		wristMotor.setSelectedSensorPosition(0, 0, 10);
    	}
    	wristMotor.set(ControlMode.PercentOutput, output);
    }
    
    public boolean isWristDown() {
    	return !wristDown.get();
    }
    
    public boolean isWristUp() {
    	return !wristUp.get();
    }
    
    public boolean isWristGoingUp() {
    	return isWristUp() && wristMotor.getMotorOutputPercent()>0;
    }
    public boolean isWristGoingDown() {
    	return isWristDown() && wristMotor.getMotorOutputPercent()<0;
    }
    public double getOutput() {return wristMotor.getMotorOutputPercent();}
    
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


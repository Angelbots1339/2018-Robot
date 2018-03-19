package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.OI;
import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.CommandBase;
import org.usfirst.frc.team1339.robot.commands.DriveWrist;
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
	public int toggle;
	boolean wristUpPressed = false;
	public boolean OTP = false;
	

	public Wrist() {
		toggle = 0;
		wristMotor = new TalonSRX(RobotMap.wristMotor);
		wristMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		wristMotor.setSensorPhase(true);
		
		wristUp = new DigitalInput(RobotMap.wristUpId);
		wristDown = new DigitalInput(RobotMap.wristDownId);
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWrist());
    }
    
    public void publishWebServer() {
    	CommandBase.server.valueDisplay.putValue("Wrist Degrees",
    			WristConversions.clicksToDegrees(wristMotor.getSelectedSensorPosition(0)));
    	CommandBase.server.valueDisplay.putValue("wrist up limit", wristUp.get());
    	CommandBase.server.valueDisplay.putValue("wrist down limit", wristDown.get());
    	CommandBase.server.valueDisplay.putValue("Wrist Going Down ", isWristGoingDown());
    	CommandBase.server.valueDisplay.putValue("Wrist Going Up ", isWristGoingUp());
    	
    }
    
    public void resetEncoder() {
    	wristMotor.setSelectedSensorPosition(0, 0, 0);
    }
    
    public void setOutput(double output) {
    	if(Math.abs(output) < 0.1) output = 0;
    	if(isWristUp() && !wristUpPressed) {
    		toggle = -1;
    		wristUpPressed = true;
    	}
    	if(wristUpPressed && !isWristUp()) wristUpPressed = false;
    	
    	if (isWristDown()) {
    		output = Math.max(0, output);
   			wristMotor.setSelectedSensorPosition(0, 0, 10);
    	}
    	else if(isWristUp()) {
    		output = Math.min(0, output);
    	}
    	
    	wristMotor.set(ControlMode.PercentOutput, output);
    }
    
    public boolean isWristDown() {
    	return !wristDown.get();
    }
    
    public boolean isWristUp() {
    	return !wristUp.get();
    }
    public boolean isOTPOk() {
    	return WristConversions.clicksToDegrees(wristMotor.getSelectedSensorPosition(0))<40;
    }
    
    public boolean isWristGoingUp() {
    	return isWristUp() && wristMotor.getMotorOutputPercent() > 0;
    }
    
    public boolean isWristGoingDown() {
    	return isWristDown() && wristMotor.getMotorOutputPercent() < 0;
    }
    
    public double getOutput() {return wristMotor.getMotorOutputPercent();}
    
    public void PIDWrist(double setpoint) {
    	if(isWristUp() && !wristUpPressed) {
    		toggle = -1;
    		wristUpPressed = true;
    	}
    	if(wristUpPressed && !isWristUp()) wristUpPressed = false;
    	if(isWristGoingUp() || isWristGoingDown()) {
    		toggle = -1;
    		setOutput(0);
    	}
    	else wristMotor.set(ControlMode.Position, setpoint);
    	System.out.println("SetPoint: " + WristConversions.clicksToDegrees((int)setpoint));
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


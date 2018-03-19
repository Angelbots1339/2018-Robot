package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;

/**
 *
 */
public class WristToggle extends CommandBase {

	boolean OTP = false;
	boolean pressed = false;
	
    public WristToggle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	wrist.setPID(0, RobotMap.wristKp, RobotMap.wristKi, RobotMap.wristKd);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if ((oi.getXButton().get() && !pressed && OTP) || oi.getAButton().get() || oi.getBButton().get()) {
    		wrist.toggle = 0;
    		OTP = false;
    		pressed = true;
    	}
    	if(oi.getXButton().get() && !pressed && !OTP && CommandBase.elevator.canGoOTP()) {
    		wrist.toggle = 0;
    		OTP = true;
    		pressed = true;
    	}
    	
    	if(pressed && !oi.getXButton().get()) {
    		pressed = false;
    	}
    	
    	if(wrist.toggle == -1) wrist.setOutput(0);
    	else { 
    		if(!OTP) {
    			wrist.PIDWrist(RobotMap.wristHorizontal);
    			wrist.OTP = false;
    		}
	    	else { wrist.PIDWrist(RobotMap.wristOTP);//CHANGE TO OTP POSITION
	    		wrist.OTP = true;
	    	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(oi.getOperatorStick().getRawAxis(RobotMap.xboxLeftYAxis)) > 0.2;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

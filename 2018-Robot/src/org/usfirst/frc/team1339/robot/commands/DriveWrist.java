package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.utils.WristConversions;

import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class DriveWrist extends CommandBase {
	boolean resetInitialized = false;
	boolean toggle = false;
	boolean pressed = false;
	
    public DriveWrist() {
        requires(wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//double output = oi.getXboxStick().getRawAxis(RobotMap.xboxRightTrigger) -
    	//		oi.getXboxStick().getRawAxis(RobotMap.xboxLeftTrigger);
    	//double output = (oi.getRightBumper().get() ? 0.25 : 0) - (oi.getLeftBumper().get() ? 0.25 : 0);
    	double output=0;
    	if (oi.getXboxStick().getPOV()<110 && oi.getXboxStick().getPOV()>70) {
    		resetInitialized = true;
    	}
    	
    	
    	if(resetInitialized) {
    		if(!wrist.isWristUp()) {
    			wrist.setOutput(.25);
    		}
    		else {
    			wrist.toggle = 0;
    			Scheduler.getInstance().add(new PIDWrist(-82));
    			resetInitialized = false;
    		}
    	}
    	else if (oi.getXButton().get() && !pressed) {
    		wrist.toggle = 0;
    		toggle = !toggle;
    		pressed = true;
    		if(toggle) Scheduler.getInstance().add(new PIDWrist(-82));
    		else Scheduler.getInstance().add(new PIDWrist(-30));
    	}
    	if(pressed && !oi.getXButton().get()) {
    		pressed = false;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	wrist.toggle = 1;
    	wrist.setOutput(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	wrist.toggle = 1;
    	wrist.setOutput(0);
    }
}

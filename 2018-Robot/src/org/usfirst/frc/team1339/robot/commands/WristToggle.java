package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.utils.WristConversions;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WristToggle extends CommandBase {

	boolean toggle = false;
	boolean pressed = false;
	
    public WristToggle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (oi.getXButton().get() && !pressed) {
    		wrist.toggle = 0;
    		toggle = !toggle;
    		pressed = true;
    	}
    	if(pressed && !oi.getXButton().get()) {
    		pressed = false;
    	}
    	if(wrist.toggle == -1) wrist.setOutput(0);
    	else if(toggle) wrist.PIDWrist(WristConversions.degreesToClicks(-82));
    	else wrist.PIDWrist(WristConversions.degreesToClicks(-30));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

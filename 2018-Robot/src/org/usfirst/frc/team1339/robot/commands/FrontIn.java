package org.usfirst.frc.team1339.robot.commands;

/**
 *
 */
public class FrontIn extends CommandBase {

    public FrontIn() {
    	requires(pinchers);
    	setTimeout(0.05);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	pinchers.setFrontOut(false);
    	pinchers.setFrontIn(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	pinchers.setFrontIn(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	pinchers.setFrontIn(false);
    }
}

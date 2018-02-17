package org.usfirst.frc.team1339.robot.commands;

/**
 *
 */
public class FrontOut extends CommandBase {

    public FrontOut() {
    	requires(pinchers);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	pinchers.setFrontIn(false);
    	pinchers.setFrontOut(true);
    	setTimeout(0.05);
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
    	pinchers.setFrontOut(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	pinchers.setFrontOut(false);
    }
}

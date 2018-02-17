package org.usfirst.frc.team1339.robot.commands;

/**
 *
 */
public class BackOut extends CommandBase {

    public BackOut() {
    	requires(pinchers);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	pinchers.setBackIn(false);
    	pinchers.setBackOut(true);
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
    	pinchers.setBackOut(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	pinchers.setBackOut(false);
    }
}

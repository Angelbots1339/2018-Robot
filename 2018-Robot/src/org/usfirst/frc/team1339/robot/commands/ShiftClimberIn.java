package org.usfirst.frc.team1339.robot.commands;

/**
 *
 */
public class ShiftClimberIn extends CommandBase {

    public ShiftClimberIn() {
    	requires(elevator);
    	setTimeout(0.05);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.setOutSol(false);
    	elevator.setInSol(true);
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
    	elevator.setInSol(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	elevator.setInSol(false);
    }
}

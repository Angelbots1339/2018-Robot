package org.usfirst.frc.team1339.robot.commands;

/**
 *
 */
public class DriveElevator extends CommandBase {
	
    public DriveElevator() {
    	requires(elevator);
    }
    
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	elevator.setElevtor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	elevator.setElevtor(0);
    }
}

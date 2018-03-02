package org.usfirst.frc.team1339.robot.commands;

/**
 *
 */
public class DriveClimber extends CommandBase {
	
    public DriveClimber() {
    	requires(elevator);
    }
    
    protected void initialize() {
    	elevator.setElevMotorsBrakeMode(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	boolean climb = oi.getOperatorAButton().get();
    	elevator.setClimber(climb ? -1 : 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	elevator.setClimber(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	elevator.setClimber(0);
    }
}

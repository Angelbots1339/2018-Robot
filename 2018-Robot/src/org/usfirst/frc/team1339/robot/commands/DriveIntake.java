package org.usfirst.frc.team1339.robot.commands;

/**
 *
 */
public class DriveIntake extends CommandBase {

    public DriveIntake() {
        // Use requires() here to declare subsystem dependencies
        requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double output = (oi.getRightBumper().get() ? 0.6 : 0) - (oi.getLeftBumper().get() ? 0.5 : 0);
    	intake.setIntake(output);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.setIntake(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	intake.setIntake(0);
    }
}

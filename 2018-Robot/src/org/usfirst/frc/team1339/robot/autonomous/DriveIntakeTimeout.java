package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.commands.CommandBase;

/**
 *
 */
public class DriveIntakeTimeout extends CommandBase {

	double speed;
	
    public DriveIntakeTimeout(double speed, double time) {
        // Use requires() here to declare subsystem dependencies
        requires(intake);
        
        this.speed = speed;
        setTimeout(time);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	intake.setIntake(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
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

package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.commands.CommandBase;

/**
 *
 */
public class DriveForwardTimeout extends CommandBase {

	private double speed;
	
    public DriveForwardTimeout(double speed, double time) {
    	requires(chassis);
    	setTimeout(time);
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	chassis.setMotorValues(speed, speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	chassis.setMotorValues(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	chassis.setMotorValues(0, 0);
    }
}

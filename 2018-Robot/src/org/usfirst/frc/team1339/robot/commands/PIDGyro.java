package org.usfirst.frc.team1339.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PIDGyro extends CommandBase {
	
	double setpoint, tolerance;

    public PIDGyro(double setpoint, double tolerance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(chassis);
    	this.setpoint = setpoint;
    	this.tolerance = tolerance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	chassis.gyroPID.setSetpoint(setpoint + chassis.getGyroAngle());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	chassis.gyroPID();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return oi.getBButton().get(); //chassis.gyroPID.onTarget(2);
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

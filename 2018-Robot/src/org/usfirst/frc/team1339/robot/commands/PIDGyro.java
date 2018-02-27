package org.usfirst.frc.team1339.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PIDGyro extends CommandBase {
	
	double setpoint, tolerance, counter = 0;
	private boolean increased=false;

    public PIDGyro(double setpoint, double tolerance, double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(chassis);
    	//chassis.resetGyro();
    	this.setpoint = setpoint;
    	this.tolerance = tolerance;
    	setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//chassis.resetGyro();
    	chassis.gyroPID.setSetpoint(setpoint + chassis.getGyroAngle());
    	increased = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//if(chassis.gyroPID.onTarget(tolerance)) {
    	//	counter++;
    	//}
    	//else counter = 0;
    	chassis.gyroPID();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (chassis.getGyroRate() < 0.3 && chassis.gyroPID.onTarget(tolerance)) || isTimedOut();
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

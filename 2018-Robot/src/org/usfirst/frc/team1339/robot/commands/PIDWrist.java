package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;

/**
 *
 */
public class PIDWrist extends CommandBase {
	
	double tolerance = -1;
	double setpoint;

    public PIDWrist(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(wrist);
    	this.setpoint = setpoint;
    }
    
    public PIDWrist(double setpoint, double tolerance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(wrist);
    	this.setpoint = setpoint;
    	this.tolerance = tolerance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	wrist.setPID(0, RobotMap.wristKp, RobotMap.wristKi, RobotMap.wristKd);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	wrist.PIDWrist(setpoint);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (tolerance == -1) {
	        return wrist.isWristGoingDown() ||
	        		wrist.isWristGoingUp();
    	} else {
	        return wrist.isWristGoingDown() ||
	        		wrist.isWristGoingUp() ||
	        		wrist.onTarget(setpoint, tolerance);
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	wrist.setOutput(0);
    	//System.out.println("Wrist ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	wrist.setOutput(0);
    	//System.out.println("Wrist interrupted");
    }
}

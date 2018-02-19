package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.utils.WristConversions;

/**
 *
 */
public class PIDWrist extends CommandBase {
	
	double setpoint;

    public PIDWrist(double degrees) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(wrist);
    	setpoint = WristConversions.degreesToClicks(degrees);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	wrist.setPID(0, RobotMap.wristKp, RobotMap.wristKi, RobotMap.wristKd);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//System.out.println(wrist.getOutput());
    	wrist.PIDWrist(setpoint);
    	//System.out.println("PIDWrist executing peasants");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return wrist.isWristGoingDown() || wrist.isWristGoingUp() || oi.getMenuButton().get();
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

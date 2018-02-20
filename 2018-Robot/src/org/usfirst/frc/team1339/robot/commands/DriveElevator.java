package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;

/**
 *
 */
public class DriveElevator extends CommandBase {
	
	private boolean pidInitialized = false;
	private double pidSetpoint = 0.0;
	
    public DriveElevator() {
    	requires(elevator);

    }
    
    protected void initialize() {
    	elevator.setElevMotorsBrakeMode(true);
    	pidSetpoint = elevator.getPositionClicks();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double output = -oi.getMadCatzStick().getRawAxis(RobotMap.operatorYAxis);
    	if(!pidInitialized && Math.abs(output) < 0.1) {
    		pidInitialized = true;
    		pidSetpoint = elevator.getPositionClicks();
    	}
    	
    	if(Math.abs(output) > 0.1) pidInitialized = false;
    	if(pidInitialized) {
    		elevator.PIDElevator(pidSetpoint);
    	} else {
    		elevator.setElevator(output);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	elevator.setElevator(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	elevator.setElevator(0);
    }
}

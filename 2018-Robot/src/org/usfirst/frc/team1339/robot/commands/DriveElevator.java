package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;

/**
 *
 */
public class DriveElevator extends CommandBase {
	
	boolean manual = false;
	boolean pidRunning = false;
	double pidSetpoint = 0;
	
    public DriveElevator() {
    	requires(elevator);
    }
    
    protected void initialize() {
    	manual = true;
    	elevator.setPID(0, RobotMap.elevatorKp, RobotMap.elevatorKi, RobotMap.elevatorKd);
    	elevator.setElevMotorsBrakeMode(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double output = -oi.getOperatorStick().getRawAxis(RobotMap.xboxRightYAxis);
    	
    	if(oi.getOperatorBButton().get()) {
    		manual = true;
    	} else if(oi.getOperatorXButton().get()) {
    		manual = false;
    	}
    	
    	if(Math.abs(output) < 0.1) {
    		if(!pidRunning) {
        		pidRunning = true;
        		pidSetpoint = elevator.getPositionClicks();
    		}
    	} else {
    		pidRunning = false;
    	}
    	
    	if(pidRunning && !manual) {
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

package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.utils.ElevatorConversions;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class DriveElevator extends CommandBase {
	
	private boolean pidInitialized = false, driveHeightInitialized = false, timedout = false;
	private double pidSetpoint = 0.0, time = 0.0;
	private final double timeout = 0.5;
	
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
    	if(!driveHeightInitialized && elevator.getPosition() < RobotMap.driveHeight && 
    			intake.hazBox(RobotMap.squeezeThreshold) && Math.abs(output) < 0.1) {
    		driveHeightInitialized = true;
    		pidInitialized = true;
    		pidSetpoint = ElevatorConversions.cmsToClicks(RobotMap.driveHeight);
    		time = Timer.getFPGATimestamp();
    		timedout = false;
    	}
    	
    	if(!pidInitialized && Math.abs(output) < 0.1) {
    		pidInitialized = true;
    		pidSetpoint = elevator.getPositionClicks();
    	}
    	
    	if(Math.abs(output) > 0.1) {
    		driveHeightInitialized = false;
    		pidInitialized = false;
    	}

    	if(pidInitialized) {
    		if(driveHeightInitialized && !timedout) {
    			if(Timer.getFPGATimestamp() > time + timeout) timedout = true;
    		} else {
    			elevator.PIDElevator(pidSetpoint);
    		}
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
    	driveHeightInitialized = false;
    	pidInitialized = false;
    	timedout = false;
    	elevator.setElevator(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	driveHeightInitialized = false;
    	pidInitialized = false;
    	timedout = false;
    	elevator.setElevator(0);
    }
}

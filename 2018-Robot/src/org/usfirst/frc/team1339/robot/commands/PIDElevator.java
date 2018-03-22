package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.utils.ElevatorConversions;

/**
 *
 */
public class PIDElevator extends CommandBase {

	double setpoint;
	boolean driveHeightInitialized = false, timedout = false;
	boolean toggle1=false;
	boolean toggle2=false;
	int tposition = 0;
	boolean goingDown=false;
	//private double driveHeightSetpoint = 0.0, time = 0.0;
	//private final double timeout = 0.5;
	
    public PIDElevator(int setPointInCm) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(elevator);
		elevator.position = setPointInCm;
		elevator.state = 0;
    	this.setpoint = ElevatorConversions.cmsToClicks(setPointInCm);
    	tposition = setPointInCm;

    	//2301
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Math.abs(ElevatorConversions.clicksToCMs((int)(this.setpoint))-elevator.getHeight()) > 25) goingDown=true;
    	else goingDown=false;
    	elevator.state = 0;
    	elevator.setPID(0, RobotMap.elevatorKp, RobotMap.elevatorKi, RobotMap.elevatorKd);
    	elevator.position = tposition;
    	driveHeightInitialized = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (oi.getRightBumper().get() && !toggle1) {
    		toggle2 = false;
    		toggle1 = true;
    		elevator.state = Math.min(elevator.state + 1, 1);
    	}
    	
    	if (oi.getXboxStick().getRawAxis(RobotMap.xboxRightTrigger) > 0.5 && !toggle2) {
    		toggle1 = false;
    		toggle2 = true;
    		elevator.state = Math.max(elevator.state - 1, -1);
    	}
    	
    	if(elevator.position == RobotMap.posSwitch) {
    		driveHeightInitialized = false;
    		if(elevator.state == -1) setpoint = RobotMap.lowSwitch;
    		else if (elevator.state == 1) setpoint = RobotMap.highSwitch;
    		else setpoint = RobotMap.posSwitch;
    	}
    	else if (elevator.position == RobotMap.posScale) {
    		driveHeightInitialized = false;
    		if(elevator.state == -1) setpoint = RobotMap.lowScale;
    		else if (elevator.state == 1) setpoint = RobotMap.highScale;
    		else setpoint = RobotMap.posScale;
    	}
    	if (!oi.getRightBumper().get()) {
    		toggle1 = false;
    	}
    	
    	if(oi.getXboxStick().getRawAxis(RobotMap.xboxRightTrigger) < 0.5) {
    		toggle2 = false;
    	}
    	
    	/*if(!driveHeightInitialized && elevator.getPositionClicks() < RobotMap.driveHeight
    			&& intake.hazBox(RobotMap.squeezeThreshold)) {
    		driveHeightInitialized = true;
    		driveHeightSetpoint = RobotMap.driveHeight;
    		time = Timer.getFPGATimestamp();
    		timedout = false;
    	}
    	
    	if(driveHeightInitialized && !intake.hazBox(RobotMap.squeezeThreshold)) {
    		driveHeightInitialized = false;
    	}
    	
    	if(driveHeightInitialized && elevator.position == 0) {
    		if (!timedout) {
    			if(Timer.getFPGATimestamp() > time + timeout) timedout = true;
    		}
    		else {
    			elevator.PIDElevator(driveHeightSetpoint);
    		}
    	}
    	else {
    		elevator.PIDElevator(setpoint);
    	}*/
    	if(!goingDown || CommandBase.wrist.isOTPOk()) elevator.PIDElevator(setpoint);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (this.setpoint == 0) {
    		return elevator.isCarriageDown() || 
            		Math.abs(oi.getOperatorStick().getRawAxis(RobotMap.xboxRightYAxis)) > 0.1 ||
            		elevator.onTarget(0, RobotMap.tol2Cm);
    	}
        return  elevator.isCarriageGoingDown() ||
        		elevator.isElevatorGoingUp() ||
        		Math.abs(oi.getOperatorStick().getRawAxis(RobotMap.xboxRightYAxis)) > 0.1;
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

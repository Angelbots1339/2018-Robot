package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;

/**
 *
 */
public class WristToggle extends CommandBase {

	boolean toggle = false;
	boolean pressed = false;
	
    public WristToggle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	wrist.setPID(0, RobotMap.wristKp, RobotMap.wristKi, RobotMap.wristKd);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (oi.getXButton().get() && !pressed) {
    		wrist.toggle = 0;
    		toggle = !toggle;
    		pressed = true;
    	}
    	if(pressed && !oi.getXButton().get()) {
    		pressed = false;
    	}
    	if(wrist.toggle == -1) wrist.setOutput(0);
    	else if(toggle) wrist.PIDWrist(RobotMap.wristHorizontal);
    	else wrist.PIDWrist(RobotMap.wristFortyFive);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(oi.getOperatorStick().getRawAxis(RobotMap.xboxLeftYAxis)) > 0.1;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

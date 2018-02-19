package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;

/**
 *
 */
public class DriveWrist extends CommandBase {
	
    public DriveWrist() {
        requires(wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double output = oi.getXboxStick().getRawAxis(RobotMap.xboxRightTrigger) -
    			oi.getXboxStick().getRawAxis(RobotMap.xboxLeftTrigger);
    	//double output = (oi.getRightBumper().get() ? 0.25 : 0) - (oi.getLeftBumper().get() ? 0.25 : 0);
    	
    	
    	wrist.setOutput(output);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	wrist.setOutput(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	wrist.setOutput(0);
    }
}

package org.usfirst.frc.team1339.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 */
public class CenterToSwitch extends CommandBase {
	
	String name = "null";
	
    public CenterToSwitch() {
    	requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	String data = DriverStation.getInstance().getGameSpecificMessage();
    	if(data.length() > 0) {
    		if(data.charAt(0) == 'L') {
    			name = "LeftCenterToSwitch";
    		} else if(data.charAt(0) == 'R') {
    			name = "RightCenterToSwitch";
    		}
    	}
    	
    	if(!name.equals("null"))
    		chassis.initializeMotionProfile(name);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	chassis.executeMotionProfile();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return chassis.isTrajectoryFinished() ||
        		oi.getLeftBumper().get() ||
        		name.equals("null");
    }

    // Called once after isFinished returns true
    protected void end() {
    	chassis.cancelMotionProfile();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	chassis.cancelMotionProfile();
    }
}

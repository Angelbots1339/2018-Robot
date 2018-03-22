package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.CommandBase;
import org.usfirst.frc.team1339.utils.ParseFiles;

/**
 *
 */
public class CenterToSwitch extends CommandBase {
	
	ParseFiles path = null;
	
    public CenterToSwitch() {
    	requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(RobotMap.gameMessage.length() > 0) {
    		if(RobotMap.gameMessage.charAt(0) == 'L') {
    			path = RobotMap.Center_To_Left_Switch;
    		} else if(RobotMap.gameMessage.charAt(0) == 'R') {
    			path = RobotMap.Center_To_Right_Switch;
    		}
    	}
    	
    	if(path!=null)
    		chassis.initializeMotionProfile(path);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	chassis.executeMotionProfile();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return chassis.isTrajectoryFinished() ||
        		oi.getLeftBumper().get() ||
        		path==null;
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

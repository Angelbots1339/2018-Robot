package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.CommandBase;
import org.usfirst.frc.team1339.utils.ParseFiles;

/**
 *
 */
public class RightToScale extends CommandBase {
	
	ParseFiles path = null;
	boolean passive;
	
    public RightToScale(boolean passive) {
    	requires(chassis);
    	this.passive = passive;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(RobotMap.gameMessage.length() > 0) {
    		if(RobotMap.gameMessage.charAt(1) == 'R') {
    			path = RobotMap.Right_To_Scale;
    		} else if(RobotMap.gameMessage.charAt(1) == 'L') {
    			if(passive) {
    				path = RobotMap.Drive_Forward;
    			} else {
        			//path = RobotMap.Drive_Forward; WE HAVE TO DO THE RIGHT OOP
    			}
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
    	 return (chassis.isTrajectoryFinished() && path!=RobotMap.Drive_Forward) ||
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

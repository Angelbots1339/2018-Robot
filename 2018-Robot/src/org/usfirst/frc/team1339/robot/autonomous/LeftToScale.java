package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.CommandBase;

/**
 *
 */
public class LeftToScale extends CommandBase {
	
	String name = "null";
	boolean passive;
	
    public LeftToScale(boolean passive) {
    	requires(chassis);
    	this.passive = passive;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(RobotMap.gameMessage.length() > 0) {
    		if(RobotMap.gameMessage.charAt(1) == 'L') {
    			name = "LeftToScale";
    		} else if(RobotMap.gameMessage.charAt(1) == 'R') {
    			if(passive) {
    				name = "DriveForward";
    			} else {
        			name = "LeftToOppositeScale";
    			}
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
        return (chassis.isTrajectoryFinished() && !name.equals("DriveForward")) ||
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

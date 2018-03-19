package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.utils.ParseFiles;

/**
 *
 */
public class ExecuteProfile extends CommandBase {
	
	ParseFiles file;
	
    public ExecuteProfile(ParseFiles file) {
    	requires(chassis);
    	this.file = file;
    }
    
    protected void initialize() {
    	chassis.initializeMotionProfile(file);
    }
    
    
    protected void execute() {
    	chassis.executeMotionProfile();
    }
    
    protected boolean isFinished() {
        return chassis.isTrajectoryFinished() || oi.getBButton().get();
    }
    
    protected void end() {
    	chassis.cancelMotionProfile();
    }
    
    protected void interrupted() {
    	chassis.cancelMotionProfile();
    }
}

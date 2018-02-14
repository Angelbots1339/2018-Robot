package org.usfirst.frc.team1339.robot.commands;

/**
 *
 */
public class ExecuteProfile extends CommandBase {
	
	String name;
	
    public ExecuteProfile(String filename) {
    	requires(chassis);
    	name = filename;
    }
    
    protected void initialize() {
    	chassis.initializeMotionProfile(name);
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

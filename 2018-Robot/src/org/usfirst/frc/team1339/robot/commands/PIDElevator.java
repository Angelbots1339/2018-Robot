package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.utils.ElevatorConversions;

/**
 *
 */
public class PIDElevator extends CommandBase {

	double setpoint;
	boolean autoMode;
	
    public PIDElevator(double setpointInCm) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(elevator);
    	this.setpoint = ElevatorConversions.cmsToClicks(setpointInCm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.setPID(0, RobotMap.elevatorKp, RobotMap.elevatorKi, RobotMap.elevatorKd);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	elevator.PIDElevator(setpoint);
    	//System.out.println("Executing ElevetorPID");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return  elevator.isCarriageGoingDown() ||
        		elevator.isElevatorGoingUp() ||
        		oi.getOneButton().get();
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

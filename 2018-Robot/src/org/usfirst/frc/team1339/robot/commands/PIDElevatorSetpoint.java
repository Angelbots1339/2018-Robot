package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PIDElevatorSetpoint extends CommandBase {

	double setpoint;
	
    public PIDElevatorSetpoint(double setpoint, double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(elevator);
    	this.setpoint = setpoint;
    	setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println(setpoint);
    	elevator.setPID(0, RobotMap.elevatorKp, RobotMap.elevatorKi, RobotMap.elevatorKd);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	elevator.PIDElevator(setpoint);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return elevator.isCarriageGoingDown() || elevator.isElevatorGoingUp() || isTimedOut();
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

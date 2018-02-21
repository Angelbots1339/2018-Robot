package org.usfirst.frc.team1339.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetElevator extends CommandBase {

    public ResetElevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	elevator.setElevator(-0.25);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return elevator.isCarriageDown();
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

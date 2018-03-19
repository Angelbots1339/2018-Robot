package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.commands.groups.ClawMed;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleSqueeze extends CommandBase {

	private boolean isSqueezed;
    public ToggleSqueeze() {
        requires(pinchers);
        isSqueezed = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(isSqueezed) {
    		System.out.println("Unsqueezing");
    		(new BackIn()).execute();
    		(new FrontOut()).execute();
    	} else {
    		System.out.println("Squeezing");
    		(new BackIn()).execute();
    		(new FrontIn()).execute();
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

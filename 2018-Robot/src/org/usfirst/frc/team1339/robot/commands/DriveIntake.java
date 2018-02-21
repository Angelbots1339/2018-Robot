package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.groups.ClawClosed;
import org.usfirst.frc.team1339.robot.commands.groups.ClawMed;
import org.usfirst.frc.team1339.robot.subsystems.Pinchers;
import org.usfirst.frc.team1339.robot.subsystems.Pinchers.ClawPosition;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveIntake extends CommandBase {

	private boolean squeezeInitialized = false;
	Command closed;
	Command med;
	
    public DriveIntake() {
        // Use requires() here to declare subsystem dependencies
        requires(intake);
        closed = new ClawClosed();
        med = new ClawMed();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!squeezeInitialized && intake.hazBox(RobotMap.squeezeThreshold)
    			&& Pinchers.getClawPosition() != ClawPosition.CLOSED) {
    		closed.start();
    		squeezeInitialized = true;
    	}
    	if(squeezeInitialized && !intake.hazBox(RobotMap.squeezeThreshold) 
    			&& Pinchers.getClawPosition() != ClawPosition.MED) {
    		med.start();
    		squeezeInitialized = false;
    	}
    	double output = (oi.getRightBumper().get() ? 0.8 : 0) - (oi.getLeftBumper().get() ? 0.5 : 0);
    	intake.setIntake(output);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.setIntake(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	intake.setIntake(0);
    }
}

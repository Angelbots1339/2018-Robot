package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 */
public class ArcadeDrive extends CommandBase {

	double throttle, turn;
	Joystick stick;
	
    public ArcadeDrive() {
        requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	stick = oi.getXboxStick();
		throttle = stick.getRawAxis(RobotMap.xboxLeftYAxis);
		turn = stick.getRawAxis(RobotMap.xboxRightXAxis);
		
		chassis.directionalDrive(throttle, turn);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	chassis.directionalDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	chassis.directionalDrive(0, 0);
    }
}

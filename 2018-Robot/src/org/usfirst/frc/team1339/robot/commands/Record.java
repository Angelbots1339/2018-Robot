package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 */
public class Record extends CommandBase {
	
	Joystick stick;
	String name;

    public Record(String filename) {
        requires(chassis);
        name = filename;
    }

    protected void initialize() {
    	chassis.initLog(name);
    }

    protected void execute() {
    	stick = oi.getXboxStick();
    	double throttle = -stick.getRawAxis(RobotMap.xboxLeftYAxis);
    	double turn = -stick.getRawAxis(RobotMap.xboxRightXAxis);
    	
    	chassis.recordArcadeDrive(throttle, turn);
    }

    protected boolean isFinished() {
        return oi.getBButton().get();
    }

    protected void end() {
    	chassis.closeLog();
    }

    protected void interrupted() {
    	chassis.closeLog();
    }
}

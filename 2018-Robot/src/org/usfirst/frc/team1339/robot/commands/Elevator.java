package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Elevator extends CommandBase {
	
    public Elevator() {
    	requires(elevatorsystem);
    }
    boolean moving;
    boolean up;
    
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		if(oi.getXboxStick().getRawAxis(RobotMap.xboxRightBumper) == 1) {
			up = true;
			moving = true;
		}else if(oi.getXboxStick().getRawAxis(RobotMap.xboxLeftBumper) == 1) {
			up = false;
			moving = true;
		}
		//if(sensorinput){
		//	moving = false;
		//}	
		if(moving) {
			elevatorsystem.setElevtor(up?1:-1);
		}else {
			elevatorsystem.setElevtor(0);
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

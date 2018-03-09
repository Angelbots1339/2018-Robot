package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class RightSideAuto extends Command {
	
	int mode = 0; //0: Drive forward, 1: Scale only, 2:Two cube, 3: Opposite scale
	
    public RightSideAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(RobotMap.gameMessage.length() > 0) {
    		if(RobotMap.gameMessage.charAt(1) == 'L') {
    			mode = 3;
    		} else if(RobotMap.gameMessage.charAt(1) == 'R') {
    			if(RobotMap.gameMessage.charAt(0) == 'L') {
    				mode = 1;
    			} else if(RobotMap.gameMessage.charAt(0) == 'R') {
    				mode = 2;
    			}
    		}
    	}
    	
    	if(mode == 0) {
    		Scheduler.getInstance().add(new DriveForwardTimeout(0.5, 2.5));
    	} else if(mode == 1) {
    		Scheduler.getInstance().add(new RightToScale(false));
    	} else if(mode == 2) {
    		Scheduler.getInstance().add(new RightToScale(false));
    	} else if(mode == 3) {
    		Scheduler.getInstance().add(new RightToOppositeScaleAuto());
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

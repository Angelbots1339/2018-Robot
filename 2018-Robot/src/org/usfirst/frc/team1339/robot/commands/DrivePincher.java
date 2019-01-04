package org.usfirst.frc.team1339.robot.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class DrivePincher extends CommandBase {
	
	
	private boolean pressed=false;
	private boolean aPressed=false;
	private boolean changed = true;
  
    public DrivePincher() {
    	requires(pinchers);
    }
    
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		
    	if(oi.getRightStickButton().get()) {
    		openClaw();
    		changed = true;
    	} else {
    		if(oi.getLeftStickButton().get() && !pressed) {
    			pinchers.toggle = !pinchers.toggle;
    			pressed = true;
    			changed = true;
    		}
    		if (oi.getAButton().get() && !aPressed) {
    			pinchers.toggle = true;
    			aPressed = true;
    			changed = true;
    		}
    		if (pinchers.toggle && changed) {
    			medClaw();
    			changed = false;
    		}
    		else if(!pinchers.toggle && changed){
    			closeClaw();
    			changed = false;
    		}
    	}
    	if (!oi.getLeftStickButton().get()) pressed = false;
    	if (!oi.getAButton().get()) aPressed = false;
    }
    
    private void openClaw() {
    	pinchers.setBackIn(false);
    	pinchers.setBackOut(true);
    	pinchers.setFrontIn(false);
    	pinchers.setFrontOut(true);
    	resetClaw();
    }
    private void closeClaw() {
    	pinchers.setBackOut(false);
    	pinchers.setBackIn(true);
    	pinchers.setFrontOut(false);
    	pinchers.setFrontIn(true);
    	resetClaw();
    }
    private void medClaw() {
    	pinchers.setBackOut(false);
    	pinchers.setBackIn(true);
    	pinchers.setFrontIn(false);
    	pinchers.setFrontOut(true);
    	resetClaw();
    }
    private void resetClaw() {
    	Timer.delay(0.1);
    	pinchers.setBackOut(false);
    	pinchers.setBackIn(false);
    	pinchers.setFrontIn(false);
    	pinchers.setFrontOut(false);
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

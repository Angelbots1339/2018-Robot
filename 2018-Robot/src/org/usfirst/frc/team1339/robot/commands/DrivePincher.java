package org.usfirst.frc.team1339.robot.commands;

/**
 *
 */
public class DrivePincher extends CommandBase {
	
	private boolean toggle=false;
	private boolean pressed=false;
	
    public DrivePincher() {
    	requires(pinchers);
    }
    
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(oi.getRightStickButton().get()) {
    		openClaw();
    	} else {
    		if(oi.getLeftStickButton().get() && !pressed) {
    			toggle = !toggle;
    			pressed = true;
    		}
    		if (toggle) medClaw();
    		else closeClaw();
    	}
    	if (!oi.getLeftStickButton().get()) pressed = false;
    		
    }
    
    private void openClaw() {
    	pinchers.setBackIn(false);
    	pinchers.setBackOut(true);
    	
    	pinchers.setFrontIn(false);
    	pinchers.setFrontOut(true);
    }
    private void closeClaw() {
    	pinchers.setBackOut(false);
    	pinchers.setBackIn(true);
    	pinchers.setFrontOut(false);
    	pinchers.setFrontIn(true);
    }
    private void medClaw() {
    	pinchers.setBackOut(false);
    	pinchers.setBackIn(true);
    	pinchers.setFrontIn(false);
    	pinchers.setFrontOut(true);
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

package org.usfirst.frc.team1339.robot.commands;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.subsystems.Elevator;
import org.usfirst.frc.team1339.utils.ElevatorConversions;

/**
 *
 */
public class PIDElevator extends CommandBase {

	double setpoint;
	boolean autoMode;
	boolean toggle1=false;
	boolean toggle2=false;
	int tposition = 0;
	
    public PIDElevator(int setPointInCm) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(elevator);
    	
		elevator.position = setPointInCm;
		elevator.state = 0;
    	this.setpoint = ElevatorConversions.cmsToClicks(setPointInCm);
    	tposition = setPointInCm;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	elevator.setPID(0, RobotMap.elevatorKp, RobotMap.elevatorKi, RobotMap.elevatorKd);
    	elevator.position = tposition;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//System.out.println("Executing ElevetorPID");
    	if (oi.getRightBumper().get() && !toggle1) {
    		toggle2 = false;
    		toggle1 = true;
    		System.out.println(elevator.position);
    		elevator.state = Math.min(elevator.state+1, 1);
    	}
    	
    	if (oi.getXboxStick().getRawAxis(RobotMap.xboxRightTrigger)>0.5 && !toggle2) {
    		toggle1 = false;
    		toggle2 = true;
    		elevator.state = Math.max(elevator.state-1, -1);
    	}
    	
    	if(elevator.position == RobotMap.posSwitch) {
    		if(elevator.state == -1) setpoint = RobotMap.lowSwitch;
    		else if (elevator.state == 1) setpoint = RobotMap.highSwitch;
    		else setpoint = RobotMap.posSwitch;
    	}
    	else if (elevator.position == RobotMap.posScale) {
    		if(elevator.state == -1) setpoint = RobotMap.lowScale;
    		else if (elevator.state == 1) setpoint = RobotMap.highScale;
    		else setpoint = RobotMap.posScale;
    	}
    	//else setpoint=0;
    	/*case RobotMap.posSwitch:
    		if (elevator.state == -1) setpoint = RobotMap.lowSwitch;
    		else if (elevator.state == 1) setpoint = RobotMap.highSwitch;
    		else setpoint = RobotMap.posSwitch;
    		break;
    	case RobotMap.posScale:
    		if (elevator.state == -1) setpoint = RobotMap.lowScale;
    		else if (elevator.state == 1) setpoint = RobotMap.highScale;
    		break;
    		
    	}
    	*/
    	if (!oi.getRightBumper().get()) {
    		toggle1 = false;
    	}
    	
    	if(oi.getXboxStick().getRawAxis(RobotMap.xboxRightTrigger)<0.5) {
    		toggle2 = false;
    	}
    	
    	System.out.println(setpoint);
    	
    	elevator.PIDElevator(setpoint);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return  elevator.isCarriageGoingDown() ||
        		elevator.isElevatorGoingUp() ||
        		oi.getOneButton().get();
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

package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pinchers extends Subsystem {
	
	Solenoid backIn, backOut, frontIn, frontOut;
	
	public static enum ClawPosition{
		OPEN,
		CLOSED,
		MED,
	}
	
	private static ClawPosition position = ClawPosition.MED;
	
	public Pinchers() {
		backIn = new Solenoid(RobotMap.backInSol);
		backOut = new Solenoid(RobotMap.backOutSol);
		frontIn = new Solenoid(RobotMap.frontInSol);
		frontOut = new Solenoid(RobotMap.frontOutSol);
	}

    public void initDefaultCommand() {
    }
    
    public void setBackIn(boolean value) {
    	backIn.set(value);
    }
    public void setBackOut(boolean value) {
    	backOut.set(value);
    }
    public void setFrontIn(boolean value) {
    	frontIn.set(value);
    }
    public void setFrontOut(boolean value) {
    	frontOut.set(value);
    }
    
    public static void setClawPosition(ClawPosition num) { position = num; }
    
    public static ClawPosition getClawPosition() { return position; }

}


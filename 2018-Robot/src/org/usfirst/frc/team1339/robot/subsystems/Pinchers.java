package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.DrivePincher;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pinchers extends Subsystem {
	
	public boolean toggle=true;
	Solenoid backIn, backOut, frontIn, frontOut;
	
	public Pinchers() {
		backIn = new Solenoid(RobotMap.backInSol);
		backOut = new Solenoid(RobotMap.backOutSol);
		frontIn = new Solenoid(RobotMap.frontInSol);
		frontOut = new Solenoid(RobotMap.frontOutSol);
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new DrivePincher());
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
}


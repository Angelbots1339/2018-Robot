package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.DriveClimber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

	TalonSRX climbMotor;
	
	public Climber() {
		climbMotor = new TalonSRX(RobotMap.climbMotor);
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new DriveClimber());
    }
    
    public void setOutput(double output) {
    	climbMotor.set(ControlMode.PercentOutput, output);
    }
}


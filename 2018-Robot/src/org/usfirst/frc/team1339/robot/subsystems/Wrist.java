package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.DriveWrist;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Wrist extends Subsystem {
	
	TalonSRX wristMotor;

	public Wrist() {
		wristMotor = new TalonSRX(RobotMap.wristMotor);
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWrist());
    }
    
    public void setOutput(double output) {
    	wristMotor.set(ControlMode.PercentOutput, output);
    }
}


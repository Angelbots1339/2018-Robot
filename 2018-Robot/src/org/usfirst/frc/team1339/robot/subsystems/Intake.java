package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.DriveIntake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	private TalonSRX lMotor;
	private TalonSRX rMotor;
	
	public Intake() {
		lMotor = new TalonSRX(RobotMap.leftIntakeMotor);
		rMotor = new TalonSRX(RobotMap.rightIntakeMotor);
		rMotor.setInverted(true);
	}

    public void initDefaultCommand() {
        setDefaultCommand(new DriveIntake());
    }
    
    public void setIntake(double output) {
    	lMotor.set(ControlMode.PercentOutput, output);
    	rMotor.set(ControlMode.PercentOutput, output);
    }
}


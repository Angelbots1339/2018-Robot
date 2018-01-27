package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSystem extends Subsystem {

	private TalonSRX intakeL;
	private TalonSRX intakeR;
	
	public IntakeSystem() {
		intakeL = new TalonSRX(RobotMap.leftIntakeMotor);
		intakeR = new TalonSRX(RobotMap.rightIntakeMotor);
	}

    public void initDefaultCommand() {
        setDefaultCommand(new Intake());
    }
    
    public void setIntake(double d) {
    	intakeL.set(ControlMode.PercentOutput, d);
    	intakeR.set(ControlMode.PercentOutput, d);
    }
}


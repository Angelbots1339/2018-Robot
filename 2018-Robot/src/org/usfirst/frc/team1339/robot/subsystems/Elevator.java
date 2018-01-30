package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.DriveElevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {

	private TalonSRX elevatorMaster;
	private TalonSRX elevatorSlave;
	
	public Elevator() {
		elevatorMaster = new TalonSRX(RobotMap.leftElevatorMotor);
		
		elevatorSlave = new TalonSRX(RobotMap.rightElevatorMotor);
		elevatorSlave.setInverted(true);
		elevatorSlave.follow(elevatorMaster);
	}

    public void initDefaultCommand() {
        setDefaultCommand(new DriveElevator());
    }
    public void setElevtor(double output) {
    	elevatorMaster.set(ControlMode.PercentOutput, output);
    }
}


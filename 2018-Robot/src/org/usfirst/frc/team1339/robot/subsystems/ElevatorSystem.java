package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.Elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ElevatorSystem extends Subsystem {

	private TalonSRX elevator;
	
	public ElevatorSystem() {
		elevator = new TalonSRX(RobotMap.elevatorMotor);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new Elevator());
    }
    public void setElevtor(double d) {
    	elevator.set(ControlMode.PercentOutput, d);
    }
}


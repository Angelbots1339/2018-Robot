package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.DriveElevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
		elevatorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		
		elevatorSlave = new TalonSRX(RobotMap.rightElevatorMotor);
		elevatorSlave.setInverted(true);
		elevatorSlave.follow(elevatorMaster);
	}

    public void initDefaultCommand() {
        setDefaultCommand(new DriveElevator());
    }
    
    public void setElevator(double output) {
    	elevatorMaster.set(ControlMode.PercentOutput, output);
    }
    
    public void PIDElevator(double setpoint) {
    	elevatorMaster.set(ControlMode.Position, setpoint);
    }
    
    public void setPID(int slot, double p, double i, double d) {
    	elevatorMaster.config_kP(slot, p, 0);
    	elevatorMaster.config_kI(slot, i, 0);
    	elevatorMaster.config_kD(slot, d, 0);
    }
    
    public boolean onTarget(double setpoint, double tolerance) {
    	return Math.abs(setpoint - elevatorMaster.getSelectedSensorPosition(0)) < tolerance;
    }
}


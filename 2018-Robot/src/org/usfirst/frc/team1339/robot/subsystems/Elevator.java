package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.CommandBase;
import org.usfirst.frc.team1339.robot.commands.DriveElevator;
import org.usfirst.frc.team1339.utils.ElevatorConversions;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator extends Subsystem {
	/*
	public static enum ElevatorState{
		LOW,
		MID,
		HIGH
	}
	public static enum ElevatorPosition{
		ZERO,
		SWITCH,
		SCALE
	}
	*/
	private TalonSRX elevatorMaster;
	private TalonSRX elevatorSlave;
	private TalonSRX climber;

	private Solenoid out, in;

	private DigitalInput elevatorUp;
	private DigitalInput carriageDown;
	
	public int position; //0 = bottom(0) ; 1 = switch(65) ; 2 = scale(150)
	public int state;

	public Elevator() {
		elevatorMaster = new TalonSRX(RobotMap.topElevatorMotor);
		elevatorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		elevatorMaster.setSensorPhase(true);

		elevatorSlave = new TalonSRX(RobotMap.bottomElevatorMotor);
		elevatorSlave.follow(elevatorMaster);
		elevatorSlave.setInverted(true); //Only for final

		climber = new TalonSRX(RobotMap.climbMotor);

		in = new Solenoid(RobotMap.climbInSol);
		out = new Solenoid(RobotMap.climbOutSol);

		elevatorUp = new DigitalInput(RobotMap.elevatorUpId);
		carriageDown = new DigitalInput(RobotMap.carriageDownId);
		
		position = 0;
		state = 0;
	}

	public void initDefaultCommand() {
		//setDefaultCommand(new DriveElevator());
	}

	public void setElevMotorsBrakeMode(boolean brake) {
		elevatorMaster.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
		elevatorSlave.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
	}

	public void publishSmartDashboard() {
		SmartDashboard.putNumber("Elevator Enc",
				ElevatorConversions.clicksToCMs(elevatorMaster.getSelectedSensorPosition(0)));
		CommandBase.server.valueDisplay.putValue("Elevator Enc",
				ElevatorConversions.clicksToCMs(elevatorMaster.getSelectedSensorPosition(0)));
		CommandBase.server.valueDisplay.putValue("Carriage down", carriageDown.get());
		CommandBase.server.valueDisplay.putValue("Carriage Up", elevatorUp.get());
		//System.out.println(ElevatorConversions.clicksToCMs(elevatorMaster.getSelectedSensorPosition(0)));
		CommandBase.server.valueDisplay.putValue("Elevator Position", position);
		CommandBase.server.valueDisplay.putValue("Elevator State", state);
	}

	public void setElevator(double output) {
		if (isCarriageDown()) {
			output = Math.max(0, output);
			elevatorMaster.setSelectedSensorPosition(0, 0, 10);
		}
		else if(isElevatorUp()) {
			output = Math.min(0, output);
		}

		if(Math.abs(output) < 0.1) output = 0;
		elevatorMaster.set(ControlMode.PercentOutput, output);
	}

	public void setClimber(double output) {
		output = Math.min(0, output);
		climber.set(ControlMode.PercentOutput, output);
	}

	public boolean isElevatorUp() {
		return !elevatorUp.get();
	}

	public boolean isCarriageDown() {
		return !carriageDown.get();
	}
	   
    public boolean isElevatorGoingUp() {
    	return isElevatorUp() && elevatorMaster.getMotorOutputPercent()>0;
    }
    public boolean isCarriageGoingDown() {
    	return isCarriageDown() && elevatorMaster.getMotorOutputPercent()<0;
    }

	public void PIDElevator(double setpoint) {
		if(isElevatorGoingUp() || isCarriageGoingDown()) setElevator(0);
		else elevatorMaster.set(ControlMode.Position, setpoint);
	}

	public void setPID(int slot, double p, double i, double d) {
		elevatorMaster.config_kP(slot, p, 0);
		elevatorMaster.config_kI(slot, i, 0);
		elevatorMaster.config_kD(slot, d, 0);
	}

	public boolean onTarget(double setpoint, double tolerance) {
		return Math.abs(setpoint - elevatorMaster.getSelectedSensorPosition(0)) < tolerance;
	}

	public void setOutSol(boolean val) {
		out.set(val);
	}

	public void setInSol(boolean val) {
		in.set(val);
	}
	
	public double getPosition() { return ElevatorConversions.clicksToCMs(elevatorMaster.getSelectedSensorPosition(0));}
	
	public double getPositionClicks() { return elevatorMaster.getSelectedSensorPosition(0);}
}


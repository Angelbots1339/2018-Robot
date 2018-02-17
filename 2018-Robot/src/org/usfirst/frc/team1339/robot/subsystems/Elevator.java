package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.DriveElevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator extends Subsystem {

	private TalonSRX elevatorMaster;
	private TalonSRX elevatorSlave;
	
	//private DigitalInput elevatorUp;
	//private DigitalInput carriageDown;
	
	public Elevator() {
		elevatorMaster = new TalonSRX(RobotMap.topElevatorMotor);
		elevatorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		
		elevatorSlave = new TalonSRX(RobotMap.bottomElevatorMotor);
		elevatorSlave.follow(elevatorMaster);
		
		//elevatorUp = new DigitalInput(RobotMap.elevatorUpId);
		//carriageDown = new DigitalInput(RobotMap.carriageDownId);
	}

    public void initDefaultCommand() {
        setDefaultCommand(new DriveElevator());
    }
    
    public void publishSmartDashboard() {
    	SmartDashboard.putNumber("Elev Top Current", elevatorMaster.getOutputCurrent());
    	SmartDashboard.putNumber("Elev Bottom Motor", elevatorSlave.getOutputCurrent());
    }
    
    public void setElevator(double output) {
    	/*if (carriageDown.get() && output<=0)
    		output=0;
    	else if(elevatorUp.get() && output>=0)
    		output=0;*/
    	elevatorMaster.set(ControlMode.PercentOutput, output);
    }
    
    /*public boolean isElevatorUp() {
    	return elevatorUp.get();
    }
    
    public boolean isCarriageDown() {
    	return carriageDown.get();
    }*/
    
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


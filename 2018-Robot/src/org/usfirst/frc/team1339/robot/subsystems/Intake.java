package org.usfirst.frc.team1339.robot.subsystems;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.CommandBase;
import org.usfirst.frc.team1339.robot.commands.DriveIntake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {

	private TalonSRX lMotor;
	private TalonSRX rMotor;
	
	//private Ultrasonic ultra;
	private double threshold = 40;
	
	public Intake() {
		lMotor = new TalonSRX(RobotMap.leftIntakeMotor);
		rMotor = new TalonSRX(RobotMap.rightIntakeMotor);
		rMotor.setInverted(true);
		
		//ultra = new Ultrasonic(RobotMap.ultraOut, RobotMap.ultraIn);
	}

    public void initDefaultCommand() {
        setDefaultCommand(new DriveIntake());
    }
    
    public void publishWebServer() {
    	//CommandBase.server.valueDisplay.putValue("Ultrasonic", ultra.getRangeMM());
    	//CommandBase.server.valueDisplay.putValue("Haz box", hazBox());
    }
    
    public void setIntake(double output) {
    	lMotor.set(ControlMode.PercentOutput, output);
    	rMotor.set(ControlMode.PercentOutput, output);
    }
    
    /*public boolean hazBox() {
    	return ultra.getRangeMM() < threshold;
    }*/
}


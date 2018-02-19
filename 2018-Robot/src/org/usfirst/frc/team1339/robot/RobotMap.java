/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1339.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//Controller Ports
	public final static int xboxPort = 0;
	public final static int operatorPort = 1;

	//XBox Axis
	public final static int xboxLeftXAxis = 0;
	public final static int xboxLeftYAxis = 1;
	public final static int xboxLeftTrigger = 2;
	public final static int xboxRightTrigger = 3;
	public final static int xboxRightXAxis = 4;
	public final static int xboxRightYAxis = 5;

	//XBox Buttons
	public final static int xboxAButton = 1;
	public final static int xboxBButton = 2;
	public final static int xboxXButton = 3;
	public final static int xboxYButton = 4;
	public final static int xboxLeftBumper = 5;
	public final static int xboxRightBumper = 6;
	public final static int xboxViewButton = 7;
	public final static int xboxMenuButton = 8;
	public final static int xboxLeftStickButton = 9;
	public final static int xboxRightStickButton = 10;

	//Mad Catz Axis
	public final static int operatorXAxis = 0;
	public final static int operatorYAxis = 1;
	public final static int operatorZAxis = 2;
	public final static int operatorZRotate = 3;

	//Mad Catz Buttons
	public final static int operatorOneButton = 1;
	public final static int operatorTwoButton = 2;
	public final static int operatorThreeButton = 3;
	public final static int operatorFourButton = 4;
	public final static int operatorFiveButton = 5;
	public final static int operatorSixButton = 6;
	public final static int operatorSevenButton = 7;
	
	//Drive Motors
	public final static int rightTopDriveMotor = 1;
	public final static int rightFrontDriveMotor = 2;
	public final static int rightBackDriveMotor = 3;
	public final static int leftTopDriveMotor = 12;
	public final static int leftFrontDriveMotor = 11;
	public final static int leftBackDriveMotor = 10;
	
	//Intake Motors
	public final static int rightIntakeMotor = 4;
	public final static int leftIntakeMotor = 5;
	
	//Elevator Motors
	public final static int topElevatorMotor = 7;
	public final static int bottomElevatorMotor = 8;
	
	//Wrist Motor
	public final static int wristMotor = 6;
	
	//Climbing Motors
	public final static int climbMotor = 9;
	
	//TalonSRX Motion Profiling PIDF
	public static final double talonKf = 1.4; //1.77 1.4
	public static final double talonKp = 2.0; //0.4 2.5 
	public static final double talonKi = 0.0;
	public static final double talonKd = 10;
	
	//TalonSRX Position Closed Loop PID on Elevator
	public static final double elevatorKp = 0.1;
	public static final double elevatorKi = 0.0;
	public static final double elevatorKd = 6;
	
	//TalonSRX Position Closed Loop PID on Wrist
	public static final double wristKp = 0.025;
	public static final double wristKi = 0.0;
	public static final double wristKd = 0.1;

	//Solenoids
	public final static int frontInSol = 1;//1 
	public final static int frontOutSol = 0;//0
	public final static int backInSol = 3;//3
	public final static int backOutSol = 2;//2
	public final static int climbOutSol = 5;
	public final static int climbInSol = 6;
	
	//Sensors
	public final static int gyroId = 0;
	
	public final static double kThrottleLimiter = 0.5;
	public final static double kTurnLimiter = 0.8;
	
	public final static int wristUpId = 0;
	public final static int wristDownId = 2;
	public final static int elevatorUpId = 5; // We don't know
	public final static int carriageDownId = 1;
	public final static int ultrasonicInput = 3;
	public final static int ultrasonicOutput = 4;
	
	//Gyro PID Values
	public final static double gyroKp = 0.0075;
	public final static double gyroKi = 0.0000;
	public final static double gyroKd = 0.04;
	
	//Gyro error
	public final static double gyroKe = 1.03;
	
	
}

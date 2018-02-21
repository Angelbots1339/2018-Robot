/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1339.robot;

import org.usfirst.frc.team1339.utils.ElevatorConversions;

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
	public final static int operatorEightButton = 8;
	public final static int operatorNineButton = 9;
	public final static int operatorTenButton = 10;
	public final static int operatorElevenButton = 11;
	public final static int operatorTwelveButton = 12;
	
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
	public static final double elevatorKd = 10;
	
	//TalonSRX Position Closed Loop PID on Wrist
	public static final double wristKp = 0.04;
	public static final double wristKi = 0.0;
	public static final double wristKd = 0.1;

	//LEDs
	public final static int rightLEDStripId = 1;
	public final static int leftLEDStripId = 2;
	
	//Solenoids
	public final static int frontInSol = 1;
	public final static int frontOutSol = 0;
	public final static int backInSol = 3;
	public final static int backOutSol = 2;
	public final static int climbOutSol = 5;
	public final static int climbInSol = 7;
	
	//Sensors
	public final static int gyroId = 0;
	
	public final static int wristUpId = 0;
	public final static int wristDownId = 2;
	public final static int elevatorUpId = 5; // We don't know
	public final static int carriageDownId = 1;
	public final static int ultrasonicInput = 3;
	public final static int ultrasonicOutput = 4;
	
	//Gyro PID Values
	public final static double gyroKp = 0.0075;
	public final static double gyroKi = 0.0000;
	public final static double gyroKd = 0.0;
	
	//Gyro error
	public final static double gyroKe = 1.03;

	//Ramp up
	public final static double[] lowerLimitRamp = {0.0, 0.0};
	public final static double[] upperLimitRamp = {180.0, 2.0};
  
	//Chassis limiter
	public final static double kThrottleLimiter = 0.5;
	public final static double kTurnLimiter = 0.8;
	
	//Elevator Positions
	public final static int posSwitch = ElevatorConversions.cmsToClicks(65);
	public final static int posScale = ElevatorConversions.cmsToClicks(155);
	
	public final static int lowSwitch = ElevatorConversions.cmsToClicks(55);
	public final static int highSwitch = ElevatorConversions.cmsToClicks(80);
	public final static int lowScale = ElevatorConversions.cmsToClicks(140);
	public final static int highScale = ElevatorConversions.cmsToClicks(170);

	//Ultrasonic thresholds
	public final static double threshold = 80;
	public final static double squeezeThreshold = 110;
	
	//Elevator Heights
	public final static double driveHeight = 5; //centimeters
}

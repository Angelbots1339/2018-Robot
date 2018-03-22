/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1339.robot;

import org.usfirst.frc.team1339.robot.autonomous.AutonomousTest;
import org.usfirst.frc.team1339.robot.autonomous.CenterSwitchAuto;
import org.usfirst.frc.team1339.robot.autonomous.DriveForwardTimeout;
import org.usfirst.frc.team1339.robot.autonomous.LeftToScaleAuto;
import org.usfirst.frc.team1339.robot.autonomous.RightSideAuto;
import org.usfirst.frc.team1339.robot.autonomous.RightToOppositeScaleAuto;
import org.usfirst.frc.team1339.robot.autonomous.RightToScaleAuto;
import org.usfirst.frc.team1339.robot.commands.CommandBase;
import org.usfirst.frc.team1339.robot.commands.ShiftClimberOut;
import org.usfirst.frc.team1339.utils.ParseFiles;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	Command autonomousCommand;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		CommandBase.init();
		pathInit();
		
		CommandBase.server.autonomousSelector.add("Chill", new ShiftClimberOut());
		CommandBase.server.autonomousSelector.add("Drive Forward", new DriveForwardTimeout(0.6, 2));
		CommandBase.server.autonomousSelector.add("Center To Switch", new CenterSwitchAuto());
		CommandBase.server.autonomousSelector.add("Right To Scale And Pick Up", new RightToScaleAuto(true));
		//CommandBase.server.autonomousSelector.add("Right to opp scale", new RightToOppositeScaleAuto());
		//CommandBase.server.autonomousSelector.add("Right To Scale Force", new RightToScaleAuto(false));
		CommandBase.server.autonomousSelector.add("Left To Scale And Pick Up", new LeftToScaleAuto(true));
		//CommandBase.server.autonomousSelector.add("TwoCubeSwitch", new CenterSwitchSecondCubeAuto());
		//CommandBase.server.autonomousSelector.add("Left To Scale Force", new ExecuteProfile("LeftToOppositeScale"));
		//CommandBase.server.autonomousSelector.add("Two Cube", new TwoCube());
		CommandBase.server.autonomousSelector.add("Test", new AutonomousTest());
		
		
		CommandBase.server.autonomousSelector.setCurrentMode(0);
		CommandBase.server.start();
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		//CommandBase.leds.disabledInit();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		//CommandBase.leds.disabledPeriodic();
		CommandBase.intake.publishWebServer();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	private void pathInit(){
		RobotMap.Center_To_Left_Switch = new ParseFiles("CenterToLeftSwitch");
		RobotMap.Center_To_Right_Switch = new ParseFiles("CenterToRightSwitch");
		
		RobotMap.Center_To_Cube = new ParseFiles("CenterToCube");
		
		RobotMap.Drive_Forward = new ParseFiles("DriveForward");
		
		RobotMap.Left_To_Opposite_Scale = new ParseFiles("LeftToOppositeScale");
		
		RobotMap.First_To_Opposite_Scale = new ParseFiles("FirstToOppositeScale");
		RobotMap.Second_To_Opposite_Scale = new ParseFiles("SecondToOppositeScale");
		RobotMap.Third_To_Opposite_Scale = new ParseFiles("ThirdToOppositeScale");
		
		RobotMap.Left_To_Scale = new ParseFiles("LeftToScale");
		RobotMap.Left_Scale_Second_Cube = new ParseFiles("LeftScaleSecondCubeTest");
		
		RobotMap.Right_To_Scale = new ParseFiles("RightToScale");
		
		RobotMap.First_Scale_Test = new ParseFiles("FirstToLeftScale");
		RobotMap.Second_Scale_Test = new ParseFiles("SecondToLeftScale");
		RobotMap.Third_Scale_Test = new ParseFiles("ThirdToLeftScale");
		
		RobotMap.Second_Cube = new ParseFiles("SecondCube");
		
		RobotMap.Right_To_Opposite_Scale = new ParseFiles("RightToOppositeScale");
		
		//RobotMap.Reversed_Center_To_Left_Switch = new ParseFiles("ReversedCenterToLeftSwitch");
		//RobotMap.Reversed_Center_To_Right_Switch = new ParseFiles("ReversedCenterToRightSwitch");
	}
	
	@Override
	public void autonomousInit() {
		RobotMap.gameMessage = DriverStation.getInstance().getGameSpecificMessage();

		CommandBase.wrist.resetEncoder();
		CommandBase.elevator.resetEncoder();
		CommandBase.chassis.setBrakeMode(true);
		
		autonomousCommand = CommandBase.server.autonomousSelector.getCurrentModeCommand();
		
		if(CommandBase.server.autonomousSelector.getCurrentModeName().equals("Right Side Auto")) {
			if(RobotMap.gameMessage.length() > 0) {
	    		if(RobotMap.gameMessage.charAt(1) == 'R') {
	    			autonomousCommand = new RightToScaleAuto(true);
	    		} else if(RobotMap.gameMessage.charAt(1) == 'L') {
	    			autonomousCommand = new RightToOppositeScaleAuto();
	    		}
	    	}
		}
		
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
		
		//CommandBase.leds.autoInit();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		CommandBase.chassis.publishSmartDashboard();
		//CommandBase.leds.autoPeriodic();
	}

	@Override
	public void teleopInit() {
		CommandBase.chassis.setBrakeMode(false);
		CommandBase.chassis.resetSensors();
		CommandBase.wrist.toggle = -1;
		
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		
		//CommandBase.leds.teleOpInit();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		CommandBase.chassis.publishSmartDashboard();
		CommandBase.intake.publishWebServer();
		CommandBase.elevator.publishSmartDashboard();
		CommandBase.wrist.publishWebServer();
		
		//CommandBase.leds.teleOpPeriodic();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}

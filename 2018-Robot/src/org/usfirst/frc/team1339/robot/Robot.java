/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1339.robot;

import org.usfirst.frc.team1339.robot.autonomous.CenterSwitchAuto;
import org.usfirst.frc.team1339.robot.autonomous.DriveForwardTimeout;
import org.usfirst.frc.team1339.robot.autonomous.LeftToScaleAuto;
import org.usfirst.frc.team1339.robot.autonomous.RightToScaleAuto;
import org.usfirst.frc.team1339.robot.commands.CommandBase;
import org.usfirst.frc.team1339.robot.commands.ExecuteProfile;

import edu.wpi.first.wpilibj.CameraServer;
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
		
		CommandBase.server.autonomousSelector.add("Chill", null);
		CommandBase.server.autonomousSelector.add("Drive Forward", new DriveForwardTimeout(0.6, 2));
		CommandBase.server.autonomousSelector.add("Center To Switch", new CenterSwitchAuto());
		CommandBase.server.autonomousSelector.add("Right To Scale", new RightToScaleAuto());
		CommandBase.server.autonomousSelector.add("Left To Scale", new LeftToScaleAuto());
		
		CommandBase.server.autonomousSelector.add("Circle", new ExecuteProfile("Circle"));
		//CommandBase.server.autonomousSelector.add("Two Cube Left", new TwoCube());
		CommandBase.server.autonomousSelector.setCurrentMode(1);
		CommandBase.server.start();
		
		CameraServer.getInstance().startAutomaticCapture();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		CommandBase.leds.disabledInit();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		CommandBase.leds.disabledPeriodic();
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
	@Override
	public void autonomousInit() {
		RobotMap.gameMessage = DriverStation.getInstance().getGameSpecificMessage();
		CommandBase.chassis.setBrakeMode(true);
		autonomousCommand = CommandBase.server.autonomousSelector.getCurrentModeCommand();
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
		CommandBase.leds.autoInit();
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		CommandBase.leds.autoPeriodic();
	}

	@Override
	public void teleopInit() {
		CommandBase.chassis.setBrakeMode(false);
		CommandBase.wrist.toggle = -1;
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		CommandBase.chassis.resetSensors();
		CommandBase.leds.teleOpInit();
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
		
		CommandBase.leds.teleOpPeriodic();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}

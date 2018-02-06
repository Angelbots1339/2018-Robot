/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1339.robot;

import org.usfirst.frc.team1339.robot.commands.CommandBase;
import org.usfirst.frc.team1339.utils.Server;

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
	Server server;
	long counter = 0;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		CommandBase.init();
		server = new Server(8099);
		server.autonomousSelector.add("Chill", null);
		server.autonomousSelector.setCurrentMode(0);
		server.valueDisplay.putValue("Beam Break", "Disabled");
		server.valueDisplay.putValue("Counter", counter);
		server.start();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		server.valueDisplay.putValue("Beam Break", "Disabled");
		server.valueDisplay.putValue("Mode", "Disabled");
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
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
		autonomousCommand = server.autonomousSelector.getCurrentModeCommand();
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
		server.valueDisplay.putValue("Mode", "Auto");
		CommandBase.chassis.resetEncoders();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		counter = 0;
		server.valueDisplay.putValue("Mode", "TeleOp");
		CommandBase.chassis.resetEncoders();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		counter++;
		server.valueDisplay.putValue("Counter", counter);
		server.valueDisplay.putValue("Beam Break", CommandBase.intake.hazBox());
		server.valueDisplay.putValue("Match Time", DriverStation.getInstance().getMatchTime());
	
		CommandBase.chassis.publishSmartDashboard();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}

package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.ExecuteProfile;
import org.usfirst.frc.team1339.robot.commands.PIDElevator;
import org.usfirst.frc.team1339.robot.commands.PIDGyro;
import org.usfirst.frc.team1339.robot.commands.PIDWrist;
import org.usfirst.frc.team1339.robot.commands.ShiftClimberOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousTest extends CommandGroup {

    public AutonomousTest() {
    	addSequential(new ExecuteProfile(RobotMap.First_Scale_Test));
    	addSequential(new PIDElevatorSetpoint(RobotMap.posScale, 2));
    	addSequential(new PIDElevatorSetpoint(0, 2));
    	addSequential(new PIDGyro(-67, 0.7, 3));
    	addParallel(new ExecuteProfile(RobotMap.Second_Scale_Test));
    	addSequential(new DriveIntakeTimeout(0.7, 2.5));
    	addSequential(new ExecuteProfile(RobotMap.Third_Scale_Test));
    }
}

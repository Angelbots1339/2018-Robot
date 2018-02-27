package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.DriveIntakeTimeout;
import org.usfirst.frc.team1339.robot.commands.PIDElevator;
import org.usfirst.frc.team1339.robot.commands.PIDWrist;
import org.usfirst.frc.team1339.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterSwitchAuto extends CommandGroup {

    public CenterSwitchAuto() {
    	addParallel(new PIDWrist(RobotMap.wristHorizontal));
    	addParallel(new PIDElevator(RobotMap.posSwitch));
    	addSequential(new CenterToSwitch());
    	addSequential(new DriveIntakeTimeout(0.5, 1));
    }
}

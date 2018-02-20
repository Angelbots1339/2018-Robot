package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.commands.DriveIntakeTimeout;
import org.usfirst.frc.team1339.robot.commands.PIDElevator;
import org.usfirst.frc.team1339.robot.commands.PIDWrist;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterSwitchAuto extends CommandGroup {

    public CenterSwitchAuto() {
    	addParallel(new PIDWrist(-82));
    	addParallel(new PIDElevator(65));
    	addSequential(new CenterToSwitch());
    	addSequential(new DriveIntakeTimeout(-0.5, 1));
    }
}

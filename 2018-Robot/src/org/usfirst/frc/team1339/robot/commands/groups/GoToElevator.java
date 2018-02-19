package org.usfirst.frc.team1339.robot.commands.groups;

import org.usfirst.frc.team1339.robot.commands.DriveElevator;
import org.usfirst.frc.team1339.robot.commands.ShiftClimberOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GoToElevator extends CommandGroup {

    public GoToElevator() {
    	addSequential(new ShiftClimberOut());
    	addSequential(new DriveElevator());
    }
}

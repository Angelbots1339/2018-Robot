package org.usfirst.frc.team1339.robot.commands.groups;

import org.usfirst.frc.team1339.robot.commands.DriveClimber;
import org.usfirst.frc.team1339.robot.commands.ShiftClimberIn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GoToClimber extends CommandGroup {

    public GoToClimber() {
    	addSequential(new ShiftClimberIn());
    	addSequential(new DriveClimber());
    }
}

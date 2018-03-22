package org.usfirst.frc.team1339.robot.commands.groups;

import org.usfirst.frc.team1339.robot.commands.BackOut;
import org.usfirst.frc.team1339.robot.commands.FrontOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ClawOpen extends CommandGroup {

    public ClawOpen() {
    	addSequential(new BackOut());
    	addSequential(new FrontOut());
    }
}

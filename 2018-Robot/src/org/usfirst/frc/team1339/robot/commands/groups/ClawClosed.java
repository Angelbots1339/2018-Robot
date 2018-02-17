package org.usfirst.frc.team1339.robot.commands.groups;

import org.usfirst.frc.team1339.robot.commands.BackOut;
import org.usfirst.frc.team1339.robot.commands.FrontOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ClawClosed extends CommandGroup {

    public ClawClosed() {
    	addSequential(new BackOut());
    	addSequential(new FrontOut());
    }
}

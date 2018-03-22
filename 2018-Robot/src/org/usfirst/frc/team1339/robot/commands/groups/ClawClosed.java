package org.usfirst.frc.team1339.robot.commands.groups;

import org.usfirst.frc.team1339.robot.commands.BackIn;
import org.usfirst.frc.team1339.robot.commands.FrontIn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ClawClosed extends CommandGroup {

    public ClawClosed() {
    	addSequential(new BackIn());
    	addSequential(new FrontIn());
    }
}

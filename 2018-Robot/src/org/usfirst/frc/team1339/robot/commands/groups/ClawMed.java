package org.usfirst.frc.team1339.robot.commands.groups;

import org.usfirst.frc.team1339.robot.commands.BackIn;
import org.usfirst.frc.team1339.robot.commands.FrontOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ClawMed extends CommandGroup {

    public ClawMed() {
    	addSequential(new BackIn());
    	addSequential(new FrontOut());
    }
}

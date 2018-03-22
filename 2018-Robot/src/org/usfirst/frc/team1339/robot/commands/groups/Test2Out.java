package org.usfirst.frc.team1339.robot.commands.groups;

import org.usfirst.frc.team1339.robot.commands.BackIn;
import org.usfirst.frc.team1339.robot.commands.BackOut;
import org.usfirst.frc.team1339.robot.commands.FrontOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Test2Out extends CommandGroup {

    public Test2Out() {
    	addSequential(new FrontOut());
    }
}

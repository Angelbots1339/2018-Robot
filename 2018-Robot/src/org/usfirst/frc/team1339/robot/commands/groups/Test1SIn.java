package org.usfirst.frc.team1339.robot.commands.groups;

import org.usfirst.frc.team1339.robot.commands.BackIn;
import org.usfirst.frc.team1339.robot.commands.BackOut;
import org.usfirst.frc.team1339.robot.commands.FrontOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Test1SIn extends CommandGroup {

    public Test1SIn() {
    	addSequential(new BackIn());
    }
}

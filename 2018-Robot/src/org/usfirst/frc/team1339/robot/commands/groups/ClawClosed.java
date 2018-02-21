package org.usfirst.frc.team1339.robot.commands.groups;

import org.usfirst.frc.team1339.robot.commands.BackOut;
import org.usfirst.frc.team1339.robot.commands.FrontOut;
import org.usfirst.frc.team1339.robot.subsystems.Pinchers;
import org.usfirst.frc.team1339.robot.subsystems.Pinchers.ClawPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ClawClosed extends CommandGroup {

    public ClawClosed() {
    	Pinchers.setClawPosition(ClawPosition.CLOSED);
    	addSequential(new BackOut());
    	addSequential(new FrontOut());
    }
}

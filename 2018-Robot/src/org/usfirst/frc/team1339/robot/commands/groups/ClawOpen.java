package org.usfirst.frc.team1339.robot.commands.groups;

import org.usfirst.frc.team1339.robot.commands.BackIn;
import org.usfirst.frc.team1339.robot.commands.FrontIn;
import org.usfirst.frc.team1339.robot.subsystems.Pinchers;
import org.usfirst.frc.team1339.robot.subsystems.Pinchers.ClawPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ClawOpen extends CommandGroup {

    public ClawOpen() {
    	Pinchers.setClawPosition(ClawPosition.OPEN);
    	addSequential(new BackIn());
    	addSequential(new FrontIn());
    }
}

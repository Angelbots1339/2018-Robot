package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.commands.PIDElevator;
import org.usfirst.frc.team1339.robot.commands.ResetWrist;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartAuto extends CommandGroup {

    public StartAuto() {
    	addParallel(new PIDElevator(5));
    	addParallel(new ResetWrist());
    }
}

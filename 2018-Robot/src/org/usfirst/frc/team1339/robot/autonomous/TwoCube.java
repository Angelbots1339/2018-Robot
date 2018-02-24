package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.commands.ExecuteProfile;
import org.usfirst.frc.team1339.robot.commands.PIDGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TwoCube extends CommandGroup {

    public TwoCube() {
    	addSequential(new ExecuteProfile("RightScaleSameSideTest"));
    	addSequential(new Chill(3));
    	addSequential(new PIDGyro(-120, 0.7, 3));
    	addSequential(new Chill(0.5));
    	addSequential(new ExecuteProfile("RightScaleSecondCubeTest"));
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}

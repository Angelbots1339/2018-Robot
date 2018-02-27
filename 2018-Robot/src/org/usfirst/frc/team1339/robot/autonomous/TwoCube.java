package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.DriveIntakeTimeout;
import org.usfirst.frc.team1339.robot.commands.ExecuteProfile;
import org.usfirst.frc.team1339.robot.commands.PIDElevatorSetpoint;
import org.usfirst.frc.team1339.robot.commands.PIDGyro;
import org.usfirst.frc.team1339.robot.commands.PIDWrist;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TwoCube extends CommandGroup {

    public TwoCube() {
    	addSequential(new RightToScaleAuto(),15);
    	addParallel(new PIDWrist(RobotMap.wristHorizontal));
    	addSequential(new PIDElevatorSetpoint(0, 2));
    	addSequential(new PIDGyro(120, 0.7, 3));
    	addParallel(new ExecuteProfile("LeftScaleSecondCubeTest"));
    	addSequential(new DriveIntakeTimeout(-1, 2.5));
    	addSequential(new PIDElevatorSetpoint(RobotMap.posSwitch, 1));
    	addSequential(new DriveIntakeTimeout(0.8, 2));
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

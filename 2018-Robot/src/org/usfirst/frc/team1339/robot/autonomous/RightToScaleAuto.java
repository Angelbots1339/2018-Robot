package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.DriveIntakeTimeout;
import org.usfirst.frc.team1339.robot.commands.ExecuteProfile;
import org.usfirst.frc.team1339.robot.commands.PIDElevatorSetpoint;
import org.usfirst.frc.team1339.robot.commands.PIDWrist;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightToScaleAuto extends CommandGroup {

    public RightToScaleAuto() {
    	addSequential(new ExecuteProfile("LeftScaleSameSideTest"));
    	addParallel(new PIDWrist(RobotMap.wristFortyFive), 3);
    	addSequential(new PIDElevatorSetpoint(RobotMap.highScale, 2));
    	//addParallel(new PIDWrist(RobotMap.wristFortyFive));
    	addSequential(new DriveIntakeTimeout(0.75, 0.5));
    	
    	
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

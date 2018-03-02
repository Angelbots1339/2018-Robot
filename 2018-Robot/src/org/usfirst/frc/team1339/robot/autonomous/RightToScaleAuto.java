package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.PIDWrist;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightToScaleAuto extends CommandGroup {

    public RightToScaleAuto(boolean passive) {
    	addSequential(new RightToScale(passive));
    	addParallel(new PIDWrist(RobotMap.wristFortyFive), 3);
    	addSequential(new PIDElevatorSetpoint(RobotMap.highScale, 2));
    	addSequential(new DriveIntakeTimeout(-0.75, 0.5));
    }
}

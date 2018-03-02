package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.ExecuteProfile;
import org.usfirst.frc.team1339.robot.commands.PIDGyro;
import org.usfirst.frc.team1339.robot.commands.PIDWrist;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftToScaleAuto extends CommandGroup {

    public LeftToScaleAuto(boolean passive) {
    	//Cube in the scale
    	addSequential(new LeftToScale(passive));
    	addParallel(new PIDWrist(RobotMap.wristFortyFive), 3);
    	addSequential(new PIDElevatorSetpoint(RobotMap.highScale, 2));
    	addSequential(new DriveIntakeTimeout(-0.75, 0.5));
    	
    	//Second cube
    	addParallel(new PIDWrist(RobotMap.wristHorizontal));
    	addSequential(new PIDElevatorSetpoint(0, 2));
    	addSequential(new PIDGyro(113, 0.7, 3));
    	addParallel(new ExecuteProfile("LeftScaleSecondCubeTest"), 1.5);
    	addSequential(new DriveIntakeTimeout(0.7, 2.5));
    }
}

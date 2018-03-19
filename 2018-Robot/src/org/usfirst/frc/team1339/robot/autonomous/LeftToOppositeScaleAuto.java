package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.ExecuteProfile;
import org.usfirst.frc.team1339.robot.commands.PIDGyro;
import org.usfirst.frc.team1339.robot.commands.PIDWrist;
import org.usfirst.frc.team1339.robot.commands.ShiftClimberOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftToOppositeScaleAuto extends CommandGroup {

    public LeftToOppositeScaleAuto() {
    	addSequential(new ShiftClimberOut());
    	//Cube in the scale
    	/*
    	addSequential(new ExecuteProfile("FirstToOppositeScale"));
    	addSequential(new PIDGyro(86, 0.7, 3));
    	addSequential(new Chill(0.5));
    	*/
    	addSequential(new ExecuteProfile(RobotMap.Second_To_Opposite_Scale));
    	addSequential(new PIDGyro(-90, 0.7, 3));
    	addSequential(new Chill(0.5));

    	addSequential(new ExecuteProfile(RobotMap.Third_To_Opposite_Scale));
    	addSequential(new Chill(0.5));
    	addSequential(new PIDGyro(-90, 0.7, 3));
    	addSequential(new Chill(0.5));

    	addParallel(new PIDWrist(RobotMap.wristFortyFive), 3);
    	addSequential(new PIDElevatorSetpoint(RobotMap.highScale, 2));
    	addSequential(new DriveIntakeTimeout(-0.75, 0.5));
    	/*
    	//Second cube
    	addParallel(new PIDWrist(RobotMap.wristHorizontal));
    	addSequential(new PIDElevatorSetpoint(0, 2));
    	addSequential(new PIDGyro(113, 0.7, 3));
    	addParallel(new ExecuteProfile("LeftScaleSecondCubeTest"), 1.5);
    	addSequential(new DriveIntakeTimeout(0.7, 2.5));
    	*/
    }
}

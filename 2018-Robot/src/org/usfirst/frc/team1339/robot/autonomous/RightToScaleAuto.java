package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.ExecuteProfile;
import org.usfirst.frc.team1339.robot.commands.PIDElevator;
import org.usfirst.frc.team1339.robot.commands.PIDGyro;
import org.usfirst.frc.team1339.robot.commands.PIDWrist;
import org.usfirst.frc.team1339.robot.commands.ResetWrist;
import org.usfirst.frc.team1339.robot.commands.ShiftClimberOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightToScaleAuto extends CommandGroup {

    public RightToScaleAuto(boolean passive) {
    	//addSequential(new ShiftClimberOut());
    	//Cube in scale
    	addParallel(new StartAuto());
    	addSequential(new RightToScale(passive));
    	addParallel(new PIDWrist(RobotMap.wristFortyFive), 3);
    	addSequential(new PIDElevatorSetpoint(RobotMap.highScale, 2, RobotMap.tol2Cm));
    	addSequential(new DriveIntakeTimeout(-0.75, 0.5));
    	
    	//Pick up second cube
    	addParallel(new PIDWrist(RobotMap.wristHorizontal));
    	addSequential(new PIDElevatorSetpoint(0, 2, RobotMap.tol2Cm));
    	addSequential(new PIDGyro(-113, 1, 3));
    	addParallel(new ExecuteProfile(RobotMap.Second_Cube_PickUP));
    	addSequential(new DriveIntakeTimeout(0.7, 2));
    	addSequential(new ExecuteProfile(RobotMap.Right_Second_Cube));
    	addSequential(new PIDElevatorSetpoint(RobotMap.highScale, 2, RobotMap.tol2Cm));
    	addSequential(new PIDWrist(RobotMap.wristOTP),.8);
    	addSequential(new DriveIntakeTimeout(-0.75, 0.5));
    	addSequential(new PIDWrist(RobotMap.wristHorizontal),1);
    	addSequential(new PIDElevatorSetpoint(0, 2, RobotMap.tol2Cm));
    }
}

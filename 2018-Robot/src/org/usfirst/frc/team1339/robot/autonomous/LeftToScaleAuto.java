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
public class LeftToScaleAuto extends CommandGroup {

    public LeftToScaleAuto(boolean passive) {
    	addParallel(new StartAuto());
    	//Cube in the scale
    	addSequential(new LeftToScale(passive));
    	addParallel(new PIDWrist(RobotMap.wristFortyFive), 3);
    	addSequential(new PIDElevatorSetpoint(RobotMap.highScale, 2, RobotMap.tol2Cm));
    	addSequential(new DriveIntakeTimeout(-0.75, 0.5));
    	
    	//Second cube
    	addParallel(new PIDWrist(RobotMap.wristHorizontal));
    	addSequential(new PIDElevatorSetpoint(0, 2, RobotMap.tol2Cm));

    	addSequential(new PIDGyro(113, 0.7, 3));
    	addParallel(new ExecuteProfile(RobotMap.Second_Cube_PickUP), 1.5);
    	addSequential(new DriveIntakeTimeout(0.7, 2.5));
    	addSequential(new ExecuteProfile(RobotMap.Left_Second_Cube));

    	addSequential(new PIDElevatorSetpoint(RobotMap.highScale, 2, RobotMap.tol2Cm));
    	addSequential(new PIDWrist(RobotMap.wristOTP),.8);
    	addSequential(new DriveIntakeTimeout(-0.75, 0.5));
    	addSequential(new PIDWrist(RobotMap.wristHorizontal),1);
    	addSequential(new PIDElevatorSetpoint(0, 2, RobotMap.tol2Cm));
    }
}

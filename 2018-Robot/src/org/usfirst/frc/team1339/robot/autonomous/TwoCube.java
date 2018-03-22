package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.ExecuteProfile;
import org.usfirst.frc.team1339.robot.commands.PIDGyro;
import org.usfirst.frc.team1339.robot.commands.PIDWrist;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TwoCube extends CommandGroup {

    public TwoCube() {
    	addSequential(new RightToScaleAuto(false),15);
    	addParallel(new PIDWrist(RobotMap.wristHorizontal));
    	addSequential(new PIDElevatorSetpoint(0, 2, RobotMap.tol2Cm));
    	addSequential(new PIDGyro(-113, 0.7, 3));
    	addParallel(new ExecuteProfile(RobotMap.Left_Scale_Second_Cube));
    	addSequential(new DriveIntakeTimeout(0.7, 2.5));
    	addSequential(new PIDElevatorSetpoint(RobotMap.posSwitch, 1, RobotMap.tol2Cm));
    	addSequential(new DriveIntakeTimeout(-0.8, 2));
    }
}

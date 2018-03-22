package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.ExecuteProfile;
import org.usfirst.frc.team1339.robot.commands.PIDElevator;
import org.usfirst.frc.team1339.robot.commands.PIDWrist;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterSwitchSecondCubeAuto extends CommandGroup {

    public CenterSwitchSecondCubeAuto() {
    	addParallel(new PIDWrist(RobotMap.wristHorizontal));
    	addParallel(new PIDElevator(RobotMap.posSwitch));
    	addSequential(new CenterToSwitch());
    	addSequential(new DriveIntakeTimeout(-0.5, 1));
    	addParallel(new SwitchToCenter());
    	addSequential(new Chill(.7));
    	addSequential(new PIDElevatorSetpoint(0,1, RobotMap.tol2Cm));
    	addParallel(new ExecuteProfile(RobotMap.Center_To_Cube));
    	addSequential(new DriveIntakeTimeout(0.7, 1.5));
    }
}

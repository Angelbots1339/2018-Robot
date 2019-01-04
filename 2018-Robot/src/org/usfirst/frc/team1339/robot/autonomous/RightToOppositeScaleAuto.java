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
public class RightToOppositeScaleAuto extends CommandGroup {

    public RightToOppositeScaleAuto() {
    	addParallel(new StartAuto());
    	addSequential(new ExecuteProfile(RobotMap.Right_To_Opposite_Scale));
    	addParallel(new PIDWrist(RobotMap.wristFortyFive), 3);
    	addSequential(new PIDElevatorSetpoint(RobotMap.highScale, 2, RobotMap.tol2Cm));
    	addSequential(new DriveIntakeTimeout(-0.75, 0.5));
    	addSequential(new PIDElevatorSetpoint(0, 2, RobotMap.tol2Cm));
    }
}

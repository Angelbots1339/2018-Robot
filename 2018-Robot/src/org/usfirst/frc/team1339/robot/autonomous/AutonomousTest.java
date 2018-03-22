package org.usfirst.frc.team1339.robot.autonomous;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.ExecuteProfile;
import org.usfirst.frc.team1339.robot.commands.PIDWrist;
import org.usfirst.frc.team1339.robot.commands.ResetWrist;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousTest extends CommandGroup {

    public AutonomousTest() {
    	addSequential(new ExecuteProfile(RobotMap.Right_To_Opposite_Scale));
    	
//    	addSequential(new PIDElevatorSetpoint(RobotMap.posScale, 2, RobotMap.tol2Cm));
//    	addSequential(new PIDWrist(RobotMap.wristOTP, RobotMap.wristTol2Cm));
//    	addSequential(new PIDWrist(RobotMap.wristHorizontal, RobotMap.wristTol2Cm));
    	//addSequential(new PIDElevatorSetpoint(0, 2, RobotMap.tol2Cm));
    	//addSequential(new PIDGyro(-67, 0.7, 3));
    	//addParallel(new ExecuteProfile(RobotMap.Second_Scale_Test));
    	//addSequential(new DriveIntakeTimeout(0.7, 2.5));
    	//addSequential(new ExecuteProfile(RobotMap.Third_Scale_Test));
    }
}

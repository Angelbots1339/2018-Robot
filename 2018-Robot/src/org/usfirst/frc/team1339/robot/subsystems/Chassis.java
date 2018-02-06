package org.usfirst.frc.team1339.robot.subsystems;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.ArcadeDrive;
import org.usfirst.frc.team1339.utils.Conversions;
import org.usfirst.frc.team1339.utils.MotionProfiling;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Chassis extends Subsystem {

    private TalonSRX lMaster, lFrontSlave, lBackSlave, rMaster, rFrontSlave, rBackSlave;
    
	private PrintWriter log;
	private File fileCreator;
    
    private double deadband = 0.02;
    
	private double initialTime;
	private boolean recording = false;
	private boolean following = false;
	
	private double limiter = 1;
	
	public static MotionProfiling leftProfiler;
	public static MotionProfiling rightProfiler;
	
	private double lastLeftVel = 0;
	private double lastRightVel = 0;
	private double lastTime = 0;
	
	Notifier notifier;
    
    public Chassis() {
    	lMaster = new TalonSRX(RobotMap.leftTopDriveMotor);
    	lMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
    	setPIDF(lMaster, 0, RobotMap.talonKf, RobotMap.talonKp, RobotMap.talonKi, RobotMap.talonKd);
    	
    	lFrontSlave = new TalonSRX(RobotMap.leftFrontDriveMotor);
    	lFrontSlave.setInverted(true); //Da cim not work :reverse::fire:
    	lFrontSlave.follow(lMaster);
    	
    	lBackSlave = new TalonSRX(RobotMap.leftBackDriveMotor);
    	lBackSlave.follow(lMaster);
    	
    	rMaster = new TalonSRX(RobotMap.rightTopDriveMotor);
    	rMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
    	setPIDF(rMaster, 0, RobotMap.talonKf, RobotMap.talonKp, RobotMap.talonKi, RobotMap.talonKd);
    	
    	rFrontSlave = new TalonSRX(RobotMap.rightFrontDriveMotor);
    	rFrontSlave.follow(rMaster);
    	
    	rBackSlave = new TalonSRX(RobotMap.rightBackDriveMotor);
    	rBackSlave.follow(rMaster);  	
    	
    	log = null;
    	
		leftProfiler = new MotionProfiling(lMaster, true);
		rightProfiler = new MotionProfiling(rMaster, false);
		
		notifier = new Notifier(new PeriodicRunnable());
    }

    public void initDefaultCommand() {
    	setDefaultCommand(new ArcadeDrive());
    }
    
	public void resetEncoders() {
		rMaster.setSelectedSensorPosition(0, 0, 10);
		lMaster.setSelectedSensorPosition(0, 0, 10);
	}
    
	public void publishSmartDashboard() {
		SmartDashboard.putNumber("Right Position Meters", Conversions.clicksToMeters(rMaster.getSelectedSensorPosition(0)));
		SmartDashboard.putNumber("Right Position Encoder", rMaster.getSelectedSensorPosition(0));

		SmartDashboard.putNumber("Left Position Meters", Conversions.clicksToMeters(lMaster.getSelectedSensorPosition(0)));
		SmartDashboard.putNumber("Left Position Encoder", lMaster.getSelectedSensorPosition(0));

		SmartDashboard.putNumber("Right Velocity", Conversions.clickVelToMetersPerSec(rMaster.getSelectedSensorVelocity(0)));
		SmartDashboard.putNumber("Left Velocity", Conversions.clickVelToMetersPerSec(lMaster.getSelectedSensorVelocity(0)));
		SmartDashboard.putBoolean("Recording", recording);
		SmartDashboard.putBoolean("Following", following);
		
		double time = Timer.getFPGATimestamp();
		
		SmartDashboard.putNumber("Right Acceleration", Conversions.clickVelToMetersPerSec((rMaster.getSelectedSensorVelocity(0) - lastRightVel)/ (time - lastTime)));
		lastRightVel = rMaster.getSelectedSensorVelocity(0);
		SmartDashboard.putNumber("Left Acceleration", Conversions.clicksToMeters((lMaster.getSelectedSensorVelocity(0) - lastLeftVel)/ (time - lastTime)));
		lastLeftVel = lMaster.getSelectedSensorVelocity(0);
		lastTime = time;

		if(rMaster.getControlMode() == ControlMode.MotionProfile) {
			SmartDashboard.putNumber("Right MP Position", rMaster.getActiveTrajectoryPosition());
			SmartDashboard.putNumber("Right MP Velocity", rMaster.getActiveTrajectoryVelocity());
		}
		
		if(lMaster.getControlMode() == ControlMode.MotionProfile) {
			SmartDashboard.putNumber("Left MP Position", lMaster.getActiveTrajectoryPosition());
			SmartDashboard.putNumber("Left MP Velocity", lMaster.getActiveTrajectoryVelocity());
		}
	}
	
	public void setHighGear(boolean highGear) {
		limiter = (highGear ? 1 : RobotMap.kLimiter);
	}
    
    public void setMotorValues(double left, double right) {
    	lMaster.set(ControlMode.PercentOutput, left);
    	rMaster.set(ControlMode.PercentOutput, right);
    }
    
    public void directionalDrive(double throttle, double turn) {
    	throttle = Math.copySign(throttle * throttle, throttle);
        turn = Math.copySign(turn * turn, turn);
        
        throttle = limit(throttle);
        throttle = applyDeadband(throttle, deadband);

        turn = limit(turn);
        turn = applyDeadband(turn, deadband);
        
        double leftMotorOutput;
        double rightMotorOutput;

        double maxInput = Math.copySign(Math.max(Math.abs(throttle), Math.abs(turn)), throttle);

        if (throttle >= 0.0) {
          // First quadrant, else second quadrant
          if (turn >= 0.0) {
            leftMotorOutput = maxInput;
            rightMotorOutput = throttle - turn;
          } else {
            leftMotorOutput = throttle + turn;
            rightMotorOutput = maxInput;
          }
        } else {
          // Third quadrant, else fourth quadrant
          if (turn >= 0.0) {
            leftMotorOutput = throttle + turn;
            rightMotorOutput = maxInput;
          } else {
            leftMotorOutput = maxInput;
            rightMotorOutput = throttle - turn;
          }
        }

        leftMotorOutput *= limiter;
        rightMotorOutput *= limiter;
        
        setMotorValues(limit(leftMotorOutput), limit(-rightMotorOutput));
    }
    
    private double limit(double value) {
    	double limit = .6;
    	return Math.max(Math.min(limit, value), -limit);
    }
    
    private double applyDeadband(double value, double db) {
    	if(Math.abs(value) > db) {
    		if(value > 0.0) {
    			return (value - db) / (1.0 - db);
    		} else {
    			return (value + db) / (1.0 - db);
    		}
    	} else {
    		return 0.0;
    	}
    }
    
    /*
     ************************************************************
     *                 Motion Profiling Stuff                   *
     ************************************************************
     */
    
    class PeriodicRunnable implements java.lang.Runnable {
	    public void run() {
	    	if (rightProfiler.canFillBuffer()) rMaster.processMotionProfileBuffer();
	    	if (leftProfiler.canFillBuffer()) lMaster.processMotionProfileBuffer();
	    }
	}
    
    public void initLog(String name) {
    	if (log == null) {
			try {
				fileCreator = new File("/home/lvuser/Records/" + name + ".txt");
				if(fileCreator.exists()) {
					fileCreator.delete();
    				fileCreator = new File("/home/lvuser/Records/" + name + ".txt");
				}
				fileCreator.createNewFile();
				log = new PrintWriter(fileCreator);
				System.out.println("Log created");
				recording = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			initialTime = Timer.getFPGATimestamp();
    	} else {
    		System.out.println("Log already exists");
    	}
    	lMaster.setSelectedSensorPosition(0, 0, 0);
    	rMaster.setSelectedSensorPosition(0, 0, 0);
    }
    
    public void closeLog() {
    	if(log != null) {
    		log.close();
    		log = null;
    		System.out.println("Log closed");
    		recording = false;
    	} else {
    		System.out.println("Log already closed");
    	}
    }
    
    public void recordArcadeDrive(double throttle, double turn) {
    	if (log != null) {
    		double currentTime = Timer.getFPGATimestamp();
    		log.println(Conversions.clickVelToMetersPerSec(lMaster.getSelectedSensorVelocity(0)) + ","
    				+ Conversions.clicksToMeters(lMaster.getSelectedSensorPosition(0)) + ","
    				+ Conversions.clickVelToMetersPerSec(rMaster.getSelectedSensorVelocity(0)) + ","
    				+ Conversions.clicksToMeters(rMaster.getSelectedSensorPosition(0)) + ","
    				+ (currentTime-initialTime));
    		initialTime = currentTime;
    	} else {
    		System.out.println("Log was null, couldn't record");
    	}
    	
    	directionalDrive(throttle, turn);
    }

    public void initializeMotionProfile(String filename) {
    	resetEncoders();
    	rMaster.changeMotionControlFramePeriod(10);
    	lMaster.changeMotionControlFramePeriod(10);
    	leftProfiler.initialize(filename);
    	rightProfiler.initialize(filename);
    	notifier.startPeriodic(0.01);
    	following = true;
    }
    
    public void executeMotionProfile() {
    	rightProfiler.execute();
    	leftProfiler.execute();
    	
		rMaster.set(ControlMode.MotionProfile, rightProfiler.getValue());
    	lMaster.set(ControlMode.MotionProfile, leftProfiler.getValue());
    }
    
    public boolean isTrajectoryFinished() {
    	return leftProfiler.isTrajectoryFinished();//rightProfiler.isTrajectoryFinished(); //|| leftProfiler.isTrajectoryFinished();
    }
    
    public void cancelMotionProfile() {
    	notifier.stop();
    	following = false;
    	leftProfiler.reset();
    	rightProfiler.reset();
    }
    
    private void setPIDF(TalonSRX _talon, int slot, double kF, double kP, double kI, double kD) {
		_talon.config_kF(slot, kF, 10);
		_talon.config_kP(slot, kP, 10);
		_talon.config_kI(slot, kI, 10);
		_talon.config_kD(slot, kD, 10);
    }
    
    
}


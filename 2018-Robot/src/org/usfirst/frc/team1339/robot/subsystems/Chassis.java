package org.usfirst.frc.team1339.robot.subsystems;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.robot.commands.ArcadeDrive;
import org.usfirst.frc.team1339.utils.ChassisConversions;
import org.usfirst.frc.team1339.utils.MotionProfiling;
import org.usfirst.frc.team1339.utils.SynchronousPID;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogGyro;
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
	
	private double throttleLimiter = 1, turnLimiter = 1;
	
	public static MotionProfiling leftProfiler;
	public static MotionProfiling rightProfiler;
	
	public static SynchronousPID gyroPID;
	
	private AnalogGyro gyro = new AnalogGyro(RobotMap.gyroId);
	
	Notifier notifier;
    
    public Chassis() {
    	lMaster = new TalonSRX(RobotMap.leftTopDriveMotor);
    	lMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
    	lMaster.setSensorPhase(true);
    	setPIDF(lMaster, 0, RobotMap.talonKf, RobotMap.talonKp, RobotMap.talonKi, RobotMap.talonKd);
    	
    	lFrontSlave = new TalonSRX(RobotMap.leftFrontDriveMotor);
    	lFrontSlave.setInverted(true); //Da cim not work :reverse::fire:
    	lFrontSlave.follow(lMaster);
    	
    	lBackSlave = new TalonSRX(RobotMap.leftBackDriveMotor);
    	lBackSlave.follow(lMaster);
    	
    	rMaster = new TalonSRX(RobotMap.rightTopDriveMotor);
    	rMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
    	rMaster.setSensorPhase(true);
    	rMaster.setInverted(true);
    	setPIDF(rMaster, 0, RobotMap.talonKf, RobotMap.talonKp, RobotMap.talonKi, RobotMap.talonKd);
    	
    	rFrontSlave = new TalonSRX(RobotMap.rightFrontDriveMotor);
    	rFrontSlave.follow(rMaster);
    	rFrontSlave.setInverted(true);
    	
    	rBackSlave = new TalonSRX(RobotMap.rightBackDriveMotor);
    	rBackSlave.follow(rMaster);  	
    	rBackSlave.setInverted(true);
    	
    	log = null;
    	
		leftProfiler = new MotionProfiling(lMaster, true);
		rightProfiler = new MotionProfiling(rMaster, false);
		
		notifier = new Notifier(new PeriodicRunnable());
		
		gyroPID = new SynchronousPID(RobotMap.gyroKp, RobotMap.gyroKi, RobotMap.gyroKd);
    }

    public void initDefaultCommand() {
    	setDefaultCommand(new ArcadeDrive());
    }
    
	public void resetEncoders() {
		log(rMaster.setSelectedSensorPosition(0, 0, 10));
		log(lMaster.setSelectedSensorPosition(0, 0, 10));
	}
	
	public void resetGyro() {
		gyro.reset();
	}
	
	public void resetSensors() {
		resetEncoders();
		resetGyro();
	}
	
	public double getGyroAngle() {
		double correctedAngle = gyro.getAngle() * RobotMap.gyroKe;
		return correctedAngle;
	}
	
	private void setBrakeMode(boolean value) {
		lMaster.setNeutralMode(value ? NeutralMode.Brake : NeutralMode.Coast);
		lFrontSlave.setNeutralMode(value ? NeutralMode.Brake : NeutralMode.Coast);
		lBackSlave.setNeutralMode(value ? NeutralMode.Brake : NeutralMode.Coast);
		rMaster.setNeutralMode(value ? NeutralMode.Brake : NeutralMode.Coast);
		rFrontSlave.setNeutralMode(value ? NeutralMode.Brake : NeutralMode.Coast);
		rBackSlave.setNeutralMode(value ? NeutralMode.Brake : NeutralMode.Coast);
	}
    
	public void publishSmartDashboard() {
		SmartDashboard.putBoolean("High Gear", throttleLimiter == 1);
		SmartDashboard.putNumber("Right Position Meters", ChassisConversions.clicksToMeters(rMaster.getSelectedSensorPosition(0)));
		//SmartDashboard.putNumber("Right Position Encoder", rMaster.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Velocity MPS", ChassisConversions.clickVelToMetersPerSec(rMaster.getSelectedSensorVelocity(0)));
		//SmartDashboard.putNumber("Right Velocity Enc", rMaster.getSelectedSensorVelocity(0));

		SmartDashboard.putNumber("Left Position Meters", ChassisConversions.clicksToMeters(lMaster.getSelectedSensorPosition(0)));
		//SmartDashboard.putNumber("Left Position Encoder", lMaster.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Left Velocity MPS", ChassisConversions.clickVelToMetersPerSec(lMaster.getSelectedSensorVelocity(0)));
		//SmartDashboard.putNumber("Left Velocity Enc", lMaster.getSelectedSensorVelocity(0));
		
		SmartDashboard.putNumber("gyro", getGyroAngle());
		
		SmartDashboard.putBoolean("Recording", recording);
		SmartDashboard.putBoolean("Following", following);
		
		SmartDashboard.putNumber("right executing", rightProfiler.counter);
		SmartDashboard.putNumber("left executing", leftProfiler.counter);
		
		/*MotionProfileStatus lStatus = new MotionProfileStatus();
		lMaster.getMotionProfileStatus(lStatus);
		SmartDashboard.putBoolean("has Underrun left", lStatus.hasUnderrun);
		MotionProfileStatus rStatus = new MotionProfileStatus();
		rMaster.getMotionProfileStatus(rStatus);
		SmartDashboard.putBoolean("has Underrun right", rStatus.hasUnderrun);*/
		
		if(rMaster.getControlMode() == ControlMode.MotionProfile) {
			SmartDashboard.putNumberArray("Right MP Position",
					new double[] {rMaster.getActiveTrajectoryPosition(), rMaster.getSelectedSensorPosition(0)});
			SmartDashboard.putNumber("Right MP Velocity", rMaster.getActiveTrajectoryVelocity());
			SmartDashboard.putNumber("Right MP Error", rMaster.getClosedLoopError(0));
		}
		
		if(lMaster.getControlMode() == ControlMode.MotionProfile) {
			SmartDashboard.putNumberArray("Left MP Position",
					new double[] {lMaster.getActiveTrajectoryPosition(), lMaster.getSelectedSensorPosition(0)});
			SmartDashboard.putNumber("Left MP Velocity", lMaster.getActiveTrajectoryVelocity());
			SmartDashboard.putNumber("Left MP Error", lMaster.getClosedLoopError(0));
		}
	}
	
	public void setHighGear(boolean highGear) {
		throttleLimiter = (highGear ? 1 : RobotMap.kThrottleLimiter);
		turnLimiter = (highGear ? 1 : RobotMap.kTurnLimiter);
	}
    
    public void setMotorValues(double left, double right) {
    	lMaster.set(ControlMode.PercentOutput, left);
    	rMaster.set(ControlMode.PercentOutput, right);
    }
    
    public void directionalDrive(double throttle, double turn) {
    	throttle = Math.copySign(throttle * throttle, throttle);
        turn = Math.copySign(turn * turn, turn);
        
        throttle *= throttleLimiter;
        turn *= turnLimiter;
        
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

        //leftMotorOutput *= limiter;
        //rightMotorOutput *= limiter;
        
        setMotorValues(limit(leftMotorOutput), limit(rightMotorOutput));
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
    
    public void gyroPID() {
    	setBrakeMode(true);
    	gyroPID.calculate(getGyroAngle());
    	double output = gyroPID.get();
    	setMotorValues(output, -output);
    }
    
    /*
     ************************************************************
     *                 Motion Profiling Stuff                   *
     ************************************************************
     */
    
    class PeriodicRunnable implements java.lang.Runnable {
	    public void run() {
	    	rMaster.processMotionProfileBuffer();
	    	lMaster.processMotionProfileBuffer();
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
    		log.println(ChassisConversions.clickVelToMetersPerSec(lMaster.getSelectedSensorVelocity(0)) + ","
    				+ ChassisConversions.clicksToMeters(lMaster.getSelectedSensorPosition(0)) + ","
    				+ ChassisConversions.clickVelToMetersPerSec(rMaster.getSelectedSensorVelocity(0)) + ","
    				+ ChassisConversions.clicksToMeters(rMaster.getSelectedSensorPosition(0)) + ","
    				+ (currentTime-initialTime));
    		initialTime = currentTime;
    	} else {
    		System.out.println("Log was null, couldn't record");
    	}
    	
    	directionalDrive(throttle, turn);
    }

    public void initializeMotionProfile(String filename) {
    	resetEncoders();
    	setBrakeMode(true);
    	log(rMaster.changeMotionControlFramePeriod(10));
    	log(lMaster.changeMotionControlFramePeriod(10));
    	
    	rightProfiler.initialize(filename);
    	leftProfiler.initialize(filename);
    	
    	notifier.startPeriodic(0.005);
    	following = true;
    }
    
    public void executeMotionProfile() {
    	rightProfiler.execute();
    	leftProfiler.execute();
    	
		if(rightProfiler.isStarted() && leftProfiler.isStarted()) {
			rMaster.set(ControlMode.MotionProfile, rightProfiler.getValue());
			lMaster.set(ControlMode.MotionProfile, leftProfiler.getValue());
		}
    }
    
    public boolean isTrajectoryFinished() {
    	return rightProfiler.isTrajectoryFinished() && leftProfiler.isTrajectoryFinished();
    }
    
    public void cancelMotionProfile() {
    	notifier.stop();
    	following = false;
    	leftProfiler.reset();
    	rightProfiler.reset();
    	//setBrakeMode(false);
    }
    
    private void setPIDF(TalonSRX _talon, int slot, double kF, double kP, double kI, double kD) {
		log(_talon.config_kF(slot, kF, 10));
		log(_talon.config_kP(slot, kP, 10));
		log(_talon.config_kI(slot, kI, 10));
		log(_talon.config_kD(slot, kD, 10));
    }
    
    private void log(ErrorCode code) {
    	if(code == ErrorCode.OK) return;
    	System.out.println(code);
    }
}


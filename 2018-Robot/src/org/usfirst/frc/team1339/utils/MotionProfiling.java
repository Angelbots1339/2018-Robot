package org.usfirst.frc.team1339.utils;

import java.util.ArrayList;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotionProfiling {
	
	private MotionProfileStatus _status = new MotionProfileStatus();
	
	double _pos=0,_vel=0,_heading=0;
	
	TalonSRX _talon;
	
	private SetValueMotionProfile _setValue = SetValueMotionProfile.Disable;

	private static final int kMinPointsInTalon = 10;
	
	private ParseFiles file = new ParseFiles();
	
	private boolean isLeft, isStarted = false;
	
	public MotionProfiling(TalonSRX talon, boolean isLeft){
		_talon = talon;
		this.isLeft = isLeft;
	}
	
	public void reset() {
		/*
		 * Let's clear the buffer just in case user decided to disable in the
		 * middle of an MP, and now we have the second half of a profile just
		 * sitting in memory.
		 */
		_talon.clearMotionProfileTrajectories();
		/* When we do re-enter motionProfile control mode, stay disabled. */
		_setValue = SetValueMotionProfile.Disable;
	}
	
	public void initialize(String name) {
		_setValue = SetValueMotionProfile.Enable;
		file.loadFile(name);
		startFilling(file.getLog(isLeft));
		isStarted = false;
		_talon.configMotionProfileTrajectoryPeriod(20, 10);
	}
	
	public void startFilling(ArrayList<LogPoint> log) {
		_talon.clearMotionProfileTrajectories();
		_talon.clearMotionProfileHasUnderrun(0);
		int totalCnt = log.size();	
		
		TrajectoryPoint point = new TrajectoryPoint();
		/* This is fast since it's just into our TOP buffer */
		for (int i = 0; i < totalCnt; ++i) {
			
			double position = Conversions.metersToClicks(log.get(i).position);
			double velocity = Conversions.metersPerSecToClickVel(log.get(i).velocity);
			/* for each point, fill our structure and pass it to API */
			point.position = position;
			point.velocity = velocity;
			point.headingDeg = 0; /* future feature - not used in this example*/
			point.profileSlotSelect1 = 0; /* which set of gains would you like to use [0,3]? */
			point.timeDur = TrajectoryDuration.Trajectory_Duration_0ms;
			
			point.zeroPos = false;
			if (i == 0)
				point.zeroPos = true; /* set this to true on the first point */

			point.isLastPoint = false;
			if ((i + 1) == totalCnt) 
				point.isLastPoint = true; /* set this to true on the last point  */

			_talon.pushMotionProfileTrajectory(point);
			
		}
	}
	
	public boolean canFillBuffer() {
		return _status.btmBufferCnt < 128;
	}
	
	public void execute() {
		_talon.getMotionProfileStatus(_status);
		
		/* wait for MP to stream to Talon, really just the first few
		* points
		*/
		/* do we have a minimum number of points in Talon */
		if (!isStarted && _status.btmBufferCnt > kMinPointsInTalon) {
			/* start (once) the motion profile */
			_setValue = SetValueMotionProfile.Enable;
			isStarted = true;
		}
		if (_status.activePointValid && _status.isLast) {
			/*
			 * because we set the last point's isLast to true, we will
			 * get here when the MP is done
			 */
			_setValue = SetValueMotionProfile.Hold;
		}
		SmartDashboard.putNumber("Buffer Count", _status.btmBufferCnt);
		SmartDashboard.putNumber("Top buffer count", _status.topBufferCnt);
	}
	
	public double getValue() {
		if (_setValue == SetValueMotionProfile.Disable) return 0;
		else if (_setValue == SetValueMotionProfile.Enable) return 1;
		return 2;
	}
	
	public boolean isTrajectoryFinished() {
		return _status.isLast;
	}
	
	public boolean isStarted() {
		return isStarted;
	}
}

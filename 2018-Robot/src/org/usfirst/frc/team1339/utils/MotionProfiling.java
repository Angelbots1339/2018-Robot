package org.usfirst.frc.team1339.utils;

import java.util.ArrayList;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class MotionProfiling {
	
	private MotionProfileStatus _status = new MotionProfileStatus();
	
	double _pos=0,_vel=0,_heading=0;
	
	TalonSRX _talon;
	
	int index = 0;
	
	private SetValueMotionProfile _setValue = SetValueMotionProfile.Disable;

	private static final int kMinPointsInTalon = 5; //When it is 1 we are having the "KEE" -> 5 is recommended
	public double counter;
	public double bufferPoints;
	public boolean hasFinished = false;
	private int minFillPoints = (int)(80);
	private LogPoint[] logFile;
	
	private ParseFiles file;
	
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
		log(_talon.clearMotionProfileTrajectories());
		/* When we do re-enter motionProfile control mode, stay disabled. */
		_setValue = SetValueMotionProfile.Disable;
		hasFinished = false;
	}
	
	public void initialize(ParseFiles file) {
		index = 0;
		this.file = file;
		this.logFile = file.getLog(isLeft);
		_setValue = SetValueMotionProfile.Disable;
		
		log(_talon.clearMotionProfileTrajectories());
		log(_talon.clearMotionProfileHasUnderrun(10));
		log(_talon.getMotionProfileStatus(_status)); // why are we doing this?  Chad says you're dumb
		
		fillPoints();
		isStarted = false;
		log(_talon.configMotionProfileTrajectoryPeriod(0, 10));
		//counter = 0;
		hasFinished = false;
	}
	
	public void startFilling() {
		log(_talon.clearMotionProfileTrajectories());
		log(_talon.clearMotionProfileHasUnderrun(10));
		int totalCnt = file.getSize();	
		log(_talon.getMotionProfileStatus(_status)); // why are we doing this?  Chad says you're dumb
		
		TrajectoryPoint point = new TrajectoryPoint();
		/* This is fast since it's just into our TOP buffer */
		for (int i = 0; i < totalCnt; i++) {
			
			double position = ChassisConversions.metersToClicks(logFile[i].position);
			double velocity = ChassisConversions.metersPerSecToClickVel(logFile[i].velocity);
			/* for each point, fill our structure and pass it to API */
			point.position = position;
			point.velocity = velocity;
			point.headingDeg = 0; /* future feature - not used in this example*/
			point.profileSlotSelect1 = 0; /* which set of gains would you like to use [0,3]? */
			point.timeDur = TrajectoryDuration.Trajectory_Duration_20ms;
			
			point.zeroPos = false;
			if (i == 0)
				point.zeroPos = true; /* set this to true on the first point */

			point.isLastPoint = false;
			if ((i + 1) == totalCnt) 
				point.isLastPoint = true; /* set this to true on the last point  */

			log(_talon.pushMotionProfileTrajectory(point));
			
		}
	}
	private void fillPoints() {

		int max = Math.min(index+30, file.getSize());
		/* This is fast since it's just into our TOP buffer */
		for (int i = index; i < max; i++) {
			System.out.println("PUTTING POINTS");
			TrajectoryPoint point = new TrajectoryPoint();

			double position = ChassisConversions.metersToClicks(logFile[i].position);
			double velocity = ChassisConversions.metersPerSecToClickVel(logFile[i].velocity);
			/* for each point, fill our structure and pass it to API */
			point.position = position;
			point.velocity = velocity;
			point.headingDeg = 0; /* future feature - not used in this example*/
			point.profileSlotSelect1 = 0; /* which set of gains would you like to use [0,3]? */
			point.timeDur = TrajectoryDuration.Trajectory_Duration_20ms;
			
			point.zeroPos = false;
			if (i == 0)
				point.zeroPos = true; /* set this to true on the first point */

			point.isLastPoint = false;
			if ((i + 1) == file.getSize()) 
				point.isLastPoint = true; /* set this to true on the last point  */

			log(_talon.pushMotionProfileTrajectory(point));
			
		}
		index = max;
	}
	
	public boolean canFillBuffer() {
		return _status.btmBufferCnt < 128;
	}
	
	public int getBtmBuffer() {return _status.btmBufferCnt;}
	public int getTopBuffer() {return _status.topBufferCnt;}
	
	public void execute() {
		log(_talon.getMotionProfileStatus(_status));
		//counter++;
		bufferPoints = _status.btmBufferCnt;
		System.out.println(bufferPoints);
		System.out.println("Index = " + index);
		/* wait for MP to stream to Talon, really just the first few
		* points
		*/
		
		if (isStarted && _status.topBufferCnt<minFillPoints)
			fillPoints();
		
		/* do we have a minimum number of points in Talon */
		// We might want to check for an underrun condition here and log it, otherwise it could happen
		// and we'd never know about it.  Chad and Angelo added this trash below.
		if (_status.hasUnderrun) {
			System.out.println(new java.lang.Error("The buffer has underran. Is it Left? " + isLeft));
		}
		
		if (!isStarted && _status.btmBufferCnt > kMinPointsInTalon) {
			/* start (once) the motion profile */
			_setValue = SetValueMotionProfile.Enable;
			isStarted = true;
		}
		
		System.out.println(_status.isLast);
		if (_status.activePointValid && _status.isLast) {
			/*
			 * because we set the last point's isLast to true, we will
			 * get here when the MP is done
			 */
			_setValue = SetValueMotionProfile.Hold;
			hasFinished = true;
		}
	}
	
	public double getValue() {
		if (_setValue == SetValueMotionProfile.Disable) return 0; //Do not Run Motion Profiling
		else if (_setValue == SetValueMotionProfile.Enable) return 1; //Do Run Motion Porofiling
		return 2; //Hold
	}
	
	public boolean isTrajectoryFinished() {
		return hasFinished;
	}
	
	public boolean isStarted() {
		return isStarted;
	}
	
	private void log(ErrorCode code) {
    	if(code != ErrorCode.OK) { 
    		System.out.println(new java.lang.Error("Motion Profile Error: " + code.toString()));
    	}
    }
}

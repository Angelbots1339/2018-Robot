package org.usfirst.frc.team1339.utils;

import org.usfirst.frc.team1339.robot.RobotMap;

import com.mindsensors.CANLight;

import edu.wpi.first.wpilibj.Timer;

public class LEDs {
	
	private class Color {
		public int red;
		public int green;
		public int blue;
		
		public Color(int red, int green, int blue) {
			this.red = red;
			this.green = green;
			this.blue = blue;
		}
	}
	
	private class Fader {
		
		public boolean done;

		private CANLight strip;
		private Color startColor, endColor;
		private int currentRed, currentGreen, currentBlue;
		private double dRed, dGreen, dBlue;
		private double startTime, dT;
		
		public Fader(CANLight strip) {
			this.strip = strip;
		}
		
		public void startFade(Color from, Color to, double seconds) {
			done = false;
			
			startColor = from;
			endColor = to;
			
			currentRed = startColor.red;
			currentGreen = startColor.green;
			currentBlue = startColor.blue;
			
			dRed = endColor.red - currentRed;
			dGreen = endColor.green - currentGreen;
			dBlue = endColor.blue - currentBlue;
			
			startTime = Timer.getFPGATimestamp();
			dT = seconds;
			
			strip.showRGB(currentRed, currentGreen, currentBlue);
		}
		
		public void updateFade(double currentTime) {
			if(!done) {
				double percent = (currentTime - startTime) / dT;
				if(percent >= 1) {
					currentRed = endColor.red;
					currentGreen = endColor.green;
					currentBlue = endColor.blue;
					done = true;
				} else {
					currentRed = (int) (startColor.red + (dRed * percent));
					currentGreen = (int) (startColor.green + (dGreen * percent));
					currentBlue = (int) (startColor.blue + (dBlue * percent));
				}
				strip.showRGB(currentRed, currentGreen, currentBlue);
			}
		}
	}
	
	private CANLight rStrip, lStrip;
	
	private Color red, white;
	
	private Fader lFader, rFader;
	
	private boolean leftIsRed, rightIsRed;
	
	public LEDs() {
		rStrip = new CANLight(RobotMap.rightLEDStripId);
		lStrip = new CANLight(RobotMap.leftLEDStripId);
		
		lFader = new Fader(lStrip);
		rFader = new Fader(rStrip);
		
		white = new Color(255, 255, 50);
		red = new Color(255, 0, 0);
	}
	
	public void disabledInit() {
		lFader.startFade(red, white, 1);
		rFader.startFade(white, red, 1);
		leftIsRed = false;
		rightIsRed = true;
	}
	
	public void disabledPeriodic() {
		double time = Timer.getFPGATimestamp();
		
		lFader.updateFade(time);
		if(lFader.done) {
			if(leftIsRed) lFader.startFade(red, white, 1);
			else lFader.startFade(white, red, 1);
			leftIsRed = !leftIsRed;
		}
		
		rFader.updateFade(time);
		if(rFader.done) {
			if(rightIsRed) rFader.startFade(red, white, 1);
			else rFader.startFade(white, red, 1);
			rightIsRed = !rightIsRed;
		}
	}
	
	public void autoInit() {
		
	}
	
	public void autoPeriodic() {
		
	}
	
	public void teleOpInit() {
		
	}
	
	public void teleOpPeriodic() {
		
	}
	
	private void leftShowColor(Color color) {
		lStrip.showRGB(color.red, color.green, color.blue);
	}
	
	private void rightShowColor(Color color) {
		rStrip.showRGB(color.red, color.green, color.blue);
	}
}

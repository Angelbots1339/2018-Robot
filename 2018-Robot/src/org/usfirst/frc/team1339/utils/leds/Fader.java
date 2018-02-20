package org.usfirst.frc.team1339.utils.leds;

import com.mindsensors.CANLight;

import edu.wpi.first.wpilibj.Timer;

public class Fader {
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

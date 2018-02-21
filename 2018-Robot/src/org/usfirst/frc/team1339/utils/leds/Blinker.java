package org.usfirst.frc.team1339.utils.leds;

import com.mindsensors.CANLight;

import edu.wpi.first.wpilibj.Timer;

public class Blinker {
	
	private CANLight strip;
	private double endTime;
	public boolean done;
	
	public Blinker(CANLight ledStrip) {
		strip = ledStrip;
		done = true;
	}
	
	public void blink(Color color, double time) {
		done = false;
		endTime = Timer.getFPGATimestamp() + time;
		strip.showRGB(color.red, color.green, color.blue);
	}
	
	public void updateBlinker(double time) {
		if(!done) {
			if(time > endTime) {
				done = true;
			}
		}
	}
}

package org.usfirst.frc.team1339.utils.leds;

import com.mindsensors.CANLight;

import edu.wpi.first.wpilibj.Timer;

public class AngelLight {

	private Fader fader;
	private Blinker blinker;
	private int mode; //0 = hold color, 1 = blink, 2 = fade
	
	private CANLight light;
	
	public boolean done;
	
	public AngelLight(int deviceNumber) {
		light = new CANLight(deviceNumber);
		light.showRGB(0, 0, 0);
		fader = new Fader(light);
		blinker = new Blinker(light);
		mode = 0;
	}
	
	public AngelLight(int deviceNumber, Color initialColor) {
		light = new CANLight(deviceNumber);
		light.showRGB(initialColor.red, initialColor.green, initialColor.blue);
		fader = new Fader(light);
		blinker = new Blinker(light);
		mode = 0;
	}

	public void blink(Color blinkColor, double time) {
		mode = 1;
		done = false;
		blinker.blink(blinkColor, time);
	}
	
	public void fade(Color from, Color to, double seconds) {
		mode = 2;
		done = false;
		fader.startFade(from, to, seconds);
	}
	
	public void showColor(Color color) {
		mode = 0;
		done = true;
		light.showRGB(color.red, color.green, color.blue);
	}
	
	public void updateLED() {
		double time = Timer.getFPGATimestamp();
		if(mode == 1) {
			blinker.updateBlinker(time);
			if(blinker.done) {
				done = true;
				mode = 0;
			}
		} else if(mode == 2) {
			fader.updateFade(time);
			if(fader.done) {
				done = true;
				mode = 0;
			}
		}
	}
}

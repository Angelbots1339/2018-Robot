package org.usfirst.frc.team1339.utils;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.utils.leds.AngelLight;
import org.usfirst.frc.team1339.utils.leds.Color;

public class LEDs {
	
	private AngelLight rStrip, lStrip;
	
	private Color red, white;
	
	private boolean leftIsRed;
	
	public LEDs() {
		rStrip = new AngelLight(RobotMap.rightLEDStripId);
		lStrip = new AngelLight(RobotMap.leftLEDStripId);
		
		white = new Color(255, 255, 75);
		red = new Color(255, 0, 0);
	}
	
	public void disabledInit() {
		lStrip.fade(white, red, 1);
		rStrip.fade(white, red, 1);
		leftIsRed = true;
	}
	
	public void disabledPeriodic() {
		lStrip.updateLED();
		rStrip.updateLED();
		if(lStrip.done || rStrip.done) {
			if(leftIsRed) {
				lStrip.fade(red, white, 1);
				rStrip.fade(red, white, 1);
			} else {
				lStrip.fade(white, red, 1);
				rStrip.fade(white, red, 1);
			}
			leftIsRed = !leftIsRed;
		}
	}
	
	public void autoInit() {
		
	}
	
	public void autoPeriodic() {
		
	}
	
	public void teleOpInit() {
		lStrip.showColor(red);
		rStrip.showColor(red);
	}
	
	public void teleOpPeriodic() {
		
	}
}

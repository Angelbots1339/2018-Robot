package org.usfirst.frc.team1339.utils;

import org.usfirst.frc.team1339.robot.RobotMap;
import org.usfirst.frc.team1339.utils.leds.AngelLight;
import org.usfirst.frc.team1339.utils.leds.Color;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class LEDs {
	
	private AngelLight rStrip, lStrip;
	
	private Color red, darkRed, white, blue, green;
	
	private boolean leftIsRed, hazBox;
	
	public LEDs() {
		rStrip = new AngelLight(RobotMap.rightLEDStripId);
		lStrip = new AngelLight(RobotMap.leftLEDStripId);
		
		white = new Color(200, 200, 75);
		red = new Color(255, 0, 0);
		darkRed = new Color(40, 0, 0);
		blue = new Color(0, 0, 255);
		green = new Color(0, 255, 0);
		
		hazBox = false;
	}
	
	public void disabledInit() {
		lStrip.fade(darkRed, red, 1.5);
		rStrip.fade(darkRed, red, 1);
		leftIsRed = true;
	}
	
	public void disabledPeriodic() {
		lStrip.updateLED();
		rStrip.updateLED();
		if(lStrip.done || rStrip.done) {
			if(leftIsRed) {
				lStrip.fade(red, darkRed, 1.5);
				rStrip.fade(red, darkRed, 1.5);
			} else {
				lStrip.fade(darkRed, red, 1.5);
				rStrip.fade(darkRed, red, 1.5);
			}
			leftIsRed = !leftIsRed;
		}
	}
	
	public void autoInit() {
		if(DriverStation.getInstance().getAlliance() == Alliance.Blue) {
			lStrip.showColor(blue);
			rStrip.showColor(blue);
		}
		else {
			lStrip.showColor(red);
			rStrip.showColor(red);
		}
	}
	
	public void autoPeriodic() {
		
	}
	
	public void teleOpInit() {
		lStrip.showColor(white);
		rStrip.showColor(white);
	}
	
	public void teleOpPeriodic() {
		if(hazBox) {
			lStrip.showColor(green);
			rStrip.showColor(green);
		} else {
			lStrip.showColor(white);
			rStrip.showColor(white);
		}
	}
	
	public void hazBox(boolean haz) {
		hazBox = haz;
	}
}

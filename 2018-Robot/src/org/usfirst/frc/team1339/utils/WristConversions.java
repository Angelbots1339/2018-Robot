package org.usfirst.frc.team1339.utils;

public class WristConversions {
	private static double rotSmallPerRotBig = 140 / 4.3689;
	private static double clicksPerRotSmall = 4096;
	private static double degreesPerRotation = 360;
	
	public static double clicksToDegrees(int clicks) {
		return (clicks / (rotSmallPerRotBig * clicksPerRotSmall)) * degreesPerRotation;
	}
	
	public static int degreesToClicks(double degrees) {
		return (int) ((degrees / degreesPerRotation) * rotSmallPerRotBig * clicksPerRotSmall);
	}
}

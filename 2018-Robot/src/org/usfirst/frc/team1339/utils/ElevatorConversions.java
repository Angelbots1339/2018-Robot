package org.usfirst.frc.team1339.utils;

public class ElevatorConversions {
	private static double rotSmallPerRotBig = 5;
	private static double clicksPerRotSmall = 4092;
	private static double spoolDiameter = 1.25;
	private static double cmsPerInch = 2.54;
	private static double cmsPerRotation = (spoolDiameter * cmsPerInch) * Math.PI;
	
	public static double clicksToCMs(int clicks) {
		return (clicks / (rotSmallPerRotBig * clicksPerRotSmall)) * cmsPerRotation;
	}
	
	public static int cmsToClicks(double cms) {
		return (int) ((cms / cmsPerRotation) * rotSmallPerRotBig * clicksPerRotSmall);
	}
}


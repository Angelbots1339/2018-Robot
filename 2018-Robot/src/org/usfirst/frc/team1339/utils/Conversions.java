package org.usfirst.frc.team1339.utils;

public class Conversions {
	
	public static double wheelDiameter = 6;
	public static int clicksPerRot = 1024;
	public static double metersPerInches = 0.0254;
	public static double inchesPerMeters = 1/metersPerInches;
	
	public static double clicksToMeters(double clicks) {
		return inchesToMeters((clicks / clicksPerRot) * wheelDiameter * Math.PI);
	}
	
	public static double inchesToMeters(double inches) {
		return (inches * metersPerInches);
	}
	
	public static double clickVelToMetersPerSec(double clickVel) {
		return clicksToMeters(clickVel * 10.0);
	}
	
	public static double metersToInches(double meters) {
		return (meters * inchesPerMeters);
	}
	
	public static double metersToClicks(double meters) {
		return (metersToInches(meters) / (wheelDiameter * Math.PI)) * clicksPerRot;
	}
	
	public static double metersPerSecToClickVel(double metersPerSec) {
		return metersToClicks(metersPerSec/10.0);
	}
}

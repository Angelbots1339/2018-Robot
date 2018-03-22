package org.usfirst.frc.team1339.utils;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A class to contain a list of autonomous modes and to keep track of the currently selected one.
 * @author skorman and angelo
 */
public class DelaySelector {
	
	private String delay;
	private int delayInt;
	
	/**
	 * Creates an instance of {@link DelaySelector} with an empty {@link ArrayList} and the current mode set to -1.
	 */
	public DelaySelector(){
		delayInt = 0;
		delay = "0";
	}
	
	/**
	 * Returns the mode that is currently selected. Use {@link #setCurrentMode(int)} to change this value.
	 * 
	 * @return The current mode
	 */
	public int getCurrentDelay(){
		return Integer.parseInt(delay);
	}
}

package org.usfirst.frc.team1339.utils;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A class to contain a list of autonomous modes and to keep track of the currently selected one.
 * @author N8 the Gr8
 */
public class AutonomousModeSelector {
	
	private ArrayList<Mode> modes;
	private int currentMode;
	
	/**
	 * A class to contain the name and actual command of a mode.
	 * @author N8 the Gr8
	 *
	 */
	private static class Mode{
		public String name;
		public Command command;
		
		public Mode(String name, Command command){
			this.name = name;
			this.command = command;
		}
	}
	
	/**
	 * Creates an instance of {@link AutonomousModeSelector} with an empty {@link ArrayList} and the current mode set to -1.
	 */
	public AutonomousModeSelector(){
		modes = new ArrayList<Mode>();
		currentMode = -1;
	}
	
	/**
	 * Use this to add autonomous modes to the running list of modes.
	 * 
	 * @param mode The autonomous mode that you want to add to the list.
	 */
	public void add(String name, Command mode){
		modes.add(new Mode(name, mode));
	}
	
	/**
	 * Returns the mode that is currently selected. Use {@link #setCurrentMode(int)} to change this value.
	 * 
	 * @return The current mode
	 */
	public Command getCurrentModeCommand(){
		return (isModeValid(currentMode)) ? modes.get(currentMode).command : null;
	}
	
	/**
	 * Returns the name of the current mode for webserver purposes.
	 * 
	 * @return The name of the current mode
	 */
	public String getCurrentModeName(){
		return (isModeValid(currentMode)) ? modes.get(currentMode).name : "";
	}
	
	/**
	 * Sets the current mode, if valid. {@link #getCurrentMode()} will now return this mode.
	 * 
	 * @param mode The integer that corresponds to the mode you want to select
	 * @return If the mode was successfully set (false if the given mode was invalid)
	 */
	public boolean setCurrentMode(int mode){
		if(!isModeValid(mode)) return false;
		currentMode = mode;
		return true;
	}
	
	/**
	 * Returns an array of the names' of all the modes for webserver purposes.
	 * 
	 * @return An array of all the names
	 */
	public String[] getNames(){
		String[] names = new String[modes.size()];
		for(int i = 0; i < modes.size(); i++){
			names[i] = modes.get(i).name;
		}
		return names;
	}
	
	/**
	 * Checks to see if the given mode is within the existing collection of modes.
	 * 
	 * @param mode The integer to check
	 * @return If the mode is valid
	 */
	private boolean isModeValid(int mode){
		return (mode >= 0 && mode < modes.size());
	}
}

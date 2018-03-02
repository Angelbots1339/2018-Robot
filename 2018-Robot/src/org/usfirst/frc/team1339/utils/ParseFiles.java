package org.usfirst.frc.team1339.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseFiles {

	private Scanner scanner;
	private ArrayList<LogPoint> leftLog;
	private ArrayList<LogPoint> rightLog;
	
    public ParseFiles() {
        leftLog = new ArrayList<LogPoint>();
        rightLog = new ArrayList<LogPoint>();
    }
    
    public void loadFile(String name) {
    	leftLog.clear();
    	rightLog.clear();
    	try {
    		scanner = new Scanner(new File("/home/lvuser/Records/" + name + ".txt"));
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    		System.out.println("Path not found! You do not kno de wae.");
    	}
    	
    	boolean reversed = (scanner.nextLine().equals("0") ? false:true);
    	while(scanner.hasNextLine()) {
    		String scope = scanner.nextLine();
    		if(!reversed) {
    			leftLog.add(new LogPoint(Double.parseDouble(scope.split(",")[0]),
    					Double.parseDouble(scope.split(",")[1]),
    					Double.parseDouble(scope.split(",")[4])));
    			rightLog.add(new LogPoint(Double.parseDouble(scope.split(",")[2]),
    					Double.parseDouble(scope.split(",")[3]),
    					Double.parseDouble(scope.split(",")[4])));
    		} else {
    			rightLog.add(new LogPoint(-Double.parseDouble(scope.split(",")[0]),
    					-Double.parseDouble(scope.split(",")[1]),
    					Double.parseDouble(scope.split(",")[4])));
    			leftLog.add(new LogPoint(-Double.parseDouble(scope.split(",")[2]),
    					-Double.parseDouble(scope.split(",")[3]),
    					Double.parseDouble(scope.split(",")[4])));
    		}
    	}
    }
    
    public ArrayList<LogPoint> getLog(boolean isLeft) {
    	return (isLeft ? leftLog : rightLog);
    }
}

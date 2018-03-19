package org.usfirst.frc.team1339.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseFiles {

	private Scanner scanner;
	private LogPoint[] leftLog;
	private LogPoint[] rightLog;
	private int size;
	
    public ParseFiles(String name) {

    	try {
    		scanner = new Scanner(new File("/home/lvuser/Records/" + name + ".txt"));
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    		System.out.println("Path not found! You do not kno de wae.");
    	}
    	
    	String info = scanner.nextLine();
    	System.out.println("Something Before"+info+"SA");
    	size = Integer.parseInt(info.split(",")[0]);
    	boolean reversed = (info.split(",")[1].equals("0") ? false:true);
    	
    	leftLog = new LogPoint[size];
        rightLog = new LogPoint[size];
        
    	
    	
//    	while(scanner.hasNextLine()) {
//    		String scope = scanner.nextLine();
//    		if(!reversed) {
//    			leftLog.add(new LogPoint(Double.parseDouble(scope.split(",")[0]),
//    					Double.parseDouble(scope.split(",")[1]),
//    					Double.parseDouble(scope.split(",")[4])));
//    			rightLog.add(new LogPoint(Double.parseDouble(scope.split(",")[2]),
//    					Double.parseDouble(scope.split(",")[3]),
//    					Double.parseDouble(scope.split(",")[4])));
//    		} else {
//    			rightLog.add(new LogPoint(-Double.parseDouble(scope.split(",")[0]),
//    					-Double.parseDouble(scope.split(",")[1]),
//    					Double.parseDouble(scope.split(",")[4])));
//    			leftLog.add(new LogPoint(-Double.parseDouble(scope.split(",")[2]),
//    					-Double.parseDouble(scope.split(",")[3]),
//    					Double.parseDouble(scope.split(",")[4])));
//    		}
    		for(int i=0; i<size; i++) {
    			String scope = scanner.nextLine();
        		if(!reversed) {
        			leftLog[i] = (new LogPoint(Double.parseDouble(scope.split(",")[0]),
        					Double.parseDouble(scope.split(",")[1]),
        					Double.parseDouble(scope.split(",")[4])));
        			rightLog[i] = (new LogPoint(Double.parseDouble(scope.split(",")[2]),
        					Double.parseDouble(scope.split(",")[3]),
        					Double.parseDouble(scope.split(",")[4])));
        		} else {
        			rightLog[i] = (new LogPoint(-Double.parseDouble(scope.split(",")[0]),
        					-Double.parseDouble(scope.split(",")[1]),
        					Double.parseDouble(scope.split(",")[4])));
        			leftLog[i] = (new LogPoint(-Double.parseDouble(scope.split(",")[2]),
        					-Double.parseDouble(scope.split(",")[3]),
        					Double.parseDouble(scope.split(",")[4])));
        		}
    		}
    }
    
    
    public LogPoint[] getLog(boolean isLeft) {
    	return (isLeft ? leftLog : rightLog);
    }
    
    public int getSize() { return size;}
}

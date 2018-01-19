package org.usfirst.frc.team1339.utils;

import org.json.simple.JSONObject;

public class ValueDisplay {
	
	private JSONObject json;
	
	public ValueDisplay(){
		json = new JSONObject();
	}
	
	public void putValue(String name, Object value){
		json.put(name, value);
	}
	
	public String getJSON(){
		return json.toJSONString();
	}
}

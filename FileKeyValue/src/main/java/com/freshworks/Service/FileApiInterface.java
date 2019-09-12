package com.freshworks.Service;

import java.util.Map;

import org.json.simple.JSONObject;




public interface FileApiInterface {

	public JSONObject writeJsonSimpleDemo(String filename,JSONObject jsobj);
	
	public JSONObject readJson(String filename); 
}

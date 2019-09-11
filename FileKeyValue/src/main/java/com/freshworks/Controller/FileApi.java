package com.freshworks.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.freshworks.Service.FileApiImp;

@RestController

@RequestMapping("/File")
public class FileApi {

	@Autowired
	public FileApiImp apiImp;
	boolean iserror = false;
	JSONObject status = new JSONObject(); 	

	@RequestMapping("/create")
	public synchronized JSONObject Createfile(@RequestParam Map<String, String> allParams) {
		JSONObject jobj = new JSONObject();
		

		allParams.entrySet().forEach(entry -> { // Separate key value from parameters
			if (!isKeyValueFormat(entry.getKey(), entry.getValue())) { // To check Key is valid
				iserror = true;
			} else {
				jobj.put(entry.getKey(), entry.getValue());
				status = apiImp.writeJsonSimpleDemo(entry.getKey(), jobj);
				iserror = false;
			}
		});
		if (iserror) {
			status.put("message", "Given Key or value is not Valid");
			return status;
		} else {

			return  status;
		}
	}

	@GetMapping(path = "/read/{keyname}")
	public synchronized JSONObject Readfile(@PathVariable String keyname) throws JsonParseException, JsonMappingException, FileNotFoundException, IOException {
		Map<?, ?> map = apiImp.readJson(keyname); // Read json using keyname
		JSONObject jsobj =  new JSONObject(map);
		return jsobj;
	}

	@GetMapping(path = "/delete/{keyname}")
	public synchronized JSONObject Deletefile(@PathVariable String keyname) {
		File file = new File("Store/" + keyname + ".json");
		
		boolean isdelete = apiImp.deleteDirectory(file); // Delete operation
		if(isdelete)
		{
			status.put("message", "Successfully Deleted");
			return status;
		}
		else {
	    status.put("message", "Failed to Delete file");
		return status;
		}
	}

	public synchronized boolean isKeyValueFormat(Object keys, Object val) { // Check key is string or not
		//Object json = new JSONTokener((String) val).nextValue();
		if (keys instanceof Integer) {
			return false;
		} else if (keys instanceof String ) {// && (val instanceof JSONObject || val instanceof JSONArray)) { // Check whether both key and value is valid
			if (((String) keys).length() <= 32) {
				return true;
			} else
				return false;
		}
		return false;
	}

}

package com.freshworks.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

@Service
public class FileApiImp implements FileApiInterface {

	@Override
	public JSONObject writeJsonSimpleDemo(String filename, JSONObject jsobj) {
		JSONObject iswrite = new JSONObject();
		try {
			File isfile = new File ("Store/"+ filename + ".json");
			if(!isfile.exists()) {
			File files = new File("Store");
			if (!files.exists() && !files.isDirectory()) {
				files.mkdirs();
				
				}
			
			FileWriter file = new FileWriter(files+"/"+ filename + ".json");

			file.write(jsobj.toString());
			file.flush();
			file.close();
			iswrite.put("message", "File Created Successfully");
			}
			else {
				iswrite.put("message", "File Already Exists");
			}
		} catch (IOException e) {
			e.printStackTrace();
			iswrite.put("message", e.getMessage());
		}
		return iswrite;
	}

	@Override
	public JSONObject  readJson(String filename) {
		JSONObject iswrite = new JSONObject();
		ObjectMapper objectMapper = new ObjectMapper();

		Map<?, ?> map = null;
		System.out.println("File Name is::" + filename);
		try {
			map = objectMapper.readValue(new FileInputStream("Store/" + filename + ".json"), Map.class);
			iswrite.put("response", map);
		} catch (IOException e) {
			e.printStackTrace();
			
			iswrite.put("message", e.getMessage());
			
		}

		return iswrite;
	}

	public boolean deleteDirectory(File path) {
		if (path.exists() &&!path.isDirectory()) {
			if(path.delete()) {
				System.err.println("deleted");
				return true;
			}
			else
				return false;
			}
		return false;
	}

}

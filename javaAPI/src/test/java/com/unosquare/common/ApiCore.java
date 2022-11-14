package com.unosquare.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiCore {
	
	public JSONObject getJsonObjectFromFile (String path) {
		JSONParser json = new JSONParser();
		JSONObject object = null;
		FileReader reader;
		try {
			reader = new FileReader(path);
			object = (JSONObject) json.parse(reader);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public Response sentPostRequest(String postURI, String postPath, String jsonPath) throws Exception {
		JSONObject in = getJsonObjectFromFile(jsonPath);
		if(postURI == null) {
			throw new Exception("URI cannot be null");
		}
		if(postPath == null) {
			postPath = "";
		}
		
		Response res = RestAssured.given().contentType(ContentType.JSON).baseUri(postURI)
			  .and().body(in.toString())
			  .when()
			  	.post(postPath);
		
		return res;
	}
	
	

}

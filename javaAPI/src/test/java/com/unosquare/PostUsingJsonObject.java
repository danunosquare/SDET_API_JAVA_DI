package com.unosquare;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;




public class PostUsingJsonObject {

	@Test
	public void testUsingJson () {
		
		JSONObject in = getJsonObjectFromFile("src/test/resources/jsonFiles/CreateUser.json");
		
		String str = RestAssured.baseURI = "https://reqres.in/api/";
		String path = "/api/users";
		
		Response res = RestAssured.given().contentType(ContentType.JSON).baseUri(str)
			  .and().body(in.toString())
			  .when()
			  	.post(path);
	
		Reporter.log("Response body " + res.getBody().print());
		Reporter.log("Response code " + res.getStatusCode());
		Reporter.log("Json sent: " + in );
		Reporter.log("URL post " + str + path);
		
		
	}
	
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
	
	
}

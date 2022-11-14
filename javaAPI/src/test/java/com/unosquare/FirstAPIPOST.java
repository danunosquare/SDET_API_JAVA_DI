package com.unosquare;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FirstAPIPOST {
	
	@Test
	public void testPostRequestHTMLObject () {
		 JSONObject jsonObject = new JSONObject();
		  jsonObject.put("name","JohnAPI");
		  jsonObject.put("job","QA");
		  
			 RestAssured.baseURI = "https://reqres.in/api"; 
			 RequestSpecification httpRequest = RestAssured.given(); 
			 httpRequest.headers("Content-Type", "application/json");
			 httpRequest.body(jsonObject.toString());
			 Response response = httpRequest.post("/users");
			 Reporter.log(response.getBody().print()); 
			 Reporter.log((String) response.getBody().path("name", ""));
			 Reporter.log(response.getBody().asString());
	}
	
	@Test
	public void testPostRequestGivenWhenThen () {
		  JSONObject jsonObject = new JSONObject();
		  jsonObject.put("name","JohnAPI");
		  jsonObject.put("job","QA");
		  
		  RestAssured.given().contentType(ContentType.JSON).baseUri("https://reqres.in/api/")
		  .and().body(jsonObject.toString())
		  .when()
		  	.post("/users")
		  .then()
		  	.assertThat().statusCode(201)
		  	.assertThat().contentType(ContentType.JSON)
		  	.assertThat().body("name", Matchers.equalTo("JohnAPI"));
		  
		  Reporter.log("Sucess 201 validation"); 
	}
	
	

}

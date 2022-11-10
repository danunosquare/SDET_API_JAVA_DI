package com.unosquare;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class FirsAPITest {
  
  @Test
  public void f_get() {
	  
		RestAssured.baseURI = "https://reqres.in/api/";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/users/2");
		
		int statusCode = response.getStatusCode();
		// Assert that correct status code is returned.
		Assert.assertEquals(statusCode,200);
		Reporter.log("Sucess 200 validation"); 
		
		response.then().
		body("data.first_name", Matchers.equalTo("Janet"))
		.body("data.last_name", Matchers.equalTo("Weaver"));
	
		Reporter.log(response.body().asString());

  }
  
  
  @Test
  public void f_Gherking_get() {
	  
	  RestAssured.given().contentType(ContentType.JSON).baseUri("https://reqres.in/api/")
	  .when()
	  	.get("/users/2")
	  .then()
	  	.assertThat().statusCode(200)
	  	.assertThat().contentType(ContentType.JSON)
	  	.assertThat().body("data.first_name", Matchers.equalTo("Janet"))
	  	.assertThat().body("data.last_name", Matchers.equalTo("Weaver"));
	  
	  Reporter.log("Sucess 200 validation"); 
	
  }
  
  @Test
  public void f_post() {
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
  public void f_post_Gherking() {
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
  
  
  @BeforeMethod
  public void beforeMethod() {	
  }

}

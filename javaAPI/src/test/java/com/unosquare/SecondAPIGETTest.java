package com.unosquare;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SecondAPIGETTest {

	@Test
	public void getUserRequest() {

		RestAssured.baseURI = "https://reqres.in/api/";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/unknown/2");

		int statusCode = response.getStatusCode();
		// Assert that correct status code is returned.
		Assert.assertEquals(statusCode, 200);
		Reporter.log("Sucess " + statusCode + " validation");

		response.then().body("data.id", Matchers.equalTo(2))
				.body("data.name", Matchers.equalTo("fuchsia rose"))
				.body("data.year", Matchers.equalTo(2001))
				.body("data.color", Matchers.equalTo("#C74375"))
				.body("data.pantone_value", Matchers.equalTo("17-2031"))
				.body("support.url", Matchers.equalTo("https://reqres.in/#support-heading"))
				.body("support.text", Matchers.containsString("To keep ReqRes free,"));

		Reporter.log(response.body().asString());

	}
	
	 @Test
	  public void f_Gherking_get_list() {
		  
		  RestAssured.given().contentType(ContentType.JSON).baseUri("https://reqres.in/api/")
		  .when()
		  	.get("/unknown/2")
		  .then()
		  	.assertThat().statusCode(200)
		  	.assertThat().contentType(ContentType.JSON)
		  	.body("data.name", Matchers.equalTo("fuchsia rose"))
			.body("data.year", Matchers.equalTo(2001))
			.body("data.color", Matchers.equalTo("#C74375"))
			.body("data.pantone_value", Matchers.equalTo("17-2031"))
			.body("support.url", Matchers.equalTo("https://reqres.in/#support-heading"))
			.body("support.text", Matchers.containsString("To keep ReqRes free,"));
		  Reporter.log("Sucess 200 validation"); 
		
	  }
	
	
}

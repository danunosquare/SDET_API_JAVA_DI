package com.unosquare;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FirstAPIGetTest {

	@Test
	public void testFirsGetUsingHTTPObjects() {

		RestAssured.baseURI = "https://reqres.in/api/";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/users/2");

		int statusCode = response.getStatusCode();
		// Assert that correct status code is returned.
		Assert.assertEquals(statusCode, 200);
		Reporter.log("Sucess " + statusCode + " validation");

		response.then().body("data.first_name", Matchers.equalTo("Janet"))
				.body("data.last_name", Matchers.equalTo("Weaver")).body("data.id", Matchers.equalTo(2))
				.body("data.email", Matchers.equalTo("janet.weaver@reqres.in"))
				.body("data.avatar", Matchers.equalTo("https://reqres.in/img/faces/2-image.jpg"))
				.body("support.url", Matchers.equalTo("https://reqres.in/#support-heading"))
				.body("support.text", Matchers.containsString("To keep ReqRes free,"));

		Reporter.log(response.body().asString());

	}
}

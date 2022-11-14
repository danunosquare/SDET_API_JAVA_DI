package com.unosquare;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.unosquare.common.ApiCore;


import io.restassured.response.Response;

public class PostRefactorizedUsingAPICore {
	
	ApiCore apicore;
	private final String URI = "https://reqres.in";
	
	@BeforeSuite
	public void before() {
		apicore = new ApiCore();
	}
	
	@Test
	public void testPostWihtAPICore () throws Exception {
		
		String path = "/api/users";
		String jsonPath = "src/test/resources/jsonFiles/CreateUser.json";
		int codeExpected = 201;
		
		Response test = apicore.sentPostRequest(URI, path, jsonPath);
		Assert.assertEquals(test.getStatusCode(), codeExpected);
		Reporter.log("Response body " + test.getBody().print());
	}
	
	
	@Test
	public void testLogin () throws Exception{
		String path = "/api/login";
		String jsonPath = "src/test/resources/jsonFiles/LoginUser.json";
		int codeExpected = 200;
		
		Response test = apicore.sentPostRequest(URI, path,  jsonPath);
		Assert.assertEquals(test.getStatusCode(), codeExpected);
		Reporter.log("Response body " + test.getBody().print());
		Reporter.log("token: " + test.getBody().jsonPath().getString("token"));
		
	
		
	}

}

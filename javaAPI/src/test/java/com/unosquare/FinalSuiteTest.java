package com.unosquare;

import static org.testng.Assert.fail;

import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

import org.testng.annotations.Test;

import com.unosquare.common.ApiCore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class FinalSuiteTest {

	ApiCore apicore;
	private final String URI = "https://reqres.in";
	
	@BeforeSuite
	public void before() {
		apicore = new ApiCore();
	}
	
	@Test
	public void getSingleUserTest () throws Exception{
		int codeExpected = 200;
		String path = "/api/users/2";
	
		Response res = RestAssured.given().contentType(ContentType.JSON).baseUri(URI)
		  .when()
		  	.get(path);
		Assert.assertEquals(res.getStatusCode(), codeExpected);
		Reporter.log("body with user 2"+ res.getBody().print());
	}
	
	@Test
	public void getUserListTest () throws Exception {
	
		String path = "/api/users?page=2";
		int codeExpected = 200;
		Response test = RestAssured.given().contentType(ContentType.JSON).baseUri(URI)
				  .when()
				  	.get(path);
		Assert.assertEquals(test.getStatusCode(), codeExpected);
		int per_page = test.getBody().jsonPath().getInt("per_page");
		int usersOnList = test.getBody().jsonPath().getList("data").size();
		Assert.assertEquals(per_page, usersOnList);
		Reporter.log("Response body " + test.getBody().print());
	}
	
	
	@Test
	public void getResourceListTest () throws Exception{
		String path = "/api/unknown";
		int codeExpected = 200;
		Response test = RestAssured.given().contentType(ContentType.JSON).baseUri(URI)
				  .when()
				  	.get(path);
		Assert.assertEquals(test.getStatusCode(), codeExpected);
		List<Integer> years = test.getBody().jsonPath().getList("data.year");
		for(int year : years) {
			if(!(year >= 2000 && year <= 2005)) {
				fail("Year need to be between 2000 and 2005");
			}
		}
		
		Reporter.log("Response body " + test.getBody().print());
		
	}
	
	@Test
	public void getSingleResourceTest () throws Exception{
		String path = "/api/unknown/3";
		int codeExpected = 200;
		String colorExpected = "true red";
		Response test = RestAssured.given().contentType(ContentType.JSON).baseUri(URI)
				  .when()
				  	.get(path);
		Assert.assertEquals(test.getStatusCode(), codeExpected);
		Assert.assertEquals(test.getBody().jsonPath().getString("data.name"), colorExpected);
		Reporter.log("Response body " + test.getBody().print());
	
	
	}
	
	@Test
	public void postCreateUserTest () throws Exception{
		
		String path = "/api/users";
		String jsonPath = "src/test/resources/jsonFiles/CreateUser.json";
		int codeExpected = 201;
		Response test = apicore.sentPostRequest(URI, path, jsonPath);
		Assert.assertEquals(test.getStatusCode(), codeExpected);
		Assert.assertEquals(test.getBody().jsonPath().getString("job"), "QA");
		Reporter.log("Response body " + test.getBody().print());
	}
	@Test
	public void postRegisterTest () throws Exception{
		
		String path = "/api/register";
		String jsonPath = "src/test/resources/jsonFiles/Register.json";
		int codeExpected = 200;
		Response test = apicore.sentPostRequest(URI, path, jsonPath);
		Assert.assertEquals(test.getStatusCode(), codeExpected);
		Reporter.log("Response body " + test.getBody().print());
		Reporter.log("token: " + test.getBody().jsonPath().getString("token"));

	}
	@Test
	public void postSuccessfulLoginTest () throws Exception{
		
		String path = "/api/login";
		String jsonPath = "src/test/resources/jsonFiles/LoginUser.json";
		int codeExpected = 200;
		
		Response test = apicore.sentPostRequest(URI, path,  jsonPath);
		Assert.assertEquals(test.getStatusCode(), codeExpected);
		Reporter.log("Response body " + test.getBody().print());
		Reporter.log("token: " + test.getBody().jsonPath().getString("token"));

	}
	@Test
	public void postFailLoginTest () throws Exception{
		String path = "/api/login";
		String jsonPath = "src/test/resources/jsonFiles/FakeUserLogin.json";
		int codeExpected = 400;
		Response test = apicore.sentPostRequest(URI, path,  jsonPath);
		Assert.assertEquals(test.getBody().jsonPath().getString("error"), "Missing password");
		Assert.assertEquals(test.getStatusCode(), codeExpected);
		Reporter.log("Response body " + test.getBody().print());
	}
	
	@Test
	public void putUpdateUserTest () throws Exception{
		String path = "/api/users/2";
		String jsonPath = "src/test/resources/jsonFiles/UpdateUser.json";
		int codeExpected = 201;
		Response test = apicore.sentPostRequest(URI, path,  jsonPath);
		Assert.assertEquals(test.getBody().jsonPath().getString("job"), "president");
		Assert.assertEquals(test.getStatusCode(), codeExpected);
		Reporter.log("Response body " + test.getBody().print());
	}
	
	
	
}

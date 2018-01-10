package io.swagger.api.common;

import static org.junit.Assert.*;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ClientTypesApiControllerTest {

	String token = "Bearer 8539360e-9a8e-48c5-bc2f-d2dbdcae94fb";
	RequestSpecification httpRequest = RestAssured.given().
										contentType("application/json").
										headers("accept", "application/json","Authorization", token);

	@Test
	public void testCreateClientType() {
		String newClientT = "{ \"id\": 4, \"description\": \"jakis tam opis\"}";
		int check = httpRequest.
								and().
								body(newClientT).
								when().
								post("http://localhost:8080/api/clientTypes").getStatusCode();
		assertEquals(check,200);
	}

	@Test
	public void testDeleteClientType() {
		httpRequest.
		when().
		delete("http://localhost:8080/api/clientTypes/7");

	Response response = httpRequest.
						when().
						get("http://localhost:8080/api/clientTypes/7");

	JsonPath jsonPath = new JsonPath(response.getBody().asString());
	String msg = jsonPath.getString("message");
	assertEquals(msg,"Model not found");
	}

	@Test
	public void testGetClientType() {
		Response response = httpRequest.when().get("http://localhost:8080/api/clientTypes/4");
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		int resID = jsonPath.getInt("id");
		assertEquals(resID,4);
	}

	@Test
	public void testGetClientTypes() {
		Response response = httpRequest.get("http://localhost:8080/api/clientTypes");
		String resp = httpRequest.get("http://localhost:8080/api/clientTypes").asString();
		System.out.println("resp1 " + resp);
		int statusCode = response.getStatusCode();
		assertEquals(statusCode , 200 );
	}

	@Test
	public void testUpdateClientType() {
		String myJson = "{ \"id\": 4, \"description\": \"abcd\"}";

		Response response = httpRequest.
				body(myJson).
				when().
				put("http://localhost:8080/api/clientTypes/4");

		response = httpRequest.when().get("http://localhost:8080/api/clientTypes/4");
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		String resDesc = jsonPath.getString("description");
		System.out.println("description: " + resDesc);
		assertEquals(resDesc,"abcd");
	}

}

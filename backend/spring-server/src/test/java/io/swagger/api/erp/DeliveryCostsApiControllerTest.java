package io.swagger.api.erp;

import static org.junit.Assert.*;

import org.hamcrest.core.Is;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeliveryCostsApiControllerTest {
	String token = "Bearer 44bafa20-7861-4b65-b7a1-fdc3f258c6a9";
	RequestSpecification httpRequest = RestAssured.given().
										contentType("application/json").
										headers("accept", "application/json",
												"Authorization", token);

	@Test
	public void testCreateDeliveryCost() {
		String json = "{ \"id\": 1, \"weightFrom\": 0, \"weightTo\": 0, \"price\": 0}";
		Response response = httpRequest.
								and().
								body(json).
								when().
								post("http://localhost:8080/api/erp/deliveryCosts");
		int check = response.getStatusCode();
		System.out.println("Delivery Costs id -> " + response.asString()); // 9
		assertEquals(check,200);
	}

	@Test
	public void testDeleteDeliveryCost() {
		httpRequest.
		when().
		delete("http://localhost:8080/api/erp/deliveryCosts/9");

	Response response = httpRequest.
						when().
						get("http://localhost:8080/api/erp/deliveryCosts/9");

	JsonPath jsonPath = new JsonPath(response.getBody().asString());
	String msg = jsonPath.getString("message");
	assertEquals(msg,"Model not found");
	}

	@Test
	public void testGetDeliveryCost() {
		Response response = httpRequest.
				when().
				get("http://localhost:8080/api/erp/deliveryCosts/9");
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		int resID = jsonPath.getInt("id");
		assertEquals(resID,9);
	}

	@Test
	public void testGetDeliveryCosts() {
		Response response = httpRequest.
				get("http://localhost:8080/api/erp/deliveryCosts");
		int statusCode = response.getStatusCode();
		assertEquals(statusCode , 200 );
	}

	/*
	 * Kod 500 przy PUT
	 */
	@Test
	public void testUpdateDeliveryCost() {
		String myJson = "{ \"id\": 14, \"weightFrom\": 5.0, \"weightTo\": 10.0, \"price\": 1.00}";

		httpRequest.
				body(myJson).
				when().
				put("http://localhost:8080/api/erp/deliveryCosts/9");

		JsonPath jsonPath = new JsonPath(
				httpRequest.
				when().
				get("http://localhost:8080/api/erp/deliveryCosts/9").
				getBody().
				asString()
				);
		int res = jsonPath.getInt("id");
		assertEquals(res,14);
	}

}

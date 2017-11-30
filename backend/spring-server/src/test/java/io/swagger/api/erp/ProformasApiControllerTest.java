package io.swagger.api.erp;

import static org.junit.Assert.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.junit.Test;

public class ProformasApiControllerTest {
	String token = "Bearer 44bafa20-7861-4b65-b7a1-fdc3f258c6a9";
	RequestSpecification httpRequest = RestAssured.given().
										contentType("application/json").
										headers("accept", "application/json",
												"Authorization", token);

	@Test
	public void testCreateProforma() {
		String json = "{ \"id\": 0, \"proformaNumber\": \"string\", \"orderId\": {orderID}, \"issueDate\": \"string\", \"saleDate\": \"string\", \"paymentDate\": \"string\", \"paymentMethod\": \"string\"}";
		Response response = httpRequest.
								and().
								body(json).
								when().
								post("http://localhost:8080/api/erp/proformas");
		int check = response.getStatusCode();
		System.out.println("Proforma ID -> " + response.asString());
		assertEquals(check,200);
	}

	@Test
	public void testDeleteProforma() {
		httpRequest.
		when().
		delete("http://localhost:8080/api/erp/proformas");

	Response response = httpRequest.
						when().
						get("http://localhost:8080/api/erp/proformas/{proformaID}");

	JsonPath jsonPath = new JsonPath(response.getBody().asString());
	String msg = jsonPath.getString("message");
	assertEquals(msg,"Model not found");
	}

	@Test
	public void testGetProforma() {
		Response response = httpRequest.
				when().
				get("http://localhost:8080/api/erp/proformas/{proformaID}");
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		int resID = jsonPath.getInt("id");
		assertEquals(resID, {proformaID} );
	}

	@Test
	public void testGetProformas() {
		Response response = httpRequest.
				get("http://localhost:8080/api/erp/proformas");
		int statusCode = response.getStatusCode();
		assertEquals(statusCode , 200 );
	}

	@Test
	public void testUpdateProforma() {
		String myJson = "{ \"id\": 0, \"proformaNumber\": \"update\", \"orderId\": {orderID}, \"issueDate\": \"string\", \"saleDate\": \"string\", \"paymentDate\": \"string\", \"paymentMethod\": \"string\"}";

		httpRequest.
				body(myJson).
				when().
				put("http://localhost:8080/api/erp/proformas/{proformaId}");

		JsonPath jsonPath = new JsonPath(
				httpRequest.
				when().
				get("http://localhost:8080/api/erp/proformas/{proformaId}").
				getBody().
				asString()
				);
		String res = jsonPath.getString("proformaNumber");
		assertEquals(res,"update");
	}

}

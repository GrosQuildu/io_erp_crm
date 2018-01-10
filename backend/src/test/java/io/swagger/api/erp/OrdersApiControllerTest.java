package io.swagger.api.erp;

import static org.junit.Assert.*;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class OrdersApiControllerTest {
	String token = "Bearer 44bafa20-7861-4b65-b7a1-fdc3f258c6a9";
	RequestSpecification httpRequest = RestAssured.given().
										contentType("application/json").
										headers("accept", "application/json",
												"Authorization", token);

	@Test
	public void testCreateOrder() {

		String json = "{ \"order\": { \"id\": 0, \"orderNumber\": \"string\", \"orderDate\": \"string\", \"realizationDate\": \"string\", \"realizationDeadline\": \"string\", \"employeeId\": 0, \"clientId\": 0, \"conditions\": \"string\", \"comments\": \"string\", \"advance\": 0, \"vat\": 0, \"state\": \"string\", \"deliveryCost\": 0, \"deliveryAddress\": \"string\", \"isSigned\": false, \"isPaid\": \"string\", \"isDone\": false }}";
		Response response = httpRequest.
								and().
								body(json).
								when().
								post("http://localhost:8080/api/erp/orders");
		int check = response.getStatusCode();
		System.out.println("Order ID -> " + response.asString());
		assertEquals(check,200);
	}

	@Test
	public void testDeleteOrder() {
		httpRequest.
		when().
		delete("http://localhost:8080/api/erp/orders/1");

	Response response = httpRequest.
						when().
						get("http://localhost:8080/api/erp/orders/1");

	JsonPath jsonPath = new JsonPath(response.getBody().asString());
	String msg = jsonPath.getString("message");
	assertEquals(msg,"Model not found");
	}

	@Test
	public void testGetOrder() {
		Response response = httpRequest.when().get("http://localhost:8080/api/erp/orders/1");
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		int resID = jsonPath.getInt("id");
		assertEquals(resID,1);
	}

	@Test
	public void testGetOrderDeliveryCosts() {
		Response response = httpRequest.when().get("http://localhost:8080/api/erp/orders/1/deliveryCosts");

		int check = response.getStatusCode();
		System.out.println("Delivery Costs -> " + response.asString());
		assertEquals(check,200);
	}

	@Test
	public void testGetOrderNetPrice() {
		Response response = httpRequest.when().get("http://localhost:8080/api/erp/orders/1/netPrice");

		int check = response.getStatusCode();
		System.out.println("Net Price -> " + response.asString());
		assertEquals(check,200);
	}

	@Test
	public void testGetOrders() {
		Response response = httpRequest.get("http://localhost:8080/api/erp/orders");
		int statusCode = response.getStatusCode();
		assertEquals(statusCode , 200 );
	}

	@Test
	public void testUpdateOrder() {
		String myJson = "{ \"order\": { \"id\": 1, \"orderNumber\": \"string\", \"orderDate\": \"string\", \"realizationDate\": \"string\", \"realizationDeadline\": \"string\", \"employeeId\": 0, \"clientId\": 0, \"conditions\": \"update\", \"comments\": \"string\", \"advance\": 0, \"vat\": 0, \"state\": \"string\", \"deliveryCost\": 0, \"deliveryAddress\": \"string\", \"isSigned\": false, \"isPaid\": \"string\", \"isDone\": false }}";

		httpRequest.
				body(myJson).
				when().
				put("http://localhost:8080/api/erp/orders/1");

		JsonPath jsonPath = new JsonPath(
				httpRequest.
				when().
				get("http://localhost:8080/api/erp/orders/1").
				getBody().
				asString()
				);
		String res = jsonPath.getString("conditions");
		assertEquals(res,"update");
	}

}

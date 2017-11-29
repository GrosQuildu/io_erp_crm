package io.swagger.api.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.swagger.model.BaseModel;
import io.swagger.model.common.Client;
import io.swagger.model.common.ClientRepository;
import io.swagger.model.common.ClientType;
import io.swagger.model.common.ClientTypeRepository;
import io.swagger.model.erp.OrderRepository;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClientsApiControllerTest{

	String token = "Bearer 8539360e-9a8e-48c5-bc2f-d2dbdcae94fb";
	RequestSpecification httpRequest = RestAssured.given().
										contentType("application/json").
										headers("accept", "application/json",
												"Authorization", token);

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientTypeRepository clientTypeRepository;
    @Autowired
    OrderRepository orderRepository;

/*
 * Prawdopodobnie nie przechodzi, poniewaz ClientTypeId nie moze sie dopasowac
 * z ClientTypeId z bazy danych -> GET clientTypes/{id} nie dziala
 */
	@Test
	public void testCreateClient() {
		String newClient = "{ \"id\": \"0\", \"name\": \"12\", \"street\": \"string\", \"city\": \"string\", \"postCode\": \"string\", \"nameDelivery\": \"string\", \"streetDelivery\": \"string\", \"cityDelivery\": \"string\", \"postCodeDelivery\": \"string\", \"nip\": \"string\", \"telephone\": \"string\", \"mail\": \"user22@example.com\", \"clientTypeId\": 4}";
		Response response = httpRequest.
							and().
							body(newClient).
							when().
							post("http://localhost:8080/api/clients");

		//System.out.println("Client ID -> " + response.asString());
		int clId = response.getStatusCode();
		assertEquals(clId,200);
	}

	@Test
	public void testDeleteClient() {
	httpRequest.
		when().
		delete("http://localhost:8080/api/clients/1");

	Response response = httpRequest.
						when().
						get("http://localhost:8080/api/clients/1");

	JsonPath jsonPath = new JsonPath(response.getBody().asString());
	String msg = jsonPath.getString("message");
	assertEquals(msg,"Model not found");

	}

	@Test
	public void testGetClient() {
		Response response = httpRequest.when().get("http://localhost:8080/api/clients/1");
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		int resID = jsonPath.getInt("id");
		assertEquals(resID,1);
	}

	@Test
	public void testGetClients() {
		Response response = httpRequest.get("http://localhost:8080/api/clients");
		int statusCode = response.getStatusCode();
		assertEquals(statusCode , 200 );
	}

	@Test
	public void testUpdateClient() {
		String myJson = "{ \"id\": 4, \"name\": \"update\", \"street\": \"string\", \"city\": \"string\", \"postCode\": \"string\", \"nameDelivery\": \"string\", \"streetDelivery\": \"string\", \"cityDelivery\": \"string\", \"postCodeDelivery\": \"string\", \"nip\": \"string\", \"telephone\": \"string\", \"mail\": \"user1@example.com\", \"clientTypeId\": 1}";

		httpRequest.
				body(myJson).
				when().
				put("http://localhost:8080/api/clients/4");

		JsonPath jsonPath = new JsonPath(
				httpRequest.
				when().
				get("http://localhost:8080/api/clients/4").
				getBody().
				asString()
				);
		String resName = jsonPath.getString("name");
		//System.out.println("name: " + resDesc);
		assertEquals(resName,"update");
	}

}

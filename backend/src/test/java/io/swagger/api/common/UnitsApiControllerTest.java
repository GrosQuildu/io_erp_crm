package io.swagger.api.common;

import static org.junit.Assert.*;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UnitsApiControllerTest {

	String token = "Bearer 8539360e-9a8e-48c5-bc2f-d2dbdcae94fb";
	RequestSpecification httpRequest = RestAssured.given().
										contentType("application/json").
										headers("accept", "application/json",
												"Authorization", token);

	@Test
	public void testCreateUnit() {
		String newUnit = "{ \"id\": 1, \"name\": \"unit\", \"nameShort\": \"un\"}";
		Response response = httpRequest.
							and().
							body(newUnit).
							when().
							post("http://localhost:8080/api/units");
		//JsonPath jsonPath = new JsonPath(response.getBody().asString());
		
		/*
		 * System.out dlatego, aby sprawdzic jakie id ma nowo utworzony unit, 
		 * wykorzystane w dalszych testach
		 * W tym przypadku id -> 16
		 */
		System.out.println("Unit ID -> " + response.asString());
		int unId = response.getStatusCode();
		assertEquals(unId,200);
	}

	/*
	 * Wykonuje jako ostatnie, usuwajac unit utworzony powyzej,
	 * jako ze we wczesniejszych testach istnieje
	 */
	@Test
	public void testDeleteUnit() {
		httpRequest.
		when().
		delete("http://localhost:8080/api/units/16");

	Response response = httpRequest.
						when().
						get("http://localhost:8080/api/units/16");

	JsonPath jsonPath = new JsonPath(response.getBody().asString());
	String msg = jsonPath.getString("message");
	assertEquals(msg,"Model not found");
	}

	@Test
	public void testGetUnit() {
		Response response = httpRequest.when().get("http://localhost:8080/api/units/16");
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		int resID = jsonPath.getInt("id");
		assertEquals(resID,16);
	}

	@Test
	public void testGetUnits() {
		Response response = httpRequest.get("http://localhost:8080/api/units");
		int statusCode = response.getStatusCode();
		assertEquals(statusCode , 200 );
	}

	@Test
	public void testUpdateUnit() {
		String myJson = "{ \"id\": 16, \"name\": \"update\", \"nameShort\": \"upd\"}";

		httpRequest.
				body(myJson).
				when().
				put("http://localhost:8080/api/units/16");

		JsonPath jsonPath = new JsonPath(
				httpRequest.
				when().
				get("http://localhost:8080/api/units/16").
				getBody().
				asString()
				);
		String resName = jsonPath.getString("name");
		//System.out.println("name: " + resDesc);
		assertEquals(resName,"update");
	}

}

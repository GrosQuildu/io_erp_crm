package io.swagger.api.common;
import org.hamcrest.core.*;
import org.hamcrest.core.IsEqual;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;
import static io.restassured.authentication.OAuth2Scheme.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.springframework.util.Assert;

import static org.mockito.Mockito.*;
import io.swagger.model.BaseModel;

import io.restassured.RestAssured;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.swagger.model.common.Employee;
import io.swagger.model.common.Employee.Role;

public class EmployeesApiControllerTest {
  
	String token = "Bearer 77b0f777-9671-4b9d-8044-db1a11d4f27d";
	RequestSpecification httpRequest = RestAssured.given().
										contentType("application/json").
										headers("accept", "application/json",
												"Authorization", token);

	/*
	 * Nie działa, zwraca kod 400, a hasla nie roznia sie od siebie
	 */
	@Test
	public void testChangePassword() {
		String json = "{ \"oldPassword\": \"IZ1Y60MBPMKZ\", \"newPassword\": \"ToJeStNoWeHaSlO\"}";

		Response response = httpRequest.
				when().
				get("http://localhost:8080/api/employees/3");

		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		String oldPswd = jsonPath.getString("password");

		httpRequest.
				and().
				body(json).
				when().
				put("http://localhost:8080/api/employees/3/changePassword");

		response = httpRequest.
				when().
				get("http://localhost:8080/api/employees/3");

		jsonPath = new JsonPath(response.getBody().asString());
		String newPswd = jsonPath.getString("password");
		assertNotEquals(oldPswd,newPswd);
	}

	/*
	 * Działa, ale nie diala przez id. Jest wymagane do przeslania, a generuje sie samo
	 * nawet haslo musi sie roznic od hasel innych userow
	 */
	@Test
	public void testCreateEmployee() {
    
		String newUser = "{ \"id\": 14, \"name\": \"user14\", \"telephone\": \"teluser13\", \"mail\": \"mail@13.com\", \"password\": \"11311\", \"role\": \"erp\", \"monthSchedule\": 2, \"visibility\": true}";
		httpRequest.
				and().
				body(newUser).
				when().
				post("http://localhost:8080/api/employees");

		Response response = httpRequest.when().get("http://localhost:8080/api/employees/14");
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		String usName = jsonPath.getString("name");

		assertEquals(usName,"user14");
	}

	@Test
	public void testDeleteEmployee() {
		httpRequest.
			when().
			delete("http://localhost:8080/api/employees/3");

		Response response = httpRequest.
							when().
							get("http://localhost:8080/api/employees/3");

		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		String msg = jsonPath.getString("message");
		assertEquals(msg,"Model not found");
//		int statusCode = response.getStatusCode();
//		assertEquals(statusCode , 200 );
	}

	@Test
	public void testGetEmployee() {
		Response response = httpRequest.when().get("http://localhost:8080/api/employees/1");
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		int resID = jsonPath.getInt("id");
		assertEquals(resID,1);
	}

	@Test
	public void testGetEmployees() {
		Response response = httpRequest.get("http://localhost:8080/api/employees");

		int statusCode = response.getStatusCode();
		assertEquals(statusCode , 200 );
	}

	@Test
	public void testUpdateEmployee() {
		String myJson = "{ \"id\": 1, \"name\": \"admin\", \"telephone\": \"555-222-333\", \"mail\": \"admin@io_erp_crm.com\", \"password\": \"$2a$10$ZCnoJm4GAUvlJ44Q3RnpTOJlAeRXULVRT2dNg91jyTWzUSherZ5re\", \"role\": \"admin\", \"monthSchedule\": 50000, \"visibility\": true}";

		Response response = httpRequest.
				body(myJson).
				when().
				put("http://localhost:8080/api/employees/1");

		response = httpRequest.when().get("http://localhost:8080/api/employees/1");
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		String resPh = jsonPath.getString("telephone");
		System.out.println("Tele: " + resPh);
		assertEquals(resPh,"555-222-333");
	}

}

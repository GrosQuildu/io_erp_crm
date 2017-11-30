package io.swagger.api.erp;

import static org.junit.Assert.*;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ArticlesApiControllerTest {
	String token = "Bearer 44bafa20-7861-4b65-b7a1-fdc3f258c6a9";
	RequestSpecification httpRequest = RestAssured.given().
						contentType("application/json").
						headers("accept", "application/json",
								"Authorization", token);

	/*
	 * Nie może pobrac unitID, chocia GET unit smiga az milo
	 */
	@Test
	public void testCreateArticle() {
		/*
		 * Najpierw trzeba utworzyc unit i podac jego id
		 */
		String newObj = "{ \"id\": 1, \"availability\": 1, \"unitId\": 5, \"unitPrice\": 55, \"weight\": 1}";
		Response response = httpRequest.
							and().
							body(newObj).
							when().
							post("http://localhost:8080/api/erp/articles");
		//JsonPath jsonPath = new JsonPath(response.getBody().asString());

		System.out.println("article ID -> " + response.asString());
		int id = response.getStatusCode();
		assertEquals(id,200);
	}

	@Test
	public void testDeleteArticle() {
		httpRequest.
		when().
		delete("http://localhost:8080/api/erp/articles/1");

	Response response = httpRequest.
						when().
						get("http://localhost:8080/api/erp/articles/1");

	JsonPath jsonPath = new JsonPath(response.getBody().asString());
	String msg = jsonPath.getString("message");
	assertEquals(msg,"Model not found");
	}

	@Test
	public void testGetArticle() {
		Response response = httpRequest.when().get("http://localhost:8080/api/erp/articles/1");
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		int resID = jsonPath.getInt("id");
		assertEquals(resID,1);
	}

	@Test
	public void testGetArticles() {
		Response response = httpRequest.get("http://localhost:8080/api/erp/articles");
		int statusCode = response.getStatusCode();
		assertEquals(statusCode , 200 );
	}

	@Test
	public void testUpdateArticle() {
		String myJson = "{ \"id\": 1, \"availability\": 2, \"unitId\": 5, \"unitPrice\": 69, \"weight\": 96}";

		httpRequest.
				body(myJson).
				when().
				put("http://localhost:8080/api/erp/articles/1");

		JsonPath jsonPath = new JsonPath(
				httpRequest.
				when().
				get("http://localhost:8080/api/erp/articles/1").
				getBody().
				asString()
				);
		int resId = jsonPath.getInt("availability");
		//System.out.println("name: " + resDesc);
		assertEquals(resId,2);
	}

}

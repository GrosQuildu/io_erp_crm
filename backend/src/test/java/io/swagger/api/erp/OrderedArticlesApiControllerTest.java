package io.swagger.api.erp;

import static org.junit.Assert.*;

import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OrderedArticlesApiControllerTest {
	String token = "Bearer 44bafa20-7861-4b65-b7a1-fdc3f258c6a9";
	RequestSpecification httpRequest = RestAssured.given().
										contentType("application/json").
										headers("accept", "application/json",
												"Authorization", token);
	@Test
	public void testCreateOrderedArticle() {
		String json = "{ \"id\": 0, \"articleId\": 1, \"orderId\": 1, \"description\": \"string\", \"amount\": 0, \"unitPrice\": 0, \"netPrice\": 0, \"weight\": 0}";
		Response response = httpRequest.
								and().
								body(json).
								when().
								post("http://localhost:8080/api/erp/orders/{orderId}/articles");
		int check = response.getStatusCode();
		System.out.println("Order ID -> " + response.asString());
		assertEquals(check,200);
	}

	@Test
	public void testDeleteOrderedArticle() {
		httpRequest.
		when().
		delete("http://localhost:8080/api/erp/orders/{orderId}/articles/{orderedArticleId}");

	Response response = httpRequest.
						when().
						get("http://localhost:8080/api/erp/orders/{orderId}/articles/{orderedArticleId}");

	JsonPath jsonPath = new JsonPath(response.getBody().asString());
	String msg = jsonPath.getString("message");
	assertEquals(msg,"Model not found");
	}

	@Test
	public void testGetOrderedArticle() {
		Response response = httpRequest.
				when().
				get("http://localhost:8080/api/erp/orders/{orderId}/articles/{orderedArticleId}");
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		int resID = jsonPath.getInt("id");
		assertEquals(resID, {orderedArticleId} );
	}

	@Test
	public void testGetOrderedArticleNetPrice() {
		Response response = httpRequest.
				when().
				get("http://localhost:8080/api/erp/orders/{orderId}/articles/{orderedArticleId}/netPrice");

		int check = response.getStatusCode();
		System.out.println("Net Price -> " + response.asString());
		assertEquals(check,200);
	}

	@Test
	public void testGetOrderedArticleWeight() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOrderedArticles() {
		Response response = httpRequest.
				get("http://localhost:8080/api/erp/orders/{orderId}/articles");
		int statusCode = response.getStatusCode();
		assertEquals(statusCode , 200 );
	}

	@Test
	public void testUpdateOrderedArticle() {
		String myJson = "{ \"id\": 0, \"articleId\": {articleID}, \"orderId\": {orderID}, \"description\": \"update\", \"amount\": 0, \"unitPrice\": 0, \"netPrice\": 0, \"weight\": 0}";

		httpRequest.
				body(myJson).
				when().
				put("http://localhost:8080/api/erp/orders/{orderId}/articles/{orderedArticleId}");

		JsonPath jsonPath = new JsonPath(
				httpRequest.
				when().
				get("http://localhost:8080/api/erp/orders/{orderId}/articles/{orderedArticleId}").
				getBody().
				asString()
				);
		String res = jsonPath.getString("description");
		assertEquals(res,"update");
	}

}

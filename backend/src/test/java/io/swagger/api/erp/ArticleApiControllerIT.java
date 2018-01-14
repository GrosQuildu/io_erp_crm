package io.swagger.api.erp;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.swagger.Swagger2SpringBoot;
import io.swagger.api.ITHelper;
import io.swagger.model.common.*;
import io.swagger.model.erp.Article;
import io.swagger.model.erp.ArticleRepository;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Swagger2SpringBoot.class)
public class ArticleApiControllerIT {
     private static final String RESOURCE = "/api/erp/articles";

	private static String adminToken = "";
	private static String crmToken = "";
	private static String erpToken = "";

	private static Unit unit1;
	private static Unit unit2;

	private static Article article1;
	private static Article article2;
	private static Article article3;

	private static String invalidToken;

	@Autowired
	private ArticleRepository repository;
	@Autowired
	private UnitRepository unitRepository;

	@LocalServerPort
	private int serverPort;

	@Before
	public void setUp() {
		RestAssured.port = serverPort;
		if (adminToken.isEmpty()) {
			adminToken = ITHelper.getToken(Employee.Role.ADMIN);
			crmToken = ITHelper.getToken(Employee.Role.CRM);
			erpToken = ITHelper.getToken(Employee.Role.ERP);
		}

        repository.deleteAll();
        unitRepository.deleteAll();

        unit1 = new Unit(null, "unit1", "unit1Short");
        unit2 = new Unit(null, "unit2", "unit1Short2");
        unit1 = unitRepository.save(unit1);
        unit2 = unitRepository.save(unit2);

        article1 = new Article(null, "article1", 10, unit1, new BigDecimal(2.22));
        article2 = new Article(null, "article2", 40, unit2, new BigDecimal(225.12));

        invalidToken = "XXX-9a20-4b49-97a9-YYY";
    }

    @After
	public void clear() {
		repository.deleteAll();
		unitRepository.deleteAll();
	}

	@Test
	public void createArticleWithoutAuthShouldReturn401Error() {
		given()
			.contentType("application/json")
		.when()
			.body(article1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void createArticleWithWrongUserTokenShouldReturnAuthError() {
		given()
			.header("Authorization", "Bearer " + crmToken)
			.contentType("application/json")
		.when()
			.body(article1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_FORBIDDEN);
	}

	@Test
	public void createArticleWithProperUserTokenShouldReturnIdAndSaveObject() {
		Response response = given()
								.header("Authorization", "Bearer " + erpToken)
								.contentType("application/json")
							.when()
								.body(article1)
								.post(RESOURCE)
							.then()
								.statusCode(HttpStatus.SC_OK)
								.extract().response();

		Integer newId = Integer.parseInt(new String(response.asByteArray()));
		Article createdArticle = repository.findById(newId);

		Article toCompare = given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + newId)
			.as(Article.class);
		assert toCompare.equals(createdArticle);
	}

	@Test
	public void createArticleWithoutRequiredFieldsShouldReturnError() {
		article1.setName("");
		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.body(article1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_BAD_REQUEST);
		article1.setName("article1");
	}

	@Test
	public void deleteArticleShouldDeleteArticle() {
        article1 = repository.save(article1);
		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + article1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);
		assert repository.findById(article1.getId()) == null;
	}

	@Test
	public void deleteNonexistingArticleShouldReturnError() {
		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + 321)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void updateArticleWithWrongPathBodyIdShouldReturnError() {
	    article1 = repository.save(article1);
		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.body(article1)
			.put(RESOURCE + "/" + article1.getId() + 3)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void updateArticleShouldUpdate() {
	    article1.setId(null);
		article1 = repository.save(article1);
		article1.setName("xxasdasd");
		article1.setUnitPrice(new BigDecimal(123));

		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.body(article1)
			.put(RESOURCE + "/" + article1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);

		Article toCompare = given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + article1.getId())
			.as(Article.class);
        toCompare.setUnitPrice(article1.getUnitPrice());
        assert toCompare.equals(article1);
	}

	@Test
	public void getArticlesShouldReturnAllArticles() {
	    article1.setId(null);
	    article2.setId(null);
		article1 = repository.save(article1);
		article2 = repository.save(article2);

		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("id", hasItems(article1.getId(), article2.getId()));
	}
}

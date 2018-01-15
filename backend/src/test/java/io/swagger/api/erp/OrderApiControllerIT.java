package io.swagger.api.erp;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.swagger.Swagger2SpringBoot;
import io.swagger.api.ITHelper;
import io.swagger.model.common.*;
import io.swagger.model.erp.*;
import org.apache.http.HttpStatus;
import org.joda.time.LocalDate;
import org.junit.After;
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
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;


@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Swagger2SpringBoot.class)
public class OrderApiControllerIT {
    private static final String RESOURCE = "/api/erp/orders";

	private static String adminToken = "";
	private static String crmToken = "";
	private static String erpToken = "";

	private static Employee employee1;

	private static Unit unit1;
	private static Unit unit2;

	private static Article article1;
	private static Article article2;

	private static List<OrderedArticle> orderedArticles;

	private static ClientType clientType1;
	private static ClientType clientType2;

    private static Client client1;
    private static Client client2;

	private static Order_ order1;
	private static Order_ order2;
	private static Order_ order3;

	private static String invalidToken;

	@Autowired
	private OrderRepository repository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
    private UnitRepository unitRepository;
	@Autowired
    private ArticleRepository articleRepository;
	@Autowired
    private OrderedArticleRepository orderedArticleRepository;
	@Autowired
	private ClientTypeRepository clientTypeRepository;
	@Autowired
    private ClientRepository clientRepository;

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

        employee1 = new Employee(null, "employee2", "test2@test.com", "password123", Employee.Role.ERP);

		unit1 = new Unit(null, "unit1", "unit1Short");
        unit2 = new Unit(null, "unit2", "unit1Short2");
        unit1 = unitRepository.save(unit1);
        unit2 = unitRepository.save(unit2);

        article1 = new Article(null, "article1", 10, unit1, new BigDecimal(2.22));
        article2 = new Article(null, "article2", 40, unit2, new BigDecimal(225.12));
        article1 = articleRepository.save(article1);
        article2 = articleRepository.save(article2);

        clientType1 = new ClientType(null, "clienType1");
        clientType2 = new ClientType(null, "clienType1");
        clientType1 = clientTypeRepository.save(clientType1);
        clientType2 = clientTypeRepository.save(clientType2);

        client1 = new Client(null, "client1", "test@test.com", clientType1);
        client2 = new Client(null, "client2", "test2@test.com", clientType2);
        client1 = clientRepository.save(client1);
        client2 = clientRepository.save(client2);


		OrderedArticle orderedArticle1 = new OrderedArticle(null, article1, "OrderedArticle1", 10);
		OrderedArticle orderedArticle2 = new OrderedArticle(null, article2, "OrderedArticle2", 30);

		order1 = new Order_(null, "orderNumber1", new LocalDate("2019-1-12"), client1, 23.3f, "someState");
		order2 = new Order_(null, "orderNumber2", new LocalDate("2011-3-12"), client1, 22.3f, "someState2");

        invalidToken = "XXX-9a20-4b49-97a9-YYY";
    }

    @After
	public void clear() {
	    repository.deleteAll();

	    for (Employee employee : employeeRepository.findAll()) {
			if(employee.getId() > 3)
				employeeRepository.delete(employee.getId());
		}

        orderedArticleRepository.deleteAll();
        articleRepository.deleteAll();
        unitRepository.deleteAll();

        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();
    }

	@Test
	public void createOrderWithoutAuthShouldReturn401Error() {
		given()
			.contentType("application/json")
		.when()
			.body(order1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void createOrderWithWrongUserTokenShouldReturnAuthError() {
		given()
			.header("Authorization", "Bearer " + crmToken)
			.contentType("application/json")
		.when()
			.body(order1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_FORBIDDEN);
	}

	@Test
	public void createOrderWithProperUserTokenShouldReturnIdAndSaveObject() {
		Response response = given()
								.header("Authorization", "Bearer " + erpToken)
								.contentType("application/json")
							.when()
								.body(order1)
								.post(RESOURCE)
							.then()
								.statusCode(HttpStatus.SC_OK)
								.extract().response();

		Integer newId = Integer.parseInt(new String(response.asByteArray()));
		Order_ createdOrder = repository.findById(newId);

		Order_ toCompare = given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + newId)
			.as(Order_.class);
		assert toCompare.getId().equals(createdOrder.getId());
	}
	
	@Test
	public void createOrderWithExistingOrderIdShouldCreateNewOrder() {
		order1 = repository.save(order1);
		order2.setId(order1.getId());

		Response response = given()
								.header("Authorization", "Bearer " + adminToken)
								.contentType("application/json")
							.when()
								.body(order2)
								.post(RESOURCE)
							.then()
								.statusCode(HttpStatus.SC_OK)
								.extract().response();

		Integer newId = Integer.parseInt(new String(response.asByteArray()));
		assert !newId.equals(order1.getId());
		assert repository.findById(order1.getId()) != null;
		assert repository.findById(newId) != null;
	}

	@Test
	public void createOrderWithoutRequiredFieldsShouldReturnError() {
		order1.orderNumber(null);
		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.body(order1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
		order1.setOrderNumber("asd");
	}

	@Test
	public void deleteOrderShouldDeleteOrder() {
        order1 = repository.save(order1);
		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + order1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);
		assert repository.findById(order1.getId()) == null;
	}

	@Test
	public void deleteNonexistingOrderShouldReturnError() {
		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + 321)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void updateOrderWithWrongPathBodyIdShouldReturnError() {
	    order1 = repository.save(order1);
		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.body(order1)
			.put(RESOURCE + "/" + order1.getId() + 3)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void updateOrderShouldUpdate() {
	    order1.setId(null);
		order1 = repository.save(order1);
		order1.setComments("xxasdasd");
		order1.setIsDone(!order1.getIsDone());

		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.body(order1)
			.put(RESOURCE + "/" + order1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);

		Order_ toCompare = given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + order1.getId())
			.as(Order_.class);
        assert toCompare.getComments().equals(order1.getComments());
        assert toCompare.getIsDone().equals(order1.getIsDone());
	}

	@Test
	public void getOrdersShouldReturnAllOrders() {
		order1 = repository.save(order1);
		order2 = repository.save(order2);

		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("id", hasItems(order1.getId(), order2.getId()));
	}
}

package io.swagger.api;

import com.jayway.restassured.response.Response;
import io.swagger.model.common.Employee;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.jayway.restassured.RestAssured.given;

public class ITHelper {
    private static final String GET_TOKEN = "/api/oauth/token";

    private static String basicUser;
    private static String basicPassword;


    static public String getToken(Employee.Role role) {
        Resource resourceTest = new ClassPathResource("/application-test.properties");
        Properties propsTest = null;
        try {
            propsTest = PropertiesLoaderUtils.loadProperties(resourceTest);
        } catch (IOException e) {
            System.out.println("Error in ITHelper GetToken");
            e.printStackTrace();
            return "";
        }

        List<String> usernames = Arrays.asList(propsTest.getProperty("security.default.admin.user"),
                propsTest.getProperty("security.default.crm.user"), propsTest.getProperty("security.default.erp.user"));
        List<String> emails = Arrays.asList(propsTest.getProperty("security.default.admin.mail"),
                propsTest.getProperty("security.default.crm.mail"), propsTest.getProperty("security.default.erp.mail"));
        List<String> passwords = Arrays.asList(propsTest.getProperty("security.default.admin.password"),
                propsTest.getProperty("security.default.crm.password"), propsTest.getProperty("security.default.erp.password"));


        basicUser = propsTest.getProperty("security.oauth2.client.id");
        basicPassword = propsTest.getProperty("security.oauth2.client.client-secret");

        String token = null;
        switch (role) {
            case ADMIN:
            Response response = given()
                    .auth().basic(basicUser, basicPassword)
                    .parameters("grant_type", "password", "username", emails.get(0), "password", passwords.get(0))
                    .when().post(GET_TOKEN).then().extract().response();
            token = response.path("access_token");
            break;

            case CRM:
            response = given()
                    .auth().basic(basicUser, basicPassword)
                    .parameters("grant_type", "password", "username", emails.get(1), "password", passwords.get(1))
                    .when().post(GET_TOKEN).then().extract().response();
            token = response.path("access_token");
            break;

            case ERP:
            response = given()
                    .auth().basic(basicUser, basicPassword)
                    .parameters("grant_type", "password", "username", emails.get(2), "password", passwords.get(2))
                    .when().post(GET_TOKEN).then().extract().response();
            token = response.path("access_token");
            break;
        }
        return token;
    }

    public static byte[] convertObjectToJsonBytes(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        byte[] result = null;
        try {
            result = mapper.writeValueAsBytes(object);
        } catch (IOException ignored) {}
        return result;
    }
}

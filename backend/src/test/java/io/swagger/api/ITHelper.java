package io.swagger.api;

import org.codehaus.jackson.map.ObjectMapper;
import com.jayway.restassured.response.Response;
import io.swagger.model.common.Employee;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;

public class ITHelper {
    private static final String GET_TOKEN = "/api/oauth/token";

    private static String adminName;
    private static String adminMail;
    private static String adminPass;

    private static String crmName;
    private static String crmMail;
    private static String crmPass;

    private static String erpName;
    private static String erpMail;
    private static String erpPass;

    @Value("${security.default.admin.user}")
    public void setAdminName(String adminName) { ITHelper.adminName = adminName; }
    @Value("${security.default.admin.mail}")
    public void setAdminMail(String adminMail) { ITHelper.adminMail = adminMail; }
    @Value("${security.default.admin.password}")
    public void setAdminPass(String adminPass) { ITHelper.adminPass = adminPass; }

    @Value("${security.default.crm.user}")
    public void setCrmName(String crmName) { ITHelper.crmName = crmName; }
    @Value("${security.default.crm.mail}")
    public void setCrmMail(String crmMail) { ITHelper.crmMail = crmMail; }
    @Value("${security.default.crm.password}")
    public void setCrmPass(String crmPass) { ITHelper.crmPass = crmPass; }

    @Value("${security.default.erp.user}")
    public void setErpName(String erpName) { ITHelper.erpName = erpName; }
    @Value("${security.default.erp.mail}")
    public void setErpMail(String erpMail) { ITHelper.erpMail = erpMail; }
    @Value("${security.default.erp.password}")
    public void setErpPass(String erpPass) { ITHelper.erpPass = erpPass; }


    static public String getToken(Employee.Role role) {
        adminName = "admin";
        adminMail = "admin@io_erp_crm.com";
        adminPass = "81J3V6V9SQMT";

        erpName = "main_erp";
        erpMail = "main_erp@io_erp_crm.com";
        erpPass = "AM3MR3F0JNG4";

        crmName = "main_crm";
        crmMail = "main_crm@io_erp_crm.com";
        crmPass = "2G8UI6F0UVJC";

        String token = null;
        System.out.println("------------------------");
        System.out.println("------------------------");
        System.out.println(adminName);
        System.out.println(adminPass);
        System.out.println("------------------------");
        System.out.println("------------------------");
        switch (role) {
            case ADMIN:
            Response response = given()
                    .auth().basic("javafx-client", "R3GMPX2DQHD1234VFG929ABW")
                    .parameters("grant_type", "password", "username", adminMail, "password", adminPass)
                    .when().post(GET_TOKEN).then().extract().response();
            token = response.path("access_token");
            break;

            case CRM:
            response = given()
                    .auth().basic("javafx-client", "R3GMPX2DQHD1234VFG929ABW")
                    .parameters("grant_type", "password", "username", crmMail, "password", crmPass)
                    .when().post(GET_TOKEN).then().extract().response();
            token = response.path("access_token");
            break;

            case ERP:
            response = given()
                    .auth().basic("javafx-client", "R3GMPX2DQHD1234VFG929ABW")
                    .parameters("grant_type", "password", "username", erpMail, "password", erpPass)
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

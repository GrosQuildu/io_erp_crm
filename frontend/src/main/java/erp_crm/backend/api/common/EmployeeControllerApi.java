package main.java.erp_crm.backend.api.common;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.erp_crm.backend.api.ConnectionApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Employee;
import main.java.erp_crm.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeControllerApi {
    private ConnectionApi connection = Login.connection;
    private Gson gson = Converters.registerDateTime(new GsonBuilder()).create();


    public void getEmployees(){
        List<Employee> employees = new ArrayList<>();
        employees.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.EMPLOYEES), Employee[].class) )
        );
        DBData.setEmployees(employees);
    }

    public Integer createEmployee(Employee employee){
        return connection.createObject(employee.serialize(), ConnectionApi.ObjectType.EMPLOYEES);
    }

    public void deleteEmployee(Employee item) {
        connection.deleteObject(item.getId(), ConnectionApi.ObjectType.EMPLOYEES);
    }
}

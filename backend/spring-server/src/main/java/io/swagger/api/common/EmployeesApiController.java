package io.swagger.api.common;

import io.swagger.model.common.Employee;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Controller
public class EmployeesApiController implements EmployeesApi {



    public ResponseEntity<Void> changePassword(@ApiParam(value = "",required=true ) @PathVariable("employeeId") Integer employeeId,
        @ApiParam(value = ""  )  @Valid @RequestBody Employee employee) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Integer> createEmployee(@ApiParam(value = "Employee to create"  )  @Valid @RequestBody Employee employee) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteEmployee(@ApiParam(value = "",required=true ) @PathVariable("employeeId") Integer employeeId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Employee> getEmployee(@ApiParam(value = "",required=true ) @PathVariable("employeeId") Integer employeeId) {
        // do some magic!
        return new ResponseEntity<Employee>(HttpStatus.OK);
    }

    public ResponseEntity<List<Employee>> getEmployees() {
        // do some magic!
        return new ResponseEntity<List<Employee>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateEmployee(@ApiParam(value = "",required=true ) @PathVariable("employeeId") Integer employeeId,
        @ApiParam(value = "Employee to create"  )  @Valid @RequestBody Employee employee) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}

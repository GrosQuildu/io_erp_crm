package io.swagger.configuration;

import io.swagger.model.common.Employee;
import io.swagger.model.common.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * Created by gros on 22.11.17.
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void save(Employee employee){
        employee.setPassword(getPasswordEncoder().encode(employee.getPassword()));
        employeeRepository.save(employee);
    }

    public boolean matches(String oldPassword, String actualPassword) {
        return getPasswordEncoder().matches(oldPassword, actualPassword);
    }
}
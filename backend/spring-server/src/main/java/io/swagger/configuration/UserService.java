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
public class UserService {

    @Autowired
    private EmployeeRepository repo;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void save(Employee user){
        user.setPassword(getPasswordEncoder().encode(user.getPassword()));
        repo.save(user);
    }

}
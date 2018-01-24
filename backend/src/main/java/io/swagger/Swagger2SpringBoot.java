package io.swagger;

import io.swagger.configuration.CustomUserDetails;
import io.swagger.configuration.EmployeeService;
import io.swagger.model.common.Employee;
import io.swagger.model.common.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableSwagger2
@EnableAutoConfiguration(exclude = RepositoryRestMvcAutoConfiguration.class)
public class Swagger2SpringBoot implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationContext ctx;

    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    public static void main(String[] args) throws Exception {
        new SpringApplication(Swagger2SpringBoot.class).run(args);
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, EmployeeRepository repository, EmployeeService service) throws Exception {
        //Setup a default users if db is empty
        if (repository.count()==0) {
            // manually read properties - spring's automagic is somagic that it returns nulls for @Value. Probably lack of a dozen new interfaces
            Resource resource = new ClassPathResource("/application.properties");
            Properties props = PropertiesLoaderUtils.loadProperties(resource);

            Environment env = ctx.getEnvironment();

            List<String> usernames = Arrays.asList(env.getProperty("security.default.admin.user"),
                    env.getProperty("security.default.crm.user"), env.getProperty("security.default.erp.user"));
            List<String> emails = Arrays.asList(env.getProperty("security.default.admin.mail"),
                    env.getProperty("security.default.crm.mail"), env.getProperty("security.default.erp.mail"));
            List<String> passwords = Arrays.asList(env.getProperty("security.default.admin.password"),
                    env.getProperty("security.default.crm.password"), env.getProperty("security.default.erp.password"));

            service.save(new Employee(1, usernames.get(0), emails.get(0), passwords.get(0), Employee.Role.ADMIN));
            service.save(new Employee(2, usernames.get(1), emails.get(1), passwords.get(1), Employee.Role.CRM));
            service.save(new Employee(3, usernames.get(2), emails.get(2), passwords.get(2), Employee.Role.ERP));
        }
        builder.userDetailsService(userDetailsService(repository)).passwordEncoder(passwordEncoder);
    }

    private String generatePassword(Integer length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder pass = new StringBuilder();
        Random rnd = new Random();
        while (pass.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            pass.append(SALTCHARS.charAt(index));
        }
        return pass.toString();

    }

    private UserDetailsService userDetailsService(final EmployeeRepository repository) {
        return mail -> new CustomUserDetails(repository.findByMail(mail));
    }
}

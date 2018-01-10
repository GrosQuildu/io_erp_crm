package main.java.erp.backend;

import main.java.erp.backend.configuration.CustomUserDetails;
import main.java.erp.backend.configuration.EmployeeService;
import main.java.erp.backend.model.common.Employee;
import main.java.erp.backend.model.common.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = { "io.swagger", "io.swagger.api" })
public class Swagger2SpringBoot implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        //Setup a default user if db is empty
        if (repository.count()==0) {
            List<String> passwords = Arrays.asList(generatePassword(12), generatePassword(12), generatePassword(12));
            service.save(new Employee(1, "admin", "admin@io_erp_crm.com", passwords.get(0), Employee.Role.ADMIN));
            service.save(new Employee(2, "main_crm", "main_crm@io_erp_crm.com", passwords.get(1), Employee.Role.CRM));
            service.save(new Employee(3, "main_erp", "main_erp@io_erp_crm.com", passwords.get(2), Employee.Role.ERP));
            System.out.println("Created default employees:");
            System.out.println("admin - " + passwords.get(0));
            System.out.println("main_crm - " + passwords.get(1));
            System.out.println("main_erp - " + passwords.get(2));
            builder.userDetailsService(userDetailsService(repository)).passwordEncoder(passwordEncoder);
        }
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

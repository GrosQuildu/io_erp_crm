package io.swagger.configuration;

import io.swagger.model.common.Employee;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * Authorize requests:
     * /oauth/token is endpoint for login, can be accessed with basic auth with credentials from application properties
     * /crm can be accessed by admin and crm employee
     * /erp can be accessed by admin and erp employee
     * rest resources are considered shared (accessed by admin, erp and crm)
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers("/oauth/token", "/api-docs", "/swagger-ui.html", "/swagger-resources/**").permitAll()
                .antMatchers("/oauth/**").denyAll()
                .antMatchers("/crm/**").hasAnyAuthority(Employee.Role.ADMIN.toString(),Employee.Role.CRM.toString())
                .antMatchers("/erp/**").hasAnyAuthority(Employee.Role.ADMIN.toString(),Employee.Role.ERP.toString())
                .antMatchers("/**").hasAnyAuthority(Employee.Role.ADMIN.toString(),Employee.Role.CRM.toString(),Employee.Role.ERP.toString());
    }
}
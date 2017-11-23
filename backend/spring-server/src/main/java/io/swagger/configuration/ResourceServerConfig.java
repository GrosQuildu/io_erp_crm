package io.swagger.configuration;

import io.swagger.model.common.Employee;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by gros on 22.11.17.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers("/oauth/token", "/api-docs").permitAll()
                .antMatchers("/oauth/**").denyAll()
                .antMatchers("/articles/**").hasAnyAuthority(Employee.Role.ADMIN.toString(),Employee.Role.CRM.toString());
    }
}
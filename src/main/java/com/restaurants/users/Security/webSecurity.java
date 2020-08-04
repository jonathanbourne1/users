package com.restaurants.users.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class webSecurity extends WebSecurityConfigurerAdapter {

    private  Environment environment;
    @Autowired
    public webSecurity(Environment environment){
        this.environment = environment;
    }


@Override
    protected void configure(HttpSecurity http) throws Exception{
    http.csrf().disable();
    http.authorizeRequests().antMatchers("/users/**").hasIpAddress(environment.getProperty("gateway.ip"));
}
}

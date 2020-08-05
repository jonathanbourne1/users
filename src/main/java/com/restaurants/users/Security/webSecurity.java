package com.restaurants.users.Security;


import com.restaurants.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class webSecurity extends WebSecurityConfigurerAdapter {

    private  Environment environment;
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public webSecurity(Environment environment, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.environment = environment;
        this.userService= userService;
        this.bCryptPasswordEncoder= bCryptPasswordEncoder;
    }

//hasIpAddress(environment.getProperty("gateway.ip"))
@Override
    protected void configure(HttpSecurity http) throws Exception{
    http.csrf().disable();
    http.authorizeRequests().antMatchers("/users/**").hasIpAddress(environment.getProperty("gateway.ip"))
            .and()
           .addFilter( getAuthenticationFilter());
}

protected AutenticationFilter getAuthenticationFilter() throws Exception{
    AutenticationFilter autenticationFilter = new AutenticationFilter(userService, environment,authenticationManager());
    autenticationFilter.setAuthenticationManager(authenticationManager());
    autenticationFilter.setFilterProcessesUrl(environment.getProperty("path.login"));
    return autenticationFilter;
}
@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
}

}







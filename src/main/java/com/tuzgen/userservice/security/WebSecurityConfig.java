package com.tuzgen.userservice.security;

import com.tuzgen.userservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@ComponentScan("com.tuzgen.userservice.services")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final UserService userDetailsService;

    public WebSecurityConfig(DataSource dataSource, UserService userDetailsService) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(authenticationProvider())
                .jdbcAuthentication()
                .dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ! Temporary we should not disable cors and csrf.
        // else does not work on Postman.
        http.cors().and().csrf().disable();

        http.authorizeRequests()
                // * public endpoints
                .antMatchers(HttpMethod.GET, "/api/v1/users/page").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/all").authenticated()
                .antMatchers(HttpMethod.GET, "/api/v1/users/{userId}").authenticated() // find out how to use path

                .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()


                .and().httpBasic();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

}

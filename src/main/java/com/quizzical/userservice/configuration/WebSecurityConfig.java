package com.quizzical.userservice.configuration;

import com.quizzical.userservice.repositories.UserRepository;
import com.quizzical.userservice.security.ApiKeyFilter;
import com.quizzical.userservice.security.UsernamePasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@ComponentScan("com.tuzgen.userservice.repositories")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;

    public WebSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ! Temporary we should not disable cors and csrf.
        // else does not work on Postman.
        http.cors().and().csrf().disable();

        http.authorizeRequests()
                // * public endpoints
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST).authenticated()
                .antMatchers(HttpMethod.PUT).authenticated()
                .antMatchers(HttpMethod.DELETE).authenticated()
//                .antMatchers(HttpMethod.GET, "**/users/page").permitAll()
//                .antMatchers(HttpMethod.GET, "**/users/all").authenticated()
//                .antMatchers(HttpMethod.GET, "**/users/{userId}/**").authenticated()
//                .antMatchers(HttpMethod.POST, "**/users/**").permitAll()
                .and().addFilterBefore(new ApiKeyFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin().and()
                .httpBasic();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new UsernamePasswordAuthenticationProvider(passwordEncoder(), userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

}

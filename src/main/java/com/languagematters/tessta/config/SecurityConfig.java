package com.languagematters.tessta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/index*",
                        "/static/**",
                        "/*.js",
                        "/*.json",
                        "/*.ico"
                ).permitAll()
                .antMatchers(
                        "/signin"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                    .loginPage("/signin")
                    .authorizationEndpoint()
                        .baseUri("/signin/oauth2/authorization");
    }

}

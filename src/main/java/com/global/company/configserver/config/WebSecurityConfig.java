package com.global.company.configserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${security.user.username}")
    String username;

    @Value("${security.user.password}")
    String password;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        RequestMatcher allOtherEndpoints = new RegexRequestMatcher(".*", null);

        http
                .requestMatcher(allOtherEndpoints)
                .authorizeRequests()
                .anyRequest()
                .hasRole("ACTUATOR")
                .and()
                .httpBasic()
                .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception
    {
        webSecurity
                .ignoring()
                // All of Spring Security will ignore the requests
                .antMatchers(HttpMethod.POST, "/encrypt")
                .regexMatchers("/manage/hystrix.stream")
                .regexMatchers("/actuator/health")
                .regexMatchers("/health")
                .regexMatchers(".*/logConfig[^/]*.xml")
                .regexMatchers("/config-server")
                .regexMatchers("/")
                .regexMatchers("/api-docs.*")
                .regexMatchers("/swagger-resources/.*");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        Map encoders = new HashMap<>();
        encoders.put("scrypt", new SCryptPasswordEncoder());

        PasswordEncoder passwordEncoder =
                new DelegatingPasswordEncoder("scrypt", encoders);
        auth
                .inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser(username).password(password).roles("ACTUATOR");
    }
}

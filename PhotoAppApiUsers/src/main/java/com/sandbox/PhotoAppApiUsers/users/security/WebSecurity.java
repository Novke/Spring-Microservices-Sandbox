package com.sandbox.PhotoAppApiUsers.users.security;

import com.sandbox.PhotoAppApiUsers.users.ui.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurity {

    @Autowired
    private Environment environment;
    @Autowired
    private UsersService usersService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    protected SecurityFilterChain mockFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        managerBuilder.userDetailsService(usersService).passwordEncoder(passwordEncoder);
        AuthenticationManager authenticationManager = managerBuilder.build();

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, usersService, environment);
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));

        http.authenticationManager(authenticationManager);

        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(h -> h.frameOptions(f -> f.sameOrigin()));
        http.authorizeHttpRequests(re -> re

//                        .requestMatchers(new AntPathRequestMatcher("/users/**")).access(
//                        new WebExpressionAuthorizationManager("hasIpAddress('"+environment.getProperty("gateway.ip")+"')"))

//                        .requestMatchers(HttpMethod.GET, "/actuator/health").authenticated()
//                        .requestMatchers(HttpMethod.GET ,"/actuator/circuitbreakerevents").authenticated()


                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .anyRequest().permitAll())

                .addFilter(new AuthorizationFilter(authenticationManager, environment))
                .addFilter(authenticationFilter)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


}

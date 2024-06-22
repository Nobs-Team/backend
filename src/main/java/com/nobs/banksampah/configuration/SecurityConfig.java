package com.nobs.banksampah.configuration;

import com.nobs.banksampah.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoderConfig.passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authentication -> {
            throw new UnsupportedOperationException("Custom authentication manager is not configured properly");
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(new AntPathRequestMatcher("/api/login")).permitAll() // Allow access to /public/**
                                                                                      // without authentication
                .requestMatchers(new AntPathRequestMatcher("/api/admin/**")).hasRole("admin") // Require ADMIN role for
                                                                                              // /admin/**
                .requestMatchers(new AntPathRequestMatcher("/api/user")).hasRole("user")
                .anyRequest().authenticated() // Require authentication for any other request
        )
                .logout(logout -> logout
                        .permitAll() // Allow everyone to log out
                );

        return http.build();
    }
}

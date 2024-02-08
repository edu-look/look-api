package com.github.edulook.look.infra.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class OAuthClientSecurityConfig {
    private static final String[] securityMatcher = {
            "/v1/**", "oauth2/**", "login/**"
    };
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .securityMatcher(securityMatcher)
            .authorizeHttpRequests( auth -> {
                auth.requestMatchers("/public/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
            })
            .oauth2Login(auth ->
                auth.loginPage("/login")
                .permitAll()
            )
            .oauth2ResourceServer(config -> config.jwt(Customizer.withDefaults()))
            .build();
    }
}

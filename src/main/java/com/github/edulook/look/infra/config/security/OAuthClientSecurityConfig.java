package com.github.edulook.look.infra.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.google.common.annotations.Beta;


@Configuration
@EnableWebSecurity
public class OAuthClientSecurityConfig {
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests( auth -> {
                auth.anyRequest().authenticated();
            })
            .oauth2Login(Customizer.withDefaults())
            .build();
    }

    // @Bean
    @Beta
    @Deprecated
    SecurityFilterChain securityFilterChainWithRedirectionEndpoint(HttpSecurity http) throws Exception {
        var ROOT = "/";
        return http
            .authorizeHttpRequests( auth -> {
                auth.anyRequest().authenticated();
            })
            .oauth2Login(oauth2Login -> oauth2Login
                .redirectionEndpoint(redirectionEndpoint ->
                    redirectionEndpoint
                        .baseUri(ROOT))
                .defaultSuccessUrl(ROOT, Boolean.TRUE)
            )
            .build();
    }

    // @Bean
    @Beta
    @Deprecated
    public SecurityFilterChain securityFilterChainWithJWT(HttpSecurity http) throws Exception {
        // https://github.com/Baeldung/spring-security-oauth/tree/master/oauth-jwt

        return http
            .authorizeHttpRequests( auth -> { 
                auth.anyRequest().authenticated();
             })
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
            .build();
    }
}

package com.github.edulook.look.infra.config.di;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;

import com.google.common.annotations.Beta;

@Configuration
// @Beta
public class DecoderJWT {
    
    @Value("${spring.security.oauth2.client.resourceserver.jwt.issuer-uri:}")
    private String issuerUri;
    
    // @Bean
    @Beta
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }

    // @Bean
    @Beta
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        return ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
    }
}

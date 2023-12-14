package com.github.edulook.look.endpoint.internal.mapper.shared.impl;

import com.github.edulook.look.core.exceptions.ForbiddenException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.github.edulook.look.endpoint.internal.mapper.shared.OAuth2AndUserAuthDTOMapper;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO.JWT;

import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

@Component
public class OAuth2AndUserAuthDTOMapperImpl implements OAuth2AndUserAuthDTOMapper {

    @Override
    public UserAuthDTO toDTO(OAuth2User oAuth2User) {
        OidcUserAuthority authority = (OidcUserAuthority) oAuth2User.getAuthorities()
            .stream()
            .filter(it -> it instanceof OidcUserAuthority)
            .findFirst()
            .get();        
        
        return UserAuthDTO
            .builder()
            .name(oAuth2User.<String>getAttribute("name"))
            .id(oAuth2User.<String>getAttribute("sub"))
            .email(oAuth2User.<String>getAttribute("email"))
            .photo(oAuth2User.<String>getAttribute("picture"))
            .jwt(JWT
                .builder()
                .token(authority.getIdToken().getTokenValue())
                .expiresAt(authority.getIdToken().getExpiresAt())
                .createdAt(authority.getIdToken().getIssuedAt())
                .build())
            .build();
    }

    @Override
    public UserAuthDTO toDTO(Jwt jwt) {
        var claims = jwt.getClaims();

        return UserAuthDTO
            .builder()
            .name((String) claims.getOrDefault("name", ""))
            .id((String) claims.getOrDefault("sub", ""))
            .email((String) claims.getOrDefault("email", ""))
            .photo((String) claims.getOrDefault("picture", ""))
            .jwt(JWT
                .builder()
                .token(jwt.getTokenValue())
                .expiresAt(jwt.getExpiresAt())
                .createdAt(jwt.getIssuedAt())
                .build())
            .build();
    }
}

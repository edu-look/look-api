package com.github.edulook.look.endpoint.internal.mapper.shared.impl;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.github.edulook.look.endpoint.internal.mapper.shared.OAuth2AndUserAuthDTOMapper;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO.JWT;

import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

@Component
public class OAuth2AndUserAuthDTOMapperImpl implements OAuth2AndUserAuthDTOMapper {

    @Override
    public UserAuthDTO toDTO(OAuth2User source) {
        OidcUserAuthority authority = (OidcUserAuthority) source.getAuthorities()
            .stream()
            .filter(it -> it instanceof OidcUserAuthority)
            .findFirst()
            .get();        
        
        return UserAuthDTO
            .builder()
            .name(source.<String>getAttribute("name"))
            .id(source.<String>getAttribute("sub"))
            .email(source.<String>getAttribute("email"))
            .photo(source.<String>getAttribute("picture"))
            .jwt(JWT
                .builder()
                .token(authority.getIdToken().getTokenValue())
                .expiresAt(authority.getIdToken().getExpiresAt())
                .createdAt(authority.getIdToken().getIssuedAt())
                .build())
            .build();
    }
}

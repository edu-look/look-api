package com.github.edulook.look.endpoint.internal.mapper.shared;

import org.mapstruct.Mapper;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import org.springframework.security.oauth2.jwt.Jwt;

@Mapper(componentModel = "spring")
public interface OAuth2AndUserAuthDTOMapper {
    UserAuthDTO toDTO(OAuth2User source);
    UserAuthDTO toDTO(Jwt jwt);
}

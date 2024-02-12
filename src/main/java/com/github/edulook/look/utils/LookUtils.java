package com.github.edulook.look.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.edulook.look.endpoint.internal.mapper.shared.OAuth2AndUserAuthDTOMapper;
import com.github.edulook.look.endpoint.internal.mapper.shared.impl.OAuth2AndUserAuthDTOMapperImpl;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Slf4j
public class LookUtils {

    private static final OAuth2AndUserAuthDTOMapper mapper = new OAuth2AndUserAuthDTOMapperImpl();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(Object obj) {
      try {
        return objectMapper.writeValueAsString(obj);
      } catch (JsonProcessingException e) {
        log.error("object to json", e);
      }

      return "{}";
    }

    public static Optional<UserAuthDTO> toUserDTO(Object principal) {
        var parses = List.of(Jwt.class, OAuth2User.class);
        try {
            if(principal instanceof Jwt) {
                return Optional.ofNullable(mapper.toDTO((Jwt) principal));
            }
            if(principal instanceof OAuth2User) {
                return Optional.ofNullable(mapper.toDTO((OAuth2User) principal));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return UserAuthDTO.none();
    }

    public static File mkdir(String dirname) {
        var dir = new File(dirname);
        if (!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }
}

package com.github.edulook.look.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.edulook.look.endpoint.internal.mapper.shared.OAuth2AndUserAuthDTOMapper;
import com.github.edulook.look.endpoint.internal.mapper.shared.impl.OAuth2AndUserAuthDTOMapperImpl;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.io.File;

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

    public static UserAuthDTO toUserDTO(Object principal) {
        try {
            return mapper.toDTO((Jwt) principal);
        }
        catch (Exception e) {
            return mapper.toDTO((OAuth2User) principal);
        }
    }

    public static File mkdir(String dirname) {
        var dir = new File(dirname);
        if (!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }
}

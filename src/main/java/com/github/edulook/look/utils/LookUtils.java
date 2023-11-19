package com.github.edulook.look.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LookUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(Object obj) {
      try {
        return objectMapper.writeValueAsString(obj);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        log.error("object to json", e);
      }

      return "{}";
    }
}

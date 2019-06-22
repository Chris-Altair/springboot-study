package com.study.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static String toJson(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }
}

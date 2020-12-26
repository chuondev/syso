package com.chuonye.syso.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json 转换辅助工具
 * 
 * @author chuonye@foxmail.com
 */
public final class JsonUtils {

    public static ObjectMapper mapper = createDefaultJsonMapper();

    private JsonUtils() {}

    public static ObjectMapper createDefaultJsonMapper() {
        // Create object mapper
        ObjectMapper mapper = new ObjectMapper();
        // Configure
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        return mapper;
    }
    
    public static <T> String objectToJson(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException("Could not map to object");
        }
    }

    public static <T> JsonNode objectToJsonNode(T obj) {
        return mapper.valueToTree(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Could not map to object");
        }
    }

    public static JsonNode jsonToJsonNode(String json) {
        JsonNode node;
        try {
            node = mapper.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException("IO exception while reading JSON", e);
        }
        return node;
    }

    public static <T> List<T> jsonToList(String content, Class<T> clazz) {
        JavaType listType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        try {
            return mapper.readValue(content, listType);
        } catch (IOException e) {
            throw new RuntimeException("IO exception while reading JSON", e);
        }
    }
    
    public static <K,V> Map<K,V> jsonToMap(String content, Class<K> keyClass, Class<V> valueClass) {
        JavaType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, keyClass, valueClass);
        
        try {
            return mapper.readValue(content, mapType);
        } catch (IOException e) {
            throw new RuntimeException("IO exception while reading JSON", e);
        }
    }
}

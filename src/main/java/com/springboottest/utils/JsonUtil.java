package com.springboottest.utils;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.TimeZone;

/**
 * Created by ymind on 2018/4/18.
 */
public final class JsonUtil {
    public static final ObjectMapper jsonObjectMapper = new ObjectMapper();

    /**
     * 将对象转换为 JSON 字符串。
     *
     * @param obj 要转换的对象。
     */
    public static String toJson(Object obj) {
        return toJson(obj, false);
    }

    /**
     * 将对象转换为 JSON 字符串。
     *
     * @param obj    要转换的对象。
     * @param format 是否要格式化输出 JSON 字符串。
     */
    public static String toJson(Object obj, boolean format) {
        jsonObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jsonObjectMapper.setTimeZone(TimeZone.getTimeZone("CTT"));

        try {
            if (format) return jsonObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            else return jsonObjectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T fromJson(String json, Class<T> valueType) {
        if (StringUtils.isEmpty(json)) return null;

        jsonObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jsonObjectMapper.setTimeZone(TimeZone.getTimeZone("CTT"));

        try {
            return jsonObjectMapper.readValue(json, valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T fromJson(String json, JavaType javaType) {
        if (StringUtils.isEmpty(json)) return null;

        try {
            return jsonObjectMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static TypeFactory getJsonTypeFactory() {
        return jsonObjectMapper.getTypeFactory();
    }

    public static String toString(Object obj) {
        String s = null;

        try {
            if(obj != null) {
                ObjectMapper e = new ObjectMapper();
                e.configure(SerializationFeature.INDENT_OUTPUT, false);
                e.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
                e.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                s = e.writeValueAsString(obj);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return s;
    }
}

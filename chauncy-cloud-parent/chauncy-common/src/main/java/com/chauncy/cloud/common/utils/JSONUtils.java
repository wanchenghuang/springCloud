package com.chauncy.cloud.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.chauncy.cloud.common.constant.Constants.STANDARD_FORMAT;

/**
 * @Author cheng
 * @create 2020-03-04 21:50
 */
public class JSONUtils {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);

    static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * init
     */
    private static final JSONUtils instance = new JSONUtils();

    private JSONUtils() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setTimeZone(TimeZone.getDefault())
                .setSerializationInclusion(JsonInclude.Include.ALWAYS)
                .setDateFormat(new SimpleDateFormat(STANDARD_FORMAT))
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .registerModule(new JavaTimeModule());
    }

    /**
     * object 转 json
     *
     * @param object object
     * @return object to json string
     */
    @SneakyThrows
    public static String toJson(@NonNull Object object) {
        try {
            return JSONObject.toJSONString(object, false);
        } catch (Exception e) {
            logger.error("object to json exception!", e);
        }

        return null;
    }


    /**
     * 反序列化指定JSON转换成指定的类的对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * classOfT
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return JSONObject.parseObject(json, clazz);
        } catch (Exception e) {
            logger.error("parse object exception!", e);
        }
        return null;
    }


    /**
     * json to list
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return list
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        try {
            return JSONArray.parseArray(json, clazz);
        } catch (Exception e) {
            logger.error("JSONArray.parseArray exception!", e);
        }

        return new ArrayList<>();
    }


    /**
     * 检查json是否合法
     *
     * @param json json
     * @return true if valid
     */
    public static boolean checkJsonVaild(String json) {

        if (StringUtils.isEmpty(json)) {
            return false;
        }

        try {
            objectMapper.readTree(json);
            return true;
        } catch (IOException e) {
            logger.error("check json object valid exception!", e);
        }

        return false;
    }


    /**
     *
     *
     * @param jsonNode
     * @param fieldName
     * @return
     */
    public static String findValue(JsonNode jsonNode, String fieldName) {
        JsonNode node = jsonNode.findValue(fieldName);

        if (node == null) {
            return null;
        }

        return node.toString();
    }


    /**
     * json to map
     * <p>
     *
     *
     * @param json json
     * @return json to map
     */
    public static Map<String, String> toMap(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return JSONObject.parseObject(json, new TypeReference<HashMap<String, String>>() {
            });
        } catch (Exception e) {
            logger.error("json to map exception!", e);
        }

        return null;
    }

    /**
     * json to map
     *
     * @param json   json
     * @param classK classK
     * @param classV classV
     * @param <K>    K
     * @param <V>    V
     * @return to map
     */
    public static <K, V> Map<K, V> toMap(String json, Class<K> classK, Class<V> classV) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return JSONObject.parseObject(json, new TypeReference<HashMap<K, V>>() {
            });
        } catch (Exception e) {
            logger.error("json to map exception!", e);
        }

        return null;
    }

    /**
     * object to json string
     *
     * @param object object
     * @return json string
     */
    public static String toJsonString(Object object) {
        try {
            return JSONObject.toJSONString(object, false);
        } catch (Exception e) {
            throw new RuntimeException("Json deserialization exception.", e);
        }
    }

    public static JSONObject parseObject(String text) {
        try {
            return JSONObject.parseObject(text);
        } catch (Exception e) {
            throw new RuntimeException("Json deserialization exception.", e);
        }
    }

    public static JSONArray parseArray(String text) {
        try {
            return JSONObject.parseArray(text);
        } catch (Exception e) {
            throw new RuntimeException("Json deserialization exception.", e);
        }
    }


    /**
     * json serializer
     */
    public static class JsonDataSerializer extends JsonSerializer<String> {

        @Override
        public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeRawValue(value);
        }

    }

    /**
     * json data deserializer
     */
    public static class JsonDataDeserializer extends JsonDeserializer<String> {

        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            return node.toString();
        }

    }

    /**
     * json 字符串转为实体
     *
     * @param json json 字符串
     * @param clazz 实体class
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> T toBean(@NonNull String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }

}

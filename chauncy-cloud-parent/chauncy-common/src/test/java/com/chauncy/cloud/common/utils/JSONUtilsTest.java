package com.chauncy.cloud.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @Author cheng
 * @create 2020-03-04 22:10
 */
public class JSONUtilsTest {

    @Test
    public void toMap() {

        String jsonStr = "{\"id\":\"1001\",\"name\":\"Jobs\"}";

        Map<String, String> models = JSONUtils.toMap(jsonStr);
        Assert.assertEquals(models.get("id"), "1001");
        Assert.assertEquals(models.get("name"), "Jobs");

    }


    @Test
    public void String2MapTest() {
        String str = list2String();

        List<LinkedHashMap> maps = JSONUtils.toList(str,
                LinkedHashMap.class);

        Assert.assertEquals(maps.size(), 1);
        Assert.assertEquals(maps.get(0).get("mysql client name"), "mysql200");
        Assert.assertEquals(maps.get(0).get("mysql address"), "192.168.xx.xx");
        Assert.assertEquals(maps.get(0).get("port"), "3306");
        Assert.assertEquals(maps.get(0).get("no index of number"), "80");
        Assert.assertEquals(maps.get(0).get("database client connections"), "190");
    }

    public String list2String() {

        LinkedHashMap<String, String> map1 = new LinkedHashMap<>();
        map1.put("mysql client name", "mysql200");
        map1.put("mysql address", "192.168.xx.xx");
        map1.put("port", "3306");
        map1.put("no index of number", "80");
        map1.put("database client connections", "190");

        List<LinkedHashMap<String, String>> maps = new ArrayList<>();
        maps.add(0, map1);
        String resultJson = JSONUtils.toJson(maps);
        return resultJson;
    }

    @Test
    public void testToJson() {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "bar");

        Assert.assertEquals("{\"foo\":\"bar\"}", JSONUtils.toJson(map));
        Assert.assertEquals(
                String.valueOf((Object) null), JSONUtils.toJson(null));
    }

    @Test
    public void testParseObject() {
        Assert.assertEquals("{\"foo\":\"bar\"}", JSONUtils.parseObject(
                "{\n" + "\"foo\": \"bar\",\n" + "}", String.class));

        Assert.assertNull(JSONUtils.parseObject("", null));
        Assert.assertNull(JSONUtils.parseObject("foo", String.class));
    }

    @Test
    public void testToList() {
        Assert.assertEquals(new ArrayList(),
                JSONUtils.toList("A1B2C3", null));
        Assert.assertEquals(new ArrayList(),
                JSONUtils.toList("", null));
    }

    @Test
    public void testCheckJsonVaild() {
        Assert.assertTrue(JSONUtils.checkJsonVaild("3"));
        Assert.assertFalse(JSONUtils.checkJsonVaild(""));
    }

    @Test
    public void testFindValue() {
        Assert.assertNull(JSONUtils.findValue(
                new ArrayNode(new JsonNodeFactory(true)), null));
    }

    @Test
    public void testToMap() {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "bar");

        Assert.assertTrue(map.equals(JSONUtils.toMap(
                "{\n" + "\"foo\": \"bar\",\n" + "}")));

        Assert.assertFalse(map.equals(JSONUtils.toMap(
                "{\n" + "\"bar\": \"foo\",\n" + "}")));

        Assert.assertNull(JSONUtils.toMap("3"));
        Assert.assertNull(JSONUtils.toMap(null));
        Assert.assertNull(JSONUtils.toMap("3", null, null));
        Assert.assertNull(JSONUtils.toMap(null, null, null));
    }

    @Test
    public void testToJsonString() {
        Map<String, Object> map = new HashMap<>();
        map.put("foo", "bar");

        Assert.assertEquals("{\"foo\":\"bar\"}",
                JSONUtils.toJsonString(map));
        Assert.assertEquals(String.valueOf((Object) null),
                JSONUtils.toJsonString(null));
    }
}

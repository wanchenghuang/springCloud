package com.chauncy.cloud.common.utils;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wanchenghuang
 * @DateTime: 2020/10/16 11:26 上午
 * @Version: 2.0.0
 * @description: 判断对象字段是否为空
 **/
public class CheckNotNullUtils {
	/**
	 * 判断一个对象中的某些字段是否为空
	 *
	 * @param obj 传入一个对象
	 * @param map key为传入需要判断的是否为空的字段，value为传入需要提示的错误信息
	 * @return 当判断出有为空的字段时，会返回错误信息，当传入的字段都不为空时，返回null
	 * @throws IllegalAccessException
	 */
	public static String checkNotNull(Object obj, Map<String,String> map) throws IllegalAccessException{

		Class aclass = obj.getClass();
		Field[] fs = aclass.getDeclaredFields();
		for (Field f : fs){
			if ((f != null) && (!"".equals(f))){
				//设置私有属性可以访问
				f.setAccessible(true);
				Object o = f.get(obj);
				String fieldName = f.getName();
				if (map.containsKey(fieldName)){
					if (o == null){
						return map.get(fieldName);
					}
					if (o instanceof String){
						if (o == null || "".equals(((String) o).trim())){
							return  map.get(fieldName);
						}
					}
				}
			}
		}
		return null;
	}

	public static void main(String[] args) throws IllegalAccessException {
		List<User> users = Lists.newArrayList();
		User user1 = new User();
		User user2 = new User();
		user1.setId("1");
		user1.setName("a");
		user2.setName("b");
		users.add(user1);
		users.add(user2);
		String result = "";
		for (User user : users) {
			Map map = new HashMap();
			map.put("id", "id不能为空");
			map.put("name", "姓名不能为空");
			result = checkNotNull(user, map);
			if (StringUtils.isNotBlank(result)){
				break;
			}
		}
		System.out.println(result);
	}

	@Data
	static class User{
		private String id;
		private String name;
	}
}

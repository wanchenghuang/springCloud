package com.chauncy.cloud.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: wanchenghuang
 * @DateTime: 2020/10/16 11:24 上午
 * @Version: 2.0.0
 * @description: 空值处理
 **/
public class BeanNullHandleUtils {

	/**
	 * 获取空值属性
	 *
	 * @param source
	 * @return
	 */
	public static String[] getNullPropertyNames (Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for(java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null) {
				emptyNames.add(pd.getName());
			}
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	/**
	 * 参数为null的时候不复制
	 *
	 * @param src
	 * @param target
	 */
	public static void copyPropertiesIgnoreNull(Object src, Object target){
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}
}

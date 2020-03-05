package com.chauncy.cloud.common.utils;

import java.util.Collection;

/**
 * @Author cheng
 * @create 2020-03-04 22:54
 */
public class ValidateUtils {

    public static boolean isValid(Collection<?> c) {
        return c != null && c.size() > 0;
    }


    public static boolean isValid(Object [] o) {
        return o != null && o.length > 0;
    }

    public static boolean isNotValid(Collection<?> c) {
        return !isValid(c);
    }
}

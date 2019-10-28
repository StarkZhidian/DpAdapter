package com.vane;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Create by vane on 2019/10/25
 * Email: 1532033525@qq.com
 * Describe: 工具类
 */
public class Utils {

    public static String toString(Object object) {
        if (object == null) {
            return "";
        }
        Class c = object.getClass();
        StringBuilder builder = new StringBuilder();
        try {
            builder.append('{');
            Field[] fields = c.getDeclaredFields();
            Class fieldClass;
            Field f;
            for (int i = 0; i < fields.length; i++) {
                f = fields[i];
                f.setAccessible(true);
                fieldClass = f.getType();
                builder.append('\"').append(f.getName()).append("\": ");
                // 字段为基本数据类型，直接 append
                if (fieldClass.isPrimitive()) {
                    builder.append(f.get(object));
                } else {
                    // 字段非基本数据类型，则递归解析
                    try {
                        builder.append(toString(f.get(object)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (i < fields.length - 1) {
                    builder.append(", ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return builder.append('}').toString();
    }
}

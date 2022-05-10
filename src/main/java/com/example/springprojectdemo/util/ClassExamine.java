package com.example.springprojectdemo.util;

import com.mysql.cj.util.StringUtils;

import java.lang.reflect.Field;

/**
 * @author fluoxtin created on 2022/4/9
 */

public class ClassExamine {

    /**
     * 对象字段互补。传入一个同类型被补充对象和完整对象，
     * 如果被补充对象中有字段为null或者字符串为空，就用完整对象对应的字段值补上去；
     * 如果被补充对象中某字段不为空则保留它自己的值。
     * @param origin
     * @param intactObject
     * @param <T>
     * @throws Exception
     */
    public static <T> void objectOverlap(T origin, T intactObject) throws Exception {
        Field[] fields = origin.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType() == String.class) {
                if (StringUtils.isNullOrEmpty((String) field.get(origin))) {
                    field.set(origin, field.get(intactObject));
                }
            } else {
                if (field.get(origin) == null) {
                    field.set(origin, field.get(intactObject));
                }
            }
        }
    }
}

package org.example.designs.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 反射工具类
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-11-19
 */
public class MyReflectUtil {

    /**
     * 获取具有 getter 和 setter 方法的字段列表
     *
     * @param clazz 要分析的类
     * @return 字段列表
     */
    public static List<Field> getFieldsWithGetterAndSetter(Class<?> clazz) {
        List<Field> fieldsWithAccessors = new ArrayList<>();
        Set<String> getters = new HashSet<>();
        Set<String> setters = new HashSet<>();

        // 获取所有方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();

            // 判断 getter 方法
            if (methodName.startsWith("get") && method.getParameterCount() == 0) {
                String propertyName = methodName.substring(3);
                getters.add(propertyName);
            }

            // 判断 setter 方法
            if (methodName.startsWith("set") && method.getParameterCount() == 1) {
                String propertyName = methodName.substring(3);
                setters.add(propertyName);
            }
        }

        // 获取所有字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String capitalizedFieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

            // 如果字段同时具有对应的 getter 和 setter
            if (getters.contains(capitalizedFieldName) && setters.contains(capitalizedFieldName)) {
                fieldsWithAccessors.add(field);
            }
        }

        return fieldsWithAccessors;
    }
}

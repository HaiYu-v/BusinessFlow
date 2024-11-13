package org.example.designs.utils.beanUtils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.TypeConverter;

import java.lang.reflect.Type;

/**
 * @Program: 11.designs
 * @Author: 海里的鱼
 * @Create: 2024-11-07 19:40
 * @Name: TODO
 * @Desc: TODO
 */
public class BeanCopyUtil {

    // 创建 CopyOptions 对象并设置自定义转换器
    static private CopyOptions copyOptions = CopyOptions.create()
            .setIgnoreNullValue(true)   // 忽略 null 值
            .setIgnoreCase(true)       // 忽略大小写
            .setConverter(new TypeConverter(){
                @Override
                public Object convert(Type type, Object o) {

                    return "1";
                }
            }); // 设置自定义转换器

    public static void copyProperties(Object source, Object target) {
        BeanUtil.copyProperties(source, target,copyOptions);
    }
//    public static void copyProperties(Object source, Object target) {
//        Field[] sourceFields = source.getClass().getDeclaredFields();
//        for (Field sourceField : sourceFields) {
//            if (sourceField.isAnnotationPresent(CopyTo.class)) {
//                CopyTo copyTo = sourceField.getAnnotation(CopyTo.class);
//                String targetFieldName = copyTo.value();
//
//                sourceField.setAccessible(true);
//                Field targetField = null;
//                try {
//                    targetField = target.getClass().getDeclaredField(targetFieldName);
//                    targetField.setAccessible(true);
//                    targetField.set(target, sourceField.get(source));
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
}

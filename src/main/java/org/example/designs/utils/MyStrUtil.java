package org.example.designs.utils;

/**
 * 字符串工具
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-29
 */
public class MyStrUtil {
    public static String append(String... params){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            if(null != params[i] && !"".equals(params[i]))
            builder.append(params[i]);
        }
        return builder.toString();
    }
}

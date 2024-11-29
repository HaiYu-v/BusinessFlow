package org.example.designs.conver.core;

import cn.hutool.core.bean.BeanUtil;
import org.example.designs.conver.analysis.DefaultAnalyzer;
import org.example.designs.conver.analysis.IAnalyzer;
import org.example.designs.conver.desc.ConverDesc;
import org.example.designs.conver.rule.FieldRules;
import org.example.designs.utils.MyReflectUtil;
import java.lang.reflect.Field;
import java.util.List;


/**
 * 转换器
 *
 * <p>
 *     将源数据转换为目标数据，转换时需要提供数据源
 *     转换时再从数据源加载值，扩展性会更好一些
 *
 *     记录一个code和他的一个field
 *     如果field为空，则表示此code指向一个数值，而非Data
 *     如果field不为空，则取data的此field值
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-20
 */
public class Converter {

    private static IAnalyzer analyzer = new DefaultAnalyzer();


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 转换
     *
     *  - 匹配rule > 匹配code > 抛异常
     *   - 有rule，则根据rule转换，无rule。
     *   - 无rule，则直接在数据源里匹配key
     *
     * @param  sourceType  目标bean的类型
     * @param  sourceCode  目标data的code
     * @param  dataRules data转换规则缓存
     * @param  dataSource  数据源
     * @return 转换是否成功
     * @throws Exception
     */
    public static <T> T conver(Class<T> sourceType, String sourceCode, DataRules dataRules, IDataSource dataSource,Boolean isThrow) throws ConverException {
        try {
            //获取source
            Object sourceCache = dataSource.get(sourceCode);
            if(null == sourceCache){
                if(!isThrow) return null;
                throw new ConverException("缓存里找不到sourceCode["+sourceCode+"]");
            }
            //基础属性
            try {
                if(sourceType.isPrimitive()){
                    return (T) sourceCache;
                }
            } catch (Exception e) {
                if(!isThrow) return null;
                throw new ConverException("sourceCode["+sourceCode+"]无法转成type["+sourceType.getName()+"]",e);
            }

            T source = null;
            try {
                source = sourceType.newInstance();
            } catch (Exception e){
                if(!isThrow) return null;
                throw new ConverException("class["+sourceType.getName()+"]无法实例化",e);
            }

            //有rule
            FieldRules fieldRules = dataRules.get(sourceCode);
            if(null != fieldRules){
                //同名字段先简单映射
                BeanUtil.copyProperties(sourceCache,source);

                List<Field> fields = MyReflectUtil.getFieldsWithGetterAndSetter(sourceType);
                for(Field field:fields){
                    // 设置字段可访问
                    field.setAccessible(true);
                    // 修改字段值
                    ConverDesc converDesc = fieldRules.get(field.getName());
                    if(null == converDesc) continue;
                    field.set(source,converDesc.getConverValue(dataSource,field.getType()));
                }
                return source;
            }

            //无rule，但有code
            //类型相同和类型不相同,都可以简单映射同名字段
            BeanUtil.copyProperties(sourceCache,source);
            return source;

        }catch (Exception e) {
            if(!isThrow) return null;
            throw new ConverException("source[" + sourceCode + "]转换失败",e);
        }
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 解析转换规则
     *
     * @param rules 转换规则
     * @param dataRulesCache data转换规则缓存
     * @return {@link FieldRules }
     */
    public static boolean analysis(Object rules, DataRules dataRulesCache) throws ConverException {
        try {
            analyzer.analysis(rules,dataRulesCache);
        }catch (Exception e) {
            throw new ConverException("转换规则解析失败",e);
        }
        return true;
    }

    public static IAnalyzer getAnalyzer() {
        return analyzer;
    }

    public static void setAnalyzer(IAnalyzer analyzer) {
        Converter.analyzer = analyzer;
    }
}

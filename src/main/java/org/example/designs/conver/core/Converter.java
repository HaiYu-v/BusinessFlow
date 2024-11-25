package org.example.designs.conver.core;

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
     * @param  target      目标data
     * @param  targetCode  目标data的code
     * @param  dataRules data转换规则缓存
     * @param  dataSource  数据源
     * @return 转换是否成功
     * @throws Exception
     */
    public static boolean conver(Object target, String targetCode, DataRules dataRules, IDataSource dataSource) throws ConverException {
        try {
            //有rule
            FieldRules fieldRules = dataRules.get(targetCode);
            if(null != fieldRules){
                List<Field> fields = MyReflectUtil.getFieldsWithGetterAndSetter(target.getClass());
                for(Field field:fields){
                    // 设置字段可访问
                    field.setAccessible(true);
                    // 修改字段值
                    ConverDesc converDesc = fieldRules.get(field.getName());
                    field.set(target,converDesc.getConverValue(dataSource));
                }
                return true;
            }

            //无rule，但有code
            if(dataSource.contains(targetCode)){
                target = dataSource.get(targetCode);
                return true;
            }

            throw new ConverException("targetCode["+targetCode+"]不存在");

        } catch (Exception e) {
            throw new ConverException(e);
        }
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 解析规则JSON
     * TODO 增加一个解析器
     *
     * @param json rule字符串
     * @param dataRulesCache data转换规则缓存
     * @return {@link FieldRules }
     */
    public static boolean analysis(String json, DataRules dataRulesCache) throws ConverException {
        try {
            analyzer.analysis(json,dataRulesCache);
        } catch (Exception e) {
            throw new ConverException(e);
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
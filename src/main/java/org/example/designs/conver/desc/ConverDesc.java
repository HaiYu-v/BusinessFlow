package org.example.designs.conver.desc;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.designs.conver.core.ConverException;
import org.example.designs.conver.converter.ConverType;
import org.example.designs.conver.core.IDataSource;
import org.example.designs.conver.formula.Calculator;

import java.util.HashMap;
import java.util.Map;

/**
 * 转换描述
 *
 * <p>
 *     通过【简单映射，常量，公式】等转换方式，将一个值转成另一个值
 *     需要提供一个数据源 {@link IDataSource}
 *     常量也是通过公式的方式去赋值
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-20
 */
public class ConverDesc {
    //转换方式
    private String type;
    //来源dataCode
    private Map<String,SourceDesc> sourceMap = new HashMap<>();
    //转换公式
    private String formula;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取目标值
     *
     * 通过源值，计算获得目标值
     *
     * @return 计算值
     */
    public Object getConverValue(IDataSource dataSource) throws Exception {
        //简单映射
        if(type.equals(ConverType.SIMPLE_MAPPING.getCode())){
            return ((SourceDesc)(sourceMap.values().toArray()[0])).getValue(dataSource);
        //常量也由公式来赋值
        }else if(type.equals(ConverType.CONSTANT.getCode()) || type.equals(ConverType.FORMULA_CALCULATION.getCode())){
            return Calculator.compute(sourceMap,formula,dataSource);
        }else {
            throw new ConverException("不支持的转换方式:["+type+"]");
        }
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 构建方法
     *
     * @param json converJSON字符串
     * @return {@link ConverDesc }
     */
    public static ConverDesc build(String json){
        JSONObject converJSON = JSONUtil.parseObj(json);
        String type = converJSON.getStr("type");
        String code = converJSON.getStr("code");
        String field = converJSON.getStr("field");
        String formula = converJSON.getStr("formula");
        return new ConverDesc(type,code,field,formula);
    }

    public ConverDesc(String type, String code, String field, String formula) {
        this.type = type;
        this.sourceMap.put(getKey(code,field),new SourceDesc(code,field));
        this.formula = formula;
    }

    private String getKey(String code,String field){
        StringBuilder builder = new StringBuilder();
        if(StrUtil.isNotBlank(code)){
            builder.append(code);
            if (StrUtil.isNotBlank(field)){
                builder.append(".").append(field);
            }
        }
        return builder.toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, SourceDesc> getSourceMap() {
        return sourceMap;
    }

    public void setSourceMap(Map<String, SourceDesc> sourceMap) {
        this.sourceMap = sourceMap;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}

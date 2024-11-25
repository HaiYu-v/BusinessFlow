package org.example.designs.conver.desc;


import org.example.designs.conver.core.ConverException;
import org.example.designs.conver.converter.ConverType;
import org.example.designs.conver.core.IDataSource;
import org.example.designs.conver.formula.Calculator;
import java.util.HashMap;
import java.util.List;
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
    private ConverType type;
    //来源dataCode
    private Map<String,SourceDesc> sourceMap = new HashMap<>();
    //转换公式
    private String formula;


    public ConverDesc(ConverType type, SourceDesc sourceDesc, String formula) {
        this.type = type;
        this.sourceMap.put(sourceDesc.getKey(),sourceDesc);
        this.formula = formula;
    }

    public ConverDesc(ConverType type, List<SourceDesc> sourceDesc, String formula) {
        this.type = type;
        this.formula = formula;
        addSourceBatch(sourceDesc);
    }

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
        if(type.equals(ConverType.SIMPLE_MAPPING)){
            return ((SourceDesc)(sourceMap.values().toArray()[0])).getValue(dataSource);
        //常量也由公式来赋值
        }else if(type.equals(ConverType.CONSTANT) || type.equals(ConverType.FORMULA_CALCULATION)){
            return Calculator.compute(sourceMap,formula,dataSource);
        }else {
            throw new ConverException("不支持的转换方式:["+type+"]");
        }
    }

//    /**
//     * -----------------------------------------------------------------------------------------------------------------
//     * 构建方法
//     *
//     * @param json converJSON字符串
//     * @return {@link ConverDesc }
//     */
//    public static ConverDesc build(String json){
//        JSONObject converJSON = JSONUtil.parseObj(json);
//        String type = converJSON.getStr("type");
//        String code = converJSON.getStr("code");
//        String field = converJSON.getStr("field");
//        String formula = converJSON.getStr("formula");
//        return new ConverDesc(type,code,field,formula);
//    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 添加Source
     *
     * @param code
     * @param field
     * @return {@link ConverDesc }
     */
    public SourceDesc addSource(String code, String field){
        SourceDesc sourceDesc = new SourceDesc(code, field);
        addSource(sourceDesc);
        return sourceDesc;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 添加Source
     *
     * @param sourceDesc
     * @return {@link ConverDesc }
     */
    public SourceDesc addSource(SourceDesc sourceDesc){
        this.sourceMap.put(sourceDesc.getKey(), sourceDesc);
        return sourceDesc;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 批量添加Source
     *
     * @param sourceList
     * @return {@link ConverDesc }
     */
    public int addSourceBatch(List<SourceDesc> sourceList){
        for (SourceDesc sourceDesc : sourceList) {
            addSource(sourceDesc);
        }
        return sourceList.size();
    }



    public ConverType getType() {
        return type;
    }

    public void setType(ConverType type) {
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

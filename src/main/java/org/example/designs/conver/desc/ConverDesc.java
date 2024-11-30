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
    //来源dataCode: bean.field:SourceDesc
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
     * 获取转换后的值
     *
     * @param dataSource 数据来源
     * @param retType 返回值类型
     * @return 计算值
     * @throws Exception
     */
    public <T> T getConverValue(IDataSource dataSource, Class<T> retType) throws Exception {
            //简单映射
            if(this.type.equals(ConverType.SIMPLE_MAPPING)){
                return (T)((SourceDesc)(sourceMap.values().toArray()[0])).getValue(dataSource);
            //常量也由公式来赋值
            }else if(this.type.equals(ConverType.CONSTANT)){
                if(!sourceMap.isEmpty()){
                    throw new ConverException("常量不能有[source]");
                }
                return Calculator.compute(sourceMap,formula,dataSource,retType);
            //公式计算
            }else if(retType.equals(ConverType.FORMULA_CALCULATION)){
                return Calculator.compute(sourceMap,formula,dataSource,retType);
            } else {
                throw new ConverException("不支持的转换方式:["+ this.type +"]");
            }
    }

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

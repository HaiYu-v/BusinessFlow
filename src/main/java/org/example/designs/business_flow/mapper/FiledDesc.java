package org.example.designs.business_flow.mapper;

/**
 * 字段描述
 *
 * <p>
 *     用于描述一个字段
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-20
 */
public class FiledDesc {
    private String filed;
    private String desc;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取字段描述
     *
     * 效果如下：name(姓名)
     *
     * @return {@link String }
     */
    public String getFiledDesc(){
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(filed).append("(").append(desc).append(")").toString();
    }

    public FiledDesc(String filed, String desc) {
        this.filed = filed;
        this.desc = desc;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

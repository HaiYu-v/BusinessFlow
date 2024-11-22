package org.example.designs.business_flow.mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Data描述
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-22
 */
public class DataDesc {
    //类在项目中的路径
    //例：org.example.designs.chain.mapper.DataDesc
    private String className;

    //data的code
    private String code;

    //字段描述
    private Map<String,FiledDesc> filedDescMap = new HashMap<>();
}

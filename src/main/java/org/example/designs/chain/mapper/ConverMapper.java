package org.example.designs.chain.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mapper
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-11-20
 */
@Mapper
public interface ConverMapper {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 查询某表的所有字段和描述
     *
     * @param table
     * @param dataBase
     * @return {@link List }<{@link FiledDesc }>
     */
    @Select("SELECT COLUMN_NAME AS 'filed',COLUMN_COMMENT AS 'desc' FROM information_schema.COLUMNS " +
            "where TABLE_SCHEMA = #{dataBase} AND TABLE_NAME = #{table}")
    List<FiledDesc> getFiledDesc(@Param("table") String table,@Param("dataBase") String dataBase);
}

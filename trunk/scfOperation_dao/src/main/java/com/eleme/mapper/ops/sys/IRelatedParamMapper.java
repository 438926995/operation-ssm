package com.eleme.mapper.ops.sys;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.type.JdbcType;

import com.eleme.domain.ops.sys.RelatedParamEntity;

/**
 * 系统参数表 对应mapper
 * 
 * @author sunwei
 * @since 2015年12月28日
 */
public interface IRelatedParamMapper {


  /**
   * 查询系统参数表
   * 
   * @author sunwei
   * @since 2015年12月28日
   * @param flag
   * @return
   */
  @Select("SELECT * FROM T_ATR_RELATED_PARAM")
  @Results({
      @Result(id = true, column = "RELATED_PARAM_ID", property = "relatedParamId",
          jdbcType = JdbcType.INTEGER),
      @Result(column = "FLAG", property = "flag", jdbcType = JdbcType.VARCHAR),
      @Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR),
      @Result(column = "SUMMARY", property = "summary", jdbcType = JdbcType.VARCHAR),
      @Result(column = "CREATED_AT", property = "createdAt", jdbcType = JdbcType.TIMESTAMP),
      @Result(column = "UPDATED_AT", property = "updatedAt", jdbcType = JdbcType.TIMESTAMP)})
  public RelatedParamEntity selectByFlag(String flag);

  @Update("UPDATE T_ATR_RELATED_PARAM SET VALUE=#{value} WHERE FLAG=#{flag}")
  public void updateValueByFlag(RelatedParamEntity relatedParam);

}

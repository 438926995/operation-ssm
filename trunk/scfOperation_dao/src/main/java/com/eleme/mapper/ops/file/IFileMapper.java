package com.eleme.mapper.ops.file;

import java.util.Map;

import com.eleme.domain.ops.file.TSysExcelTemplate;

/**
 * file Mapper
 * 
 * @author sunwei
 * @since 2015年12月14日
 *
 */
public interface IFileMapper {


  /**
   * 根据模板id获取模板信息
   * 
   * @param templateId
   */
  public TSysExcelTemplate selectExcelTemplateByTemplateId(Map<String, Object> paramMap);

}

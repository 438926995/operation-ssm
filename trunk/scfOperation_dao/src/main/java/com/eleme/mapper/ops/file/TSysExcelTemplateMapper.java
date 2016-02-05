package com.eleme.mapper.ops.file;

import com.eleme.domain.ops.file.AppExcelTemplateVo;
import com.eleme.domain.ops.file.TSysExcelTemplate;
import com.eleme.domain.ops.file.TSysExcelTemplateRelation;

import java.util.List;

/**
 * excel 导出模板 Mapper
 * <p>
 * Created by sunwei on 16/1/19.
 */
public interface TSysExcelTemplateMapper {

  /**
   * 查询出所有的ExcelTemplate信息
   *
   * @return
   */
  List<AppExcelTemplateVo> selectAppExcelTemplates(int excelTemplateType);

  /**
   * 新增一个excel导出模板
   * @param excelTemplate
   */
  void insertExcelTemplate(TSysExcelTemplate excelTemplate);

  /**
   * 更新一个excel导出模板
   * @param excelTemplate
   */
  void updateExcelTemplate(TSysExcelTemplate excelTemplate);

  /**
   * 根据tempId删除
   * @param tempId
   */
  void deleteExcelTemplateById(Integer tempId);


  /**
   * 根据 模板id 删除模板关联字段
   * @param tempId
   */
  void deleteExcelTemplateRelationsByTempId(Integer tempId);

  /**
   * 批量插入模板关联字段
   * @param excelTemplateRelations
   */
  void insertExcelTemplateRelations(List<TSysExcelTemplateRelation> excelTemplateRelations);
}

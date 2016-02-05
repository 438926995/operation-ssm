package com.eleme.domain.ops.file;

import java.util.List;

/**
 * 意向贷款申请导出申请贷款数据商户信息excel模板VO
 * Created by sunwei on 16/1/26.
 */
public class AppExcelTemplateVo extends TSysExcelTemplate{

  // 获取excel 模板包含的关联字段
  private List<TSysExcelTemplateRelation> excelTemplateRelations;

  public List<TSysExcelTemplateRelation> getExcelTemplateRelations() {
    return excelTemplateRelations;
  }

  public void setExcelTemplateRelations(List<TSysExcelTemplateRelation> excelTemplateRelations) {
    this.excelTemplateRelations = excelTemplateRelations;
  }
}

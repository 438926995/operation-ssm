package com.eleme.domain.ops.file;

/**
 * 申请贷款表单提交数据
 * Created by sunwei on 16/1/26.
 */
public class AppExcelTemplateFormVo extends AppExcelTemplateVo {

  // 模板字段json字段 可转化为 List<TSysExcelTemplateRelation>
  private String excelTemplateRelationsJsonStr;

  public String getExcelTemplateRelationsJsonStr() {
    return excelTemplateRelationsJsonStr;
  }

  public void setExcelTemplateRelationsJsonStr(String excelTemplateRelationsJsonStr) {
    this.excelTemplateRelationsJsonStr = excelTemplateRelationsJsonStr;
  }
}

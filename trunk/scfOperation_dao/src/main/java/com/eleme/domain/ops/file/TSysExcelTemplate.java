package com.eleme.domain.ops.file;

import com.eleme.bean.file.SysUploadFileBean;
import com.eleme.constants.ExportExcelTplType;

import java.io.Serializable;

/**
 * excel模板对象
 * 
 * @author sunwei
 * @since 2015年12月14日
 *
 */
public class TSysExcelTemplate extends SysUploadFileBean implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6661104128566683468L;
  /**
   * excel 模板Id
   */
  private Integer excelTemplateId;
  /**
   * excel 模板名称
   */
  private String excelTemplateName = "";
  /**
   * excel 模板描述
   */
  private String excelTemplateDesc ="";

  /**
   * excel 模板类型 0: 默认导入模板 文件类型的excel模板 1: 导出字段模板
   */
  private Integer excelTemplateType = ExportExcelTplType.DEFAULT.getType();

  public Integer getExcelTemplateId() {
    return excelTemplateId;
  }

  public void setExcelTemplateId(Integer excelTemplateId) {
    this.excelTemplateId = excelTemplateId;
  }

  public String getExcelTemplateName() {
    return excelTemplateName;
  }

  public void setExcelTemplateName(String excelTemplateName) {
    this.excelTemplateName = excelTemplateName;
  }

  public String getExcelTemplateDesc() {
    return excelTemplateDesc;
  }

  public void setExcelTemplateDesc(String excelTemplateDesc) {
    this.excelTemplateDesc = excelTemplateDesc;
  }

  public Integer getExcelTemplateType() {
    return excelTemplateType;
  }

  public void setExcelTemplateType(Integer excelTemplateType) {
    this.excelTemplateType = excelTemplateType;
  }
}

package com.eleme.domain.ops.file;

import java.util.Date;

/**
 * excel 模板导出 模板与字段关联表
 * Created by sunwei on 16/1/19.
 */
public class TSysExcelTemplateRelation {

  // 关联id
  private Integer id;
  // excel模板id
  private Integer excelTplId;
  // 字段id
  private Integer columnId;
  // 数据库中字段名称
  private String columnName;
  // 显示时的字段名称
  private String columnText;
  // 创建时间
  private Date createdAt;
  // 更新时间
  private Date updatedAt;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getExcelTplId() {
    return excelTplId;
  }

  public void setExcelTplId(Integer excelTplId) {
    this.excelTplId = excelTplId;
  }

  public Integer getColumnId() {
    return columnId;
  }

  public void setColumnId(Integer columnId) {
    this.columnId = columnId;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getColumnText() {
    return columnText;
  }

  public void setColumnText(String columnText) {
    this.columnText = columnText;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}

package com.eleme.domain.mart.basic;

/**
 * 基础数据封装类
 * 
 * @author yonglin.zhu
 *
 */
public class MBasicData extends MBasicDataKey {

  // 分类ID名称
  private String typeIdName;

  // 类型CD名称
  private String typeCdName;

  // 排序
  private Integer sortIndex;

  // 是否可见
  private Integer isVisible;

  // 备注（使用目的）
  private String remarks;
  // 分页开始
  private Integer startRecord;
  // 分页每页大小
  private Integer pageSize;

  public Integer getStartRecord() {
    return startRecord;
  }

  public void setStartRecord(Integer startRecord) {
    this.startRecord = startRecord;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getTypeIdName() {
    return typeIdName;
  }

  public void setTypeIdName(String typeIdName) {
    this.typeIdName = typeIdName == null ? null : typeIdName.trim();
  }

  public String getTypeCdName() {
    return typeCdName;
  }

  public void setTypeCdName(String typeCdName) {
    this.typeCdName = typeCdName == null ? null : typeCdName.trim();
  }

  public Integer getSortIndex() {
    return sortIndex;
  }

  public void setSortIndex(Integer sortIndex) {
    this.sortIndex = sortIndex;
  }

  public Integer getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Integer isVisible) {
    this.isVisible = isVisible;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks == null ? null : remarks.trim();
  }


}

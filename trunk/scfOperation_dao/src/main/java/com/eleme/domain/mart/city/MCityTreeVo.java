package com.eleme.domain.mart.city;

/**
 * 对MCityTree映射类的扩张
 * 
 * @author huwenwen
 *
 */
public class MCityTreeVo extends MCityTree {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 5487215501593035699L;
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

}

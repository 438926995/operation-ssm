package com.eleme.domain.mart.finance;

import java.util.Date;

/**
 * 
 * 金融机构查询条件封装类
 * 
 * @author yonglin.zhu
 *
 */
public class FinanceQueryBean {
  // 分页开始
  private Integer startRecord;
  // 页面显示条数
  private Integer pageSize;
  // 用户添加时间范围
  private String createDateRange;
  // 开始时间
  private Date startDate;
  // 结束时间
  private Date endDate;

  // 机构名称
  private String foName;

  // 状态
  private String foStatus;
  // 机构ID
  private Integer foId;

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

  public String getCreateDateRange() {
    return createDateRange;
  }

  public void setCreateDateRange(String createDateRange) {
    this.createDateRange = createDateRange;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getFoName() {
    return foName;
  }

  public void setFoName(String foName) {
    this.foName = foName;
  }

  public String getFoStatus() {
    return foStatus;
  }

  public void setFoStatus(String foStatus) {
    this.foStatus = foStatus;
  }

  public Integer getFoId() {
    return foId;
  }

  public void setFoId(Integer foId) {
    this.foId = foId;
  }


}

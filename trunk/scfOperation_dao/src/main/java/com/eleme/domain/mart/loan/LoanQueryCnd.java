package com.eleme.domain.mart.loan;

import java.util.Date;

/**
 * 
 * 商户借贷申请查询条件封装类
 * 
 * @author yonglin.zhu
 *
 */
public class LoanQueryCnd {

  // 分页开始
  private Integer startRecord;
  // 页面显示条数
  private Integer pageSize;

  // 开始时间
  private Date submitTimeFrom;

  // 结束时间
  private Date submitTimeEnd;

  // 申请人
  private String proposerName;

  // 产品ID
  private Integer fpId;

  // 状态
  private String appStatus;

  // 主键
  private Integer slId;

  // 省
  private Integer cityId;
  // 市
  private Integer provinceId;
  // 订单来源
  private String orderSource;

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

  public Date getSubmitTimeFrom() {
    return submitTimeFrom;
  }

  public void setSubmitTimeFrom(Date submitTimeFrom) {
    this.submitTimeFrom = submitTimeFrom;
  }

  public Date getSubmitTimeEnd() {
    return submitTimeEnd;
  }

  public void setSubmitTimeEnd(Date submitTimeEnd) {
    this.submitTimeEnd = submitTimeEnd;
  }

  public String getProposerName() {
    return proposerName;
  }

  public void setProposerName(String proposerName) {
    this.proposerName = proposerName;
  }

  public Integer getFpId() {
    return fpId;
  }

  public void setFpId(Integer fpId) {
    this.fpId = fpId;
  }

  public String getAppStatus() {
    return appStatus;
  }

  public void setAppStatus(String appStatus) {
    this.appStatus = appStatus;
  }

  public Integer getSlId() {
    return slId;
  }

  public void setSlId(Integer slId) {
    this.slId = slId;
  }

  public Integer getCityId() {
    return cityId;
  }

  public void setCityId(Integer cityId) {
    this.cityId = cityId;
  }

  public Integer getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(Integer provinceId) {
    this.provinceId = provinceId;
  }

  public String getOrderSource() {
    return orderSource;
  }

  public void setOrderSource(String orderSource) {
    this.orderSource = orderSource;
  }



}

package com.eleme.domain.mart.loan;

import java.util.Date;

/**
 * 
 * 商户借贷意向申请查询条件封装类
 * 
 * @author yonglin.zhu
 *
 */
public class PurposeLoanQueryCnd {

  // 分页开始
  private Integer startRecord;
  // 页面显示条数
  private Integer pageSize;
  // 地区
  private Integer cityId;
  // 省
  private Integer provinceId;
  // 申请时间开始
  private Date submitTimeFrom;
  // 申请时间结束
  private Date submitTimeTo;

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

  public Integer getCityId() {
    return cityId;
  }

  public void setCityId(Integer cityId) {
    this.cityId = cityId;
  }

  public Date getSubmitTimeFrom() {
    return submitTimeFrom;
  }

  public void setSubmitTimeFrom(Date submitTimeFrom) {
    this.submitTimeFrom = submitTimeFrom;
  }

  public Date getSubmitTimeTo() {
    return submitTimeTo;
  }

  public void setSubmitTimeTo(Date submitTimeTo) {
    this.submitTimeTo = submitTimeTo;
  }

  public Integer getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(Integer provinceId) {
    this.provinceId = provinceId;
  }
}

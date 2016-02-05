package com.eleme.bean.loan;

import java.util.Date;

/**
 * 
 * 商户借贷意向申请查询条件封装类
 * 
 * @author yonglin.zhu
 *
 */
public class PurposeLoanQueryBean {
  // 开始时间
  private Date submitTimeFrom;

  // 结束时间
  private Date submitTimeTo;

  // 省
  private Integer cityId;
  // 市
  private Integer provinceId;

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

}

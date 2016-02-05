package com.eleme.domain.mart.loan;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商户借贷意向申请封装类
 * 
 * @author yonglin.zhu
 *
 */
public class TAppPurposeLoanVo extends TAppPurposeLoan implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 8284147912273120319L;

  // 城市名称
  private String cityName;

  // 餐厅地址
  private String addressText;
  // 加入饿了么时间
  private String createdDate;
  /**
   * napos 餐厅oid
   */
  private Integer naposResOid;

  // 餐厅ID
  private Integer naposResId;
  // 日均交易额
  private BigDecimal perDayTotal;
  // 是否是重复数据 0:不重复,1:重复
  private String repeatFlag;

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public String getAddressText() {
    return addressText;
  }

  public void setAddressText(String addressText) {
    this.addressText = addressText;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public Integer getNaposResOid() {
    return naposResOid;
  }

  public void setNaposResOid(Integer naposResOid) {
    this.naposResOid = naposResOid;
  }

  public Integer getNaposResId() {
    return naposResId;
  }

  public void setNaposResId(Integer naposResId) {
    this.naposResId = naposResId;
  }

  public BigDecimal getPerDayTotal() {
    return perDayTotal;
  }

  public void setPerDayTotal(BigDecimal perDayTotal) {
    this.perDayTotal = perDayTotal;
  }

  public String getRepeatFlag() {
    return repeatFlag;
  }

  public void setRepeatFlag(String repeatFlag) {
    this.repeatFlag = repeatFlag;
  }

}

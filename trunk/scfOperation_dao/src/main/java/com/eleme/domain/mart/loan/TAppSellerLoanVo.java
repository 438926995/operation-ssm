package com.eleme.domain.mart.loan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商户借贷申请封装类
 * 
 * @author yonglin.zhu
 *
 */
public class TAppSellerLoanVo extends TAppSellerLoan implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 6217292674697215057L;

  // 节点名称
  private String nodeName;
  // 产品名称
  private String fpName;
  // 日均交易额
  private BigDecimal perDayTotal;
  // 餐厅地址
  private String addressText;
  // 加入饿了么时间
  private String createdDate;
  // 餐厅ID
  private Integer naposResId;
  // 城市
  private String cityName;
  // 审核历史记录
  private List<TApvHistoryVo> historyList;

  /**
   * napos 餐厅oid
   */
  private Integer naposResOid;

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public String getFpName() {
    return fpName;
  }

  public void setFpName(String fpName) {
    this.fpName = fpName;
  }

  public BigDecimal getPerDayTotal() {
    return perDayTotal;
  }

  public void setPerDayTotal(BigDecimal perDayTotal) {
    this.perDayTotal = perDayTotal;
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

  public Integer getNaposResId() {
    return naposResId;
  }

  public void setNaposResId(Integer naposResId) {
    this.naposResId = naposResId;
  }

  public Integer getNaposResOid() {
    return naposResOid;
  }

  public void setNaposResOid(Integer naposResOid) {
    this.naposResOid = naposResOid;
  }

  public List<TApvHistoryVo> getHistoryList() {
    return historyList;
  }

  public void setHistoryList(List<TApvHistoryVo> historyList) {
    this.historyList = historyList;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

}

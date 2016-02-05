package com.eleme.domain.mart.loan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商户借贷意向申请统计 省份,城市 封装类
 *
 * @author xudong.gu
 */
public class PurposeLoanStatisticsDetail implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -388657807488184727L;

  // 省份Id
  private Integer provId;

  // 省份统计
  private String provName;

  // 省份申请数量
  private Integer accountTotal;

  // 省份对应具体城市统计信息
  List<PurposeLoanCityStatistics> cityStatistics;

  // 省份贷款金额总计
  private BigDecimal provsAmount;

  public Integer getProvId() {
    return provId;
  }

  public void setProvId(Integer provId) {
    this.provId = provId;
  }

  public String getProvName() {
    return provName;
  }

  public void setProvName(String provName) {
    this.provName = provName;
  }

  public Integer getAccountTotal() {
    return accountTotal;
  }

  public void setAccountTotal(Integer accountTotal) {
    this.accountTotal = accountTotal;
  }

  public BigDecimal getProvsAmount() {
    return provsAmount;
  }

  public void setProvsAmount(BigDecimal provsAmount) {
    this.provsAmount = provsAmount;
  }

  public List<PurposeLoanCityStatistics> getCityStatistics() {
    return cityStatistics;
  }

  public void setCityStatistics(List<PurposeLoanCityStatistics> cityStatistics) {
    this.cityStatistics = cityStatistics;
  }

}

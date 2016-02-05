package com.eleme.domain.mart.loan;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商户借贷意向申请统计封装类
 * 
 * @author yonglin.zhu
 *
 */
public class PurposeLoanStatisticsVo implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 6722865061129454462L;

  // 省份名称
  private String proposerProvName;

  // 省份id
  private Integer proposerProvId;

  // 申请总件数
  private Integer totalAccount;

  // 申请城市金额
  private BigDecimal totalLoanAmount;

  // 城市id
  private Integer proposerCityId;

  // 城市名
  private String proposerCityName;

  public Integer getTotalAccount() {
    return totalAccount;
  }

  public void setTotalAccount(Integer totalAccount) {
    this.totalAccount = totalAccount;
  }

  public String getProposerCityName() {
    return proposerCityName;
  }

  public void setProposerCityName(String proposerCityName) {
    this.proposerCityName = proposerCityName;
  }

  public String getProposerProvName() {
    return proposerProvName;
  }

  public void setProposerProvName(String proposerProvName) {
    this.proposerProvName = proposerProvName;
  }

  public Integer getProposerProvId() {
    return proposerProvId;
  }

  public void setProposerProvId(Integer proposerProvId) {
    this.proposerProvId = proposerProvId;
  }

  public BigDecimal getTotalLoanAmount() {
    return totalLoanAmount;
  }

  public void setTotalLoanAmount(BigDecimal totalLoanAmount) {
    this.totalLoanAmount = totalLoanAmount;
  }

  public Integer getProposerCityId() {
    return proposerCityId;
  }

  public void setProposerCityId(Integer proposerCityId) {
    this.proposerCityId = proposerCityId;
  }

}

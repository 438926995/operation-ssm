package com.eleme.domain.mart.loan;

import java.math.BigDecimal;

public class PurposeLoanStatisticsExportVo {

  private Integer index;

  private String provName;

  private String cityName;

  private Integer account;

  private BigDecimal amount;

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public String getProvName() {
    return provName;
  }

  public void setProvName(String provName) {
    this.provName = provName;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public Integer getAccount() {
    return account;
  }

  public void setAccount(Integer account) {
    this.account = account;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

}

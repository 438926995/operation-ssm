package com.eleme.domain.mart.loan;

import java.math.BigDecimal;

public class PurposeLoanCityStatistics {

  private Integer cityId;

  private String cityName;

  private Integer cityAccount;

  private BigDecimal cityAmount;

  public Integer getCityId() {
    return cityId;
  }

  public void setCityId(Integer cityId) {
    this.cityId = cityId;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public Integer getCityAccount() {
    return cityAccount;
  }

  public void setCityAccount(Integer cityAccount) {
    this.cityAccount = cityAccount;
  }

  public BigDecimal getCityAmount() {
    return cityAmount;
  }

  public void setCityAmount(BigDecimal cityAmount) {
    this.cityAmount = cityAmount;
  }
}

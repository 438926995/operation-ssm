package com.eleme.domain.mart.city;

import java.io.Serializable;
import java.util.List;

/**
 * 省份对应城市 封装类
 * @author huwenwen
 *
 */
public class CityAndProvBean implements Serializable {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 5301344013452085431L;
  // 省份名
  private String cityName;
  // 省份ID
  private Integer cityID;
  // 省份对应的城市列表
  private List<MCityTree> cityList;

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public Integer getCityID() {
    return cityID;
  }

  public void setCityID(Integer cityID) {
    this.cityID = cityID;
  }

  public List<MCityTree> getCityList() {
    return cityList;
  }

  public void setCityList(List<MCityTree> cityList) {
    this.cityList = cityList;
  }
  
}

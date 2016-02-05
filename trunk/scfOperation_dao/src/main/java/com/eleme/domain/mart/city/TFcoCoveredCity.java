package com.eleme.domain.mart.city;

import java.io.Serializable;

/**
 * 金融机构 覆盖城市封装类
 * 
 * @author xudong.gu
 *
 */
public class TFcoCoveredCity implements Serializable {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 7167035026540810716L;
  
  // 主键ID
  private Integer id;
  // 金融机构ID
  private Integer foID;
  // 城市对应匹配的ID
  private Integer cityID;
  // 城市名
  private String cityName;
  // 所属省份ID
  private Integer provID;
  // 所属省份
  private String provName;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public void setCityID(Integer cityID) {
    this.cityID = cityID;
  }

  public void setProvID(Integer provID) {
    this.provID = provID;
  }

  public Integer getCityID() {
    return cityID;
  }

  public Integer getProvID() {
    return provID;
  }

  public Integer getFoID() {
    return foID;
  }

  public void setFoID(Integer foID) {
    this.foID = foID;
  }


  public String getProvName() {
    return provName;
  }

  public void setProvName(String provName) {
    this.provName = provName;
  }

}

package com.eleme.domain.mart.city;

import java.io.Serializable;

/**
 * 金融机构 覆盖城市封装类
 * 
 * @author xudong.gu
 *
 */
public class MCityTree implements Serializable {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -3143678040561212861L;

  // 主键ID
  private Integer id;
  // 城市对应匹配的ID
  private Integer cityID;
  // 城市名
  private String cityName;
  // 所属省份ID
  private Integer parentID;
  // 所属省份
  private String parentName;
  // 城市拼音
  private String cityPinyin;
  // 城市类型（1省份直辖市，2城市区，3县）
  private Integer cityType;
  // 是否有效
  private Integer isValid;

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

  public Integer getCityID() {
    return cityID;
  }

  public void setCityID(Integer cityID) {
    this.cityID = cityID;
  }

  public Integer getParentID() {
    return parentID;
  }

  public void setParentID(Integer parentID) {
    this.parentID = parentID;
  }

  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }

  public String getCityPinyin() {
    return cityPinyin;
  }

  public void setCityPinyin(String cityPinyin) {
    this.cityPinyin = cityPinyin;
  }

  public Integer getCityType() {
    return cityType;
  }

  public void setCityType(Integer cityType) {
    this.cityType = cityType;
  }

  public Integer getIsValid() {
    return isValid;
  }

  public void setIsValid(Integer isValid) {
    this.isValid = isValid;
  }
}

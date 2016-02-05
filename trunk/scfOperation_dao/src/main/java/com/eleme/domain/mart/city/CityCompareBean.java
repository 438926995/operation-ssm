package com.eleme.domain.mart.city;

/**
 * 和napos城市比较 重写equals() hashCode()
 * 
 * @author huwenwen
 *
 */
public class CityCompareBean {
  // 城市Id
  private Integer cityId;
  // 城市名
  private String cityName;
  // 城市拼音
  private String cityPinyin;
  // 原来城市Id
  private Integer originalCityId;

  @Override
  public int hashCode() {
    int result = 17;
    result = 37 * result + this.getCityId() * 37;
    result = 37 * result + this.getCityName().hashCode();
    result = 37 * result + this.getCityPinyin().hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    
    if (obj instanceof CityCompareBean) {
      CityCompareBean temp = (CityCompareBean) obj;
      if (this.getCityId().equals(temp.getCityId()) && this.getCityName().equals(temp.getCityName())
          && this.getCityPinyin().equals(temp.getCityPinyin())) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

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

  public Integer getOriginalCityId() {
    return originalCityId;
  }

  public void setOriginalCityId(Integer originalCityId) {
    this.originalCityId = originalCityId;
  }

  public String getCityPinyin() {
    return cityPinyin;
  }

  public void setCityPinyin(String cityPinyin) {
    this.cityPinyin = cityPinyin;
  }

}

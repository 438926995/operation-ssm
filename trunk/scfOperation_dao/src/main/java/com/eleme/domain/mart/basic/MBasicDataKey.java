package com.eleme.domain.mart.basic;

/**
 * 基础数据Key封装类
 * 
 * @author yonglin.zhu
 *
 */
public class MBasicDataKey {
  // 类型CD
  private String typeCd;

  // 分类ID
  private Integer typeId;

  public String getTypeCd() {
    return typeCd;
  }

  public void setTypeCd(String typeCd) {
    this.typeCd = typeCd == null ? null : typeCd.trim();
  }

  public Integer getTypeId() {
    return typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }
}

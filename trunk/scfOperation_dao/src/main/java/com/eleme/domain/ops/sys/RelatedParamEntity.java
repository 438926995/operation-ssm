package com.eleme.domain.ops.sys;

import java.util.Date;

/**
 * 系统参数实体
 * 
 * @author sunwei
 * @since 2015年12月28日
 */
public class RelatedParamEntity {

  /**
   * 主键
   */
  private Integer relatedParamId;

  /**
   * 查找标识
   */
  private String flag;

  /**
   * 对应值
   */
  private String value;
  /**
   * Flag含义说明
   */
  private String summary;
  /**
   * 创建时间
   */
  private Date createdAt;
  /**
   * 更新时间
   */
  private Date updatedAt;

  public Integer getRelatedParamId() {
    return relatedParamId;
  }

  public void setRelatedParamId(Integer relatedParamId) {
    this.relatedParamId = relatedParamId;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

}

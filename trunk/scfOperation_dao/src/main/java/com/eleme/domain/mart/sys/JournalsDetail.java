package com.eleme.domain.mart.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作记录详情
 * 
 * @author zhangqiongbiao
 */
public class JournalsDetail implements Serializable {
  private static final long serialVersionUID = -4283337636919350746L;

  private Integer journalDetailId;

  private Integer journalId;

  private String property;

  private String propKey;

  private String oldValue;

  private String value;

  private Date createdAt;

  private Date updatedAt;

  public Integer getJournalDetailId() {
    return journalDetailId;
  }

  public void setJournalDetailId(Integer journalDetailId) {
    this.journalDetailId = journalDetailId;
  }

  public Integer getJournalId() {
    return journalId;
  }

  public void setJournalId(Integer journalId) {
    this.journalId = journalId;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public String getPropKey() {
    return propKey;
  }

  public void setPropKey(String propKey) {
    this.propKey = propKey;
  }

  public String getOldValue() {
    return oldValue;
  }

  public void setOldValue(String oldValue) {
    this.oldValue = oldValue;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
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

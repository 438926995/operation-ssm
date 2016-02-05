package com.eleme.domain.mart.sys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.eleme.util.CommonUtil;

/**
 * 操作记录表
 * 
 * @author zhangqiongbiao
 */
public class Journals implements Serializable {
  private static final long serialVersionUID = 8761879627114597641L;

  private Integer journalId;

  private String journalizedId;

  private String journalizedType;

  private Integer userId;

  private String notes;

  private Integer isOps;

  private Date createdAt;

  private Date updatedAt;

  private List<JournalsDetail> journalsDetails = new ArrayList<JournalsDetail>();

  public Integer getJournalId() {
    return journalId;
  }

  public void setJournalId(Integer journalId) {
    this.journalId = journalId;
  }

  public String getJournalizedId() {
    return journalizedId;
  }

  public void setJournalizedId(String journalizedId) {
    this.journalizedId = journalizedId;
  }

  public String getJournalizedType() {
    return journalizedType;
  }

  public void setJournalizedType(String journalizedType) {
    this.journalizedType = journalizedType;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Integer getIsOps() {
    return isOps;
  }

  public void setIsOps(Integer isOps) {
    this.isOps = isOps;
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

  public List<JournalsDetail> getJournalsDetails() {
    return journalsDetails;
  }

  public void setJournalsDetails(List<JournalsDetail> journalsDetails) {
    this.journalsDetails = journalsDetails;
  }

  public Journals addAttrJournalsDetail(String propKey, Object value, Object oldValue) {
    if (!CommonUtil.equals(value, oldValue)) {
      journalsDetails.add(getAttrJournalsDetail(propKey, value, oldValue));
    }
    return this;
  }

  public JournalsDetail getAttrJournalsDetail(String propKey, Object value, Object oldValue) {
    JournalsDetail journalsDetail = new JournalsDetail();
    journalsDetail.setProperty("ATTR");
    journalsDetail.setPropKey(propKey);
    journalsDetail.setValue(String.valueOf(value));
    journalsDetail.setOldValue(String.valueOf(oldValue));
    return journalsDetail;
  }
}

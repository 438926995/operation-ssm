package com.eleme.domain.mart.product;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品审批历史记录表封装类
 * 
 * @author yonglin.zhu
 *
 */
public class TApvFinanceProductHistory implements Serializable {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 7716207711331655206L;
  // 主键ID
  private Integer ahId;
  // 单据ID
  private Integer fpId;
  // 审批状态
  private String appStatus;
  // 审批意见
  private String opinions;
  // 审批时间
  private Date appDate;
  // 实际审批人
  private Integer reallyAppUserId;

  public Integer getAhId() {
    return ahId;
  }

  public void setAhId(Integer ahId) {
    this.ahId = ahId;
  }

  public Integer getFpId() {
    return fpId;
  }

  public void setFpId(Integer fpId) {
    this.fpId = fpId;
  }

  public String getAppStatus() {
    return appStatus;
  }

  public void setAppStatus(String appStatus) {
    this.appStatus = appStatus;
  }

  public String getOpinions() {
    return opinions;
  }

  public void setOpinions(String opinions) {
    this.opinions = opinions;
  }

  public Date getAppDate() {
    return appDate;
  }

  public void setAppDate(Date appDate) {
    this.appDate = appDate;
  }

  public Integer getReallyAppUserId() {
    return reallyAppUserId;
  }

  public void setReallyAppUserId(Integer reallyAppUserId) {
    this.reallyAppUserId = reallyAppUserId;
  }

}

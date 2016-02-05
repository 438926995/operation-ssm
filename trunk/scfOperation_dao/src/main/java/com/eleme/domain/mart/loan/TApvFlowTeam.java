package com.eleme.domain.mart.loan;

import java.io.Serializable;

/**
 * 金融机构审批组封装类
 * 
 * @author yonglin.zhu
 *
 */
public class TApvFlowTeam implements Serializable {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 5443858720267968623L;

  private Integer ftId;

  private String teamName;

  private String appUserIds;

  private String appUserNames;

  public Integer getFtId() {
    return ftId;
  }

  public void setFtId(Integer ftId) {
    this.ftId = ftId;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName == null ? null : teamName.trim();
  }

  public String getAppUserIds() {
    return appUserIds;
  }

  public void setAppUserIds(String appUserIds) {
    this.appUserIds = appUserIds == null ? null : appUserIds.trim();
  }

  public String getAppUserNames() {
    return appUserNames;
  }

  public void setAppUserNames(String appUserNames) {
    this.appUserNames = appUserNames == null ? null : appUserNames.trim();
  }
}

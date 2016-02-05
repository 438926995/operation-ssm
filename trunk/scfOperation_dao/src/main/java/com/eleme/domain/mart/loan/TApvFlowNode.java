package com.eleme.domain.mart.loan;

import java.util.Date;

/**
 * 审批节点
 * 
 * @author xudong.gu
 *
 */
public class TApvFlowNode {

  // 主键
  private Integer nodeID;

  // 外键关联流程id
  private Integer procsID;

  // 节点名称
  private String nodeName;

  // 上一流程节点id
  private Integer parentID;

  // 节点类型
  private Integer nodeType;

  // 审批人id
  private String appUserIDs;

  // 审批人名
  private String appUserNames;

  // 外键关联TEAM表 id
  private Integer teamID;

  // 是否是运营后台的人员审核
  private Integer isOps;

  // 创建时间
  private Date createAt;

  // 更新时间
  private Date updateAt;

  public Integer getNodeID() {
    return nodeID;
  }

  public void setNodeID(Integer nodeID) {
    this.nodeID = nodeID;
  }

  public Integer getProcsID() {
    return procsID;
  }

  public void setProcsID(Integer procsID) {
    this.procsID = procsID;
  }

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public Integer getParentID() {
    return parentID;
  }

  public void setParentID(Integer parentID) {
    this.parentID = parentID;
  }

  public Integer getNodeType() {
    return nodeType;
  }

  public void setNodeType(Integer nodeType) {
    this.nodeType = nodeType;
  }

  public String getAppUserIDs() {
    return appUserIDs;
  }

  public void setAppUserIDs(String appUserIDs) {
    this.appUserIDs = appUserIDs;
  }

  public String getAppUserNames() {
    return appUserNames;
  }

  public void setAppUserNames(String appUserNames) {
    this.appUserNames = appUserNames;
  }

  public Integer getTeamID() {
    return teamID;
  }

  public void setTeamID(Integer teamID) {
    this.teamID = teamID;
  }

  public Integer getIsOps() {
    return isOps;
  }

  public void setIsOps(Integer isOps) {
    this.isOps = isOps;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }

  public Date getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(Date updateAt) {
    this.updateAt = updateAt;
  }

}

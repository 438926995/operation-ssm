package com.eleme.domain.ops.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限信息
 * 
 * @author zhangqiongbiao
 */
public class Authorities implements Serializable {
  private static final long serialVersionUID = 5127977650947744953L;

  // 主键
  private Integer authoritiesId;

  // 权限名称
  private String authName;

  // 权限描述
  private String authDesc;

  // 权限是否可用
  private String isEnabled;

  // 所属权限模块
  private Integer authModuleId;

  // 是否默认
  private String isDefault;

  // 系统标识
  private String prjType = "0";

  // 添加时间
  private Date createdAt;

  // 修改时间
  private Date updatedAt;
  
  // 对应资源ID
  private Integer[] resourcesIds;

  public Integer getAuthoritiesId() {
    return authoritiesId;
  }

  public void setAuthoritiesId(Integer authoritiesId) {
    this.authoritiesId = authoritiesId;
  }

  public String getAuthName() {
    return authName;
  }

  public void setAuthName(String authName) {
    this.authName = authName;
  }

  public String getAuthDesc() {
    return authDesc;
  }

  public void setAuthDesc(String authDesc) {
    this.authDesc = authDesc;
  }

  public String getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(String isEnabled) {
    this.isEnabled = isEnabled;
  }

  public Integer getAuthModuleId() {
    return authModuleId;
  }

  public void setAuthModuleId(Integer authModuleId) {
    this.authModuleId = authModuleId;
  }

  public String getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(String isDefault) {
    this.isDefault = isDefault;
  }

  public String getPrjType() {
    return prjType;
  }

  public void setPrjType(String prjType) {
    this.prjType = prjType;
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

  public Integer[] getResourcesIds() {
    return resourcesIds;
  }

  public void setResourcesIds(Integer[] resourcesIds) {
    this.resourcesIds = resourcesIds;
  }
}
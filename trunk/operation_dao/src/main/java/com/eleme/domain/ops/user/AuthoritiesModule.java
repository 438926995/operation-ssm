package com.eleme.domain.ops.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限模块信息
 * 
 * @author huwenwen
 */
public class AuthoritiesModule implements Serializable {
  private static final long serialVersionUID = 7205107790129603570L;

  // 主键
  private Integer authModuleId;

  // 权限小组名称
  private String authModuleName;

  // 系统标识
  private String prjType = "0";

  // 添加时间
  private Date createdAt;

  // 修改时间
  private Date updatedAt;

  public Integer getAuthModuleId() {
    return authModuleId;
  }

  public void setAuthModuleId(Integer authModuleId) {
    this.authModuleId = authModuleId;
  }

  public String getAuthModuleName() {
    return authModuleName;
  }

  public void setAuthModuleName(String authModuleName) {
    this.authModuleName = authModuleName;
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
}

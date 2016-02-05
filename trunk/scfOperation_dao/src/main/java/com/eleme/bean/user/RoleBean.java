package com.eleme.bean.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色添加封装Bean
 * 
 * @author huwenwen
 *
 */

public class RoleBean implements Serializable {

  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = 2974844884059313520L;

  // 角色名
  private String roleName;
  // 角色ID
  private Integer roleId;
  // 是否是默认角色
  private int isDefault;
  // 权限Id集合
  private String[] authList;
  // 权限名
  private String authName;
  // 权限Id
  private Integer authId;
  // 角色更行时间
  private Date updateAt;

  public Date getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(Date updateAt) {
    this.updateAt = updateAt;
  }

  public String getAuthName() {
    return authName;
  }

  public void setAuthName(String authName) {
    this.authName = authName;
  }

  public Integer getAuthId() {
    return authId;
  }

  public void setAuthId(Integer authId) {
    this.authId = authId;
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public int getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(int isDefault) {
    this.isDefault = isDefault;
  }

  public String[] getAuthList() {
    return authList;
  }

  public void setAuthList(String[] authList) {
    this.authList = authList;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

}

package com.eleme.domain.ops;

import java.io.Serializable;

/**
 * 用户和角色封装类
 * @author huwenwen
 *
 */
public class UserRole extends MUsers implements Serializable {
  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = 7815065671200491447L;
 
  //角色ID
  private Integer roleId;
  //角色名
  private String roleName;
  
  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }
  
}

package com.eleme.bean.security;

import java.io.Serializable;
import java.util.List;

/**
 * 角色信息封装类.
 * 
 * @author penglau
 *
 */
public class RolesBean implements Serializable {

  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = -5423642193609062875L;
  /**
   * 角色编号
   */
  private Long roleId;
  /**
   * 角色名称
   */
  private String roleName;
  /**
   * 权限集合
   */
  private List<AuthoritiesBean> authList;

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public List<AuthoritiesBean> getAuthList() {
    return authList;
  }

  public void setAuthList(List<AuthoritiesBean> authList) {
    this.authList = authList;
  }

}

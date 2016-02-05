package com.eleme.bean.user;

import java.io.Serializable;

import javax.validation.GroupSequence;

import org.hibernate.validator.constraints.NotBlank;

import com.eleme.annotation.validator.SingleUserName;
import com.eleme.validatorgroup.First;
import com.eleme.validatorgroup.Four;
import com.eleme.validatorgroup.Second;
import com.eleme.validatorgroup.Third;

/**
 * 角色添加封装Bean
 * 
 * @author huwenwen
 *
 */
@GroupSequence({First.class, Second.class, Third.class, Four.class, RoleAddBean.class})
public class RoleAddBean implements Serializable {

  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = 2974844884059313520L;

  // 角色名
  @NotBlank(message = "{field.required}", groups = {First.class})
  @SingleUserName(message = "{user.name.single}", groups = {Second.class})
  private String roleName;
  // 角色ID
  private Integer roleId;
  // 是否是默认角色
  private int isDefault;
  // 权限Id集合
  private String[] authList;
  // 权限Id str集合
  private String authStr;

  public String getAuthStr() {
    return authStr;
  }

  public void setAuthStr(String authStr) {
    this.authStr = authStr;
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

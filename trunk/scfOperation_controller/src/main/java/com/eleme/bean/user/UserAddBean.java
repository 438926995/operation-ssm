package com.eleme.bean.user;

import java.io.Serializable;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.eleme.annotation.validator.SingleUserName;
import com.eleme.validatorgroup.First;
import com.eleme.validatorgroup.Four;
import com.eleme.validatorgroup.Second;
import com.eleme.validatorgroup.Third;

/**
 * 用户添加界面的封装bean.
 * 
 * @author penglau
 *
 */
@GroupSequence({First.class, Second.class, Third.class, Four.class, UserAddBean.class})
public class UserAddBean implements Serializable {

  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = 5784026865355661352L;

  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 5, max = 30, message = "{field.range}", groups = {Second.class})
  @Email(message = "{field.email}", groups = {Third.class})
  @SingleUserName(message = "{user.name.single}", groups = {Four.class})
  private String userName;

  @NotNull(message = "{field.required}", groups = {First.class})
  @Length(min = 6, max = 20, message = "{field.range}", groups = {Second.class})
  private String pswd;

  private String isAdmin;
  
  //角色Id
  private Integer roleId;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPswd() {
    return pswd;
  }

  public void setPswd(String pswd) {
    this.pswd = pswd;
  }

  public String getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(String isAdmin) {
    this.isAdmin = isAdmin;
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }
  

}

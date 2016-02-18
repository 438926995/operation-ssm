package com.eleme.bean.user;

import java.io.Serializable;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.eleme.validatorgroup.First;
import com.eleme.validatorgroup.Four;
import com.eleme.validatorgroup.Second;
import com.eleme.validatorgroup.Third;

/**
 * 用户修改密码的封装bean.
 * 
 * @author huwenwen
 *
 */
@GroupSequence({First.class, Second.class, Third.class, Four.class, ChangePwdBean.class})
public class ChangePwdBean implements Serializable {

  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = 2660967590606654502L;

  @NotNull(message = "{field.required}", groups = {First.class})
  @Length(min = 6, max = 20, message = "{field.range}", groups = {Second.class})
  private String pswd;

  @NotNull(message = "{field.required}", groups = {First.class})
  @Length(min = 6, max = 20, message = "{field.range}", groups = {Second.class})
  private String newPswd;

  @NotNull(message = "{field.required}", groups = {First.class})
  @Length(min = 6, max = 20, message = "{field.range}", groups = {Second.class})
  private String newPswdConfirm;

  public String getPswd() {
    return pswd;
  }

  public void setPswd(String pswd) {
    this.pswd = pswd;
  }

  public String getNewPswd() {
    return newPswd;
  }

  public void setNewPswd(String newPswd) {
    this.newPswd = newPswd;
  }

  public String getNewPswdConfirm() {
    return newPswdConfirm;
  }

  public void setNewPswdConfirm(String newPswdConfirm) {
    this.newPswdConfirm = newPswdConfirm;
  }



}

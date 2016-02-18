package com.eleme.bean.user;

import java.io.Serializable;

import javax.validation.GroupSequence;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.eleme.validatorgroup.First;
import com.eleme.validatorgroup.Four;
import com.eleme.validatorgroup.Second;
import com.eleme.validatorgroup.Third;

/**
 * 权限模块 添加界面的封装bean.
 * 
 * @author huwenwen
 */
@GroupSequence({First.class, Second.class, Third.class, Four.class, AuthoritiesModuleAddBean.class})
public class AuthoritiesModuleAddBean implements Serializable {
  private static final long serialVersionUID = -6789462559529842497L;

  // 权限模块ID
  private Integer authModuleId;

  // 权限模块名称
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 50, message = "{field.range}", groups = {Second.class})
  private String authModuleName;

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
}
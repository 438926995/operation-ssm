package com.eleme.domain.ops.user;
/**
 * 权限集合的Bean
 * @author huwenwen
 *
 */
public class AuthoritieBean {
  //权限Id
  private Integer authId;
  //权限名称
  private String authName;
  // 权限分组
  private Integer authModuleId;
  //是否选择
  private boolean isChecked;
  
  public Integer getAuthModuleId() {
    return authModuleId;
  }
  public void setAuthModuleId(Integer authModuleId) {
    this.authModuleId = authModuleId;
  }
  public boolean isChecked() {
    return isChecked;
  }
  public void setChecked(boolean isChecked) {
    this.isChecked = isChecked;
  }
  public Integer getAuthId() {
    return authId;
  }
  public void setAuthId(Integer authId) {
    this.authId = authId;
  }
  public String getAuthName() {
    return authName;
  }
  public void setAuthName(String authName) {
    this.authName = authName;
  }
  
  
}

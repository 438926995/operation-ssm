package com.eleme.bean.user;

/**
 * 用户验证权限名唯一的Bean。
 * 
 * @author huwenwen
 *
 */
public class AuthNameBean {

  private String authName;
  private Integer authId;

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

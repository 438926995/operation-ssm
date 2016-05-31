package com.eleme.bean.security;

import java.io.Serializable;

/**
 * 用户登录信息封装类.
 * 
 * @author huwenwen
 *
 */
public class LoginBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1322228975682375533L;
  /**
   * 用户编号
   */
  private Long userId;
  /**
   * 登录用户名
   */
  private String userName;
  /**
   * 加密密码
   */
  private String hashedUserPass;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getHashedUserPass() {
    return hashedUserPass;
  }

  public void setHashedUserPass(String hashedUserPass) {
    this.hashedUserPass = hashedUserPass;
  }

}

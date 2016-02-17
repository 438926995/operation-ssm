package com.eleme.bean;

import java.io.Serializable;

/**
 * session信息封装类.
 * 
 * @author penglau
 *
 */
public class SessionBean implements Serializable {

  /**
   * serial VersionUID.
   */
  private static final long serialVersionUID = 1L;
  /**
   * 用户ID
   */
  private Long userId;
  /**
   * 员工姓名
   */
  private String userName;

  /**
   * 用户标识，1 商家；2 金融机构
   */
  private Integer userFlag;

  /**
   * 是否为管理员
   */
  private Boolean admin;

  /**
   * 默认构造方法
   */
  public SessionBean() {
    super();
  }

  /**
   * 构造方法，管理员为false
   * 
   * @param userId 用户id
   * @param userName 用户名称。
   */
  public SessionBean(Long userId, String userName) {
    super();
    this.userId = userId;
    this.userName = userName;
    this.admin = false;
  }

  /**
   * 构造方法.
   * 
   * @param userId 用户id
   * @param userName 用户名称。
   * @param isAdmin 是否为管理员
   */
  public SessionBean(Long userId, String userName, Boolean admin) {
    super();
    this.userId = userId;
    this.userName = userName;
    this.admin = admin;
  }

  /**
   * 构造方法.
   * 
   * @param userId 用户id
   * @param userName 用户名称。
   * @param isAdmin 是否为管理员
   */
  public SessionBean(Long userId, String userName, Boolean admin, Integer userFlag) {
    super();
    this.userId = userId;
    this.userName = userName;
    this.admin = admin;
    this.userFlag = userFlag;
  }

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

  public Boolean isAdmin() {
    return admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }


  public Integer getUserFlag() {
    return userFlag;
  }

  public void setUserFlag(Integer userFlag) {
    this.userFlag = userFlag;
  }

}

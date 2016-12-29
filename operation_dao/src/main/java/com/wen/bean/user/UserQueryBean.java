package com.wen.bean.user;

/**
 * 用户列表查询条件封装类。
 * 
 * @author huwenwen
 *
 */
public class UserQueryBean {

  // 用户姓名
  private String userName;
  // 用户状态
  private String userStatus;
  // 是否为管理员
  private String isAdmin;
  // 用户添加时间范围
  private String userCreateDateRange;

  public String getUserCreateDateRange() {
    return userCreateDateRange;
  }

  public void setUserCreateDateRange(String userCreateDateRange) {
    this.userCreateDateRange = userCreateDateRange;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserStatus() {
    return userStatus;
  }

  public void setUserStatus(String userStatus) {
    this.userStatus = userStatus;
  }

  public String getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(String isAdmin) {
    this.isAdmin = isAdmin;
  }

}

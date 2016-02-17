package com.eleme.bean.user;

import java.io.Serializable;
import java.sql.Date;

/**
 * M_USERS 的实体映射类.
 * 
 * @author penglau
 *
 */
public class MUsers implements Serializable {

  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = 3964486474255310838L;
  // 用户姓名
  private String user_name;
  // 密码
  private String pswd;
  // 添加时间
  private Date create_date;
  // 用户状态
  private String user_status;
  // 是否为管理员
  private String is_admin;

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getPswd() {
    return pswd;
  }

  public void setPswd(String pswd) {
    this.pswd = pswd;
  }

  public Date getCreate_date() {
    return create_date;
  }

  public void setCreate_date(Date create_date) {
    this.create_date = create_date;
  }

  public String getUser_status() {
    return user_status;
  }

  public void setUser_status(String user_status) {
    this.user_status = user_status;
  }

  public String getIs_admin() {
    return is_admin;
  }

  public void setIs_admin(String is_admin) {
    this.is_admin = is_admin;
  }

}

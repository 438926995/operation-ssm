package com.eleme.domain.mart.statistic;

import java.io.Serializable;
import java.sql.Date;

/**
 * 用户反馈 信息封装
 * 
 * @author xudong.gu
 *
 */
public class UserFeedback implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 144678164959037811L;

  // id
  private Integer id;

  // 用户名
  private String userName;

  // 反馈内容
  private String userFeedback;

  // 所属类别
  private Integer userCate;

  // 用户ip
  private String userIp;

  // 创建时间
  private Date createdAt;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserFeedback() {
    return userFeedback;
  }

  public void setUserFeedback(String userFeedback) {
    this.userFeedback = userFeedback;
  }

  public Integer getUserCate() {
    return userCate;
  }

  public void setUserCate(Integer userCate) {
    this.userCate = userCate;
  }

  public String getUserIp() {
    return userIp;
  }

  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }


}

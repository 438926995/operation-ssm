package com.eleme.bean.statistic;

import java.util.Date;

/**
 * 用户反馈查询条件封装
 * 
 * @author xudong.gu
 *
 */
public class UserFeedbackQuerybean {

  // 用户类型
  private Integer userCate;
  // 开始时间
  private Date submitTimeFrom;
  // 截至时间
  private Date submitTimeEnd;

  public Integer getUserCate() {
    return userCate;
  }

  public void setUserCate(Integer userCate) {
    this.userCate = userCate;
  }

  public Date getSubmitTimeFrom() {
    return submitTimeFrom;
  }

  public void setSubmitTimeFrom(Date submitTimeFrom) {
    this.submitTimeFrom = submitTimeFrom;
  }

  public Date getSubmitTimeEnd() {
    return submitTimeEnd;
  }

  public void setSubmitTimeEnd(Date submitTimeEnd) {
    this.submitTimeEnd = submitTimeEnd;
  }



}

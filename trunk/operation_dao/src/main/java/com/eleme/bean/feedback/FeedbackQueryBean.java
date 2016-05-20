package com.eleme.bean.feedback;

import com.eleme.util.pager.BaseQueryBean;

import java.util.Date;

/**
 * Created by huwenwen on 16/5/20.
 */
public class FeedbackQueryBean extends BaseQueryBean {
  private String userName;
  private Date submitTimeFrom;
  private Date submitTimeEnd;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

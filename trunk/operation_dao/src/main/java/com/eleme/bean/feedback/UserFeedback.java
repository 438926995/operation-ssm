package com.eleme.bean.feedback;

import java.util.Date;

/**
 * Created by huwenwen on 16/5/20.
 */
public class UserFeedback {
  private Integer id;
  private Integer userId;
  private String userName;
  private Date feedbackTime;
  private String feedbackContent;
  private Date replyTime;
  private String replyContent;
  private Integer replyUserId;
  private String replyUserName;
  private Integer isReply;

  public Integer getReplyUserId() {
    return replyUserId;
  }

  public void setReplyUserId(Integer replyUserId) {
    this.replyUserId = replyUserId;
  }

  public String getReplyUserName() {
    return replyUserName;
  }

  public void setReplyUserName(String replyUserName) {
    this.replyUserName = replyUserName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Date getFeedbackTime() {
    return feedbackTime;
  }

  public void setFeedbackTime(Date feedbackTime) {
    this.feedbackTime = feedbackTime;
  }

  public String getFeedbackContent() {
    return feedbackContent;
  }

  public void setFeedbackContent(String feedbackContent) {
    this.feedbackContent = feedbackContent;
  }

  public Date getReplyTime() {
    return replyTime;
  }

  public void setReplyTime(Date replyTime) {
    this.replyTime = replyTime;
  }

  public String getReplyContent() {
    return replyContent;
  }

  public void setReplyContent(String replyContent) {
    this.replyContent = replyContent;
  }

  public Integer getIsReply() {
    return isReply;
  }

  public void setIsReply(Integer isReply) {
    this.isReply = isReply;
  }
}

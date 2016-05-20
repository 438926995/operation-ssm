package com.eleme.bean.feedback;

import java.util.Date;

/**
 * Created by huwenwen on 16/5/20.
 */
public class ReplyFeedback {

  private Integer id;
  private String replyContent;
  private Date replyTime;
  private Integer replyUserId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getReplyContent() {
    return replyContent;
  }

  public void setReplyContent(String replyContent) {
    this.replyContent = replyContent;
  }

  public Date getReplyTime() {
    return replyTime;
  }

  public void setReplyTime(Date replyTime) {
    this.replyTime = replyTime;
  }

  public Integer getReplyUserId() {
    return replyUserId;
  }

  public void setReplyUserId(Integer replyUserId) {
    this.replyUserId = replyUserId;
  }
}

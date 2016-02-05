package com.eleme.domain.mart.mail;

import java.util.Date;

/**
 * 邮件发送队列 实体 指向
 * 
 * @author sunwei
 * @since 2015年12月25日
 */
public class MailSendQueueEntity {


  /**
   * 邮件id
   */
  private Integer mailId;
  /**
   * 邮件主题
   */
  private String mailTopic;
  /**
   * 邮件接收人
   */
  private String mailReceiver;
  /**
   * 邮件发送状态 0，未发送，1：正在发送，2：发送成功，3：发送失败
   */
  private Integer mailSendStatus;
  /**
   * 邮件关联类型
   */
  private Integer mailContainType;
  /**
   * 邮件关联id
   */
  private Integer mailContainId;
  /**
   * 邮件发送时间
   */
  private Date mailSendedAt;
  /**
   * 邮件内容
   */
  private String mailContent;
  /**
   * 邮件附件
   */
  private String mailAttachments;
  /**
   * 创建时间
   */
  private Date createdAt;
  /**
   * 更新时间
   */
  private Date updatedAt;
  /**
   * 邮件错误描述
   */
  private String mailSendDesc;

  public Integer getMailId() {
    return mailId;
  }

  public void setMailId(Integer mailId) {
    this.mailId = mailId;
  }

  public String getMailTopic() {
    return mailTopic;
  }

  public void setMailTopic(String mailTopic) {
    this.mailTopic = mailTopic;
  }

  public String getMailReceiver() {
    return mailReceiver;
  }

  public void setMailReceiver(String mailReceiver) {
    this.mailReceiver = mailReceiver;
  }

  public Integer getMailSendStatus() {
    return mailSendStatus;
  }

  public void setMailSendStatus(Integer mailSendStatus) {
    this.mailSendStatus = mailSendStatus;
  }

  public Integer getMailContainType() {
    return mailContainType;
  }

  public void setMailContainType(Integer mailContainType) {
    this.mailContainType = mailContainType;
  }

  public Integer getMailContainId() {
    return mailContainId;
  }

  public void setMailContainId(Integer mailContainId) {
    this.mailContainId = mailContainId;
  }

  public Date getMailSendedAt() {
    return mailSendedAt;
  }

  public void setMailSendedAt(Date mailSendedAt) {
    this.mailSendedAt = mailSendedAt;
  }

  public String getMailContent() {
    return mailContent;
  }

  public void setMailContent(String mailContent) {
    this.mailContent = mailContent;
  }

  public String getMailAttachments() {
    return mailAttachments;
  }

  public void setMailAttachments(String mailAttachments) {
    this.mailAttachments = mailAttachments;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getMailSendDesc() {
    return mailSendDesc;
  }

  public void setMailSendDesc(String mailSendDesc) {
    this.mailSendDesc = mailSendDesc;
  }

}

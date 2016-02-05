package com.eleme.domain.mart.mail;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 商户信息邮件 数据对象
 * 
 * @author sunwei
 * @since 2015年12月25日
 */
public class MailSellerInfoVO extends MailSellerInfoEntity {

  /**
   * 金融机构下属账号名称
   */
  @JsonProperty(value = "user_name")
  private String userName;

  /**
   * 金融机构下属账号绑定邮箱
   */
  @JsonProperty(value = "user_email")
  private String userEmail;

  /**
   * 商户id
   */
  @JsonProperty("seller_id")
  private Integer sellerId;

  /**
   * 商户名称
   */
  @JsonProperty(value = "seller_name")
  private String sellerName;

  /**
   * 所属机构名称
   */
  @JsonProperty(value = "fo_name")
  private String foName;

  /**
   * napos oid
   */
  @JsonProperty(value = "napos_oid")
  private String naposOid;

  /**
   * 发送队列中的id
   */
  @JsonProperty(value = "mail_id")
  private Integer mailId;

  /**
   * 邮件发送状态 null：未加入发送队列中，0，未发送，1：正在发送，2：发送成功，3：发送失败
   */
  @JsonProperty(value = "mail_send_status")
  private Integer mailSendStatus;



  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getSellerId() {
    return sellerId;
  }

  public void setSellerId(Integer sellerId) {
    this.sellerId = sellerId;
  }

  public String getSellerName() {
    return sellerName;
  }

  public void setSellerName(String sellerName) {
    this.sellerName = sellerName;
  }

  public Integer getMailSendStatus() {
    return mailSendStatus;
  }

  public void setMailSendStatus(Integer mailSendStatus) {
    this.mailSendStatus = mailSendStatus;
  }

  public String getFoName() {
    return foName;
  }

  public void setFoName(String foName) {
    this.foName = foName;
  }

  public String getNaposOid() {
    return naposOid;
  }

  public void setNaposOid(String naposOid) {
    this.naposOid = naposOid;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public Integer getMailId() {
    return mailId;
  }

  public void setMailId(Integer mailId) {
    this.mailId = mailId;
  }



}

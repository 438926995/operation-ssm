package com.eleme.domain.mart.mail;

import java.util.Date;

import com.eleme.constants.MailType;
import com.eleme.domain.mart.PageQueryBean;

/**
 * 商户信息邮件列表查询参数对象
 * 
 * @author sunwei
 * @since 2015年12月27日
 */
public class MailSellerInfoListQueryBean extends PageQueryBean {

  /**
   * 金融机构id
   */
  private Integer foId;
  /**
   * 发送状态
   */
  private Integer sendStatus;
  /**
   * 查询开始时间
   */
  private Date queryStartDate;
  /**
   * 查询结束时间
   */
  private Date queryEndDate;

  /**
   * 邮件类型
   */
  private Integer mailType = MailType.SELLER_INFO.getId();

  public Integer getFoId() {
    return foId;
  }

  public void setFoId(Integer foId) {
    this.foId = foId;
  }

  public Integer getSendStatus() {
    return sendStatus;
  }

  public void setSendStatus(Integer sendStatus) {
    this.sendStatus = sendStatus;
  }

  public Date getQueryStartDate() {
    return queryStartDate;
  }

  public void setQueryStartDate(Date queryStartDate) {
    this.queryStartDate = queryStartDate;
  }

  public Date getQueryEndDate() {
    return queryEndDate;
  }

  public void setQueryEndDate(Date queryEndDate) {
    this.queryEndDate = queryEndDate;
  }

  public Integer getMailType() {
    return mailType;
  }

  public void setMailType(Integer mailType) {
    this.mailType = mailType;
  }

}

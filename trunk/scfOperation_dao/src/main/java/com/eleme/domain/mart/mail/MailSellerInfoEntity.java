package com.eleme.domain.mart.mail;

import java.util.Date;

import com.eleme.util.jackson.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * 商户信息邮件实体 映射表T_URR_SELLER_INFO_MAIL
 * 
 * @author sunwei
 * @since 2015年12月25日
 */
public class MailSellerInfoEntity {

  /**
   * 商户信息邮件 主键id
   */
  @JsonProperty("sim_id")
  private Integer simId;


  /**
   * 贷款Id
   */
  @JsonProperty("sl_id")
  private Integer slId;

  /**
   * 商户信息Excel路径
   */
  @JsonProperty("mail_seller_info_excel_path")
  private String mailSellerInfoExcelPath;

  /**
   * 创建时间
   */
  @JsonProperty("created_at")
  private Date createdAt;
  /**
   * 更新时间
   */
  @JsonProperty("updated_at")
  private Date updatedAt;
  /**
   * 融资机构id
   */
  @JsonProperty("user_id")
  private Integer userId;

  public Integer getSimId() {
    return simId;
  }

  public void setSimId(Integer simId) {
    this.simId = simId;
  }

  public String getMailSellerInfoExcelPath() {
    return mailSellerInfoExcelPath;
  }

  public void setMailSellerInfoExcelPath(String mailSellerInfoExcelPath) {
    this.mailSellerInfoExcelPath = mailSellerInfoExcelPath;
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

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getSlId() {
    return slId;
  }

  public void setSlId(Integer slId) {
    this.slId = slId;
  }

}

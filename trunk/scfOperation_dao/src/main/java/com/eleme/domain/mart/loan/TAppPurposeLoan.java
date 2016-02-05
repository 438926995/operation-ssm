package com.eleme.domain.mart.loan;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 商户借贷意向申请封装类
 * 
 * @author yonglin.zhu
 *
 */
public class TAppPurposeLoan {
  private Integer plId;

  private Integer loanAmount;

  // 商户申请借贷金额（万元）
  private Integer loanAmountTenThousand;

  private String proposerName;

  private Integer proposerSex;
  
  private String proposerSexStr;

  private String proposerPhone;
  
  // 手机
  private String proposerPhoneStr;
  
//身份证号
 private String proposerSidStr;
  
  private String proposerEmail;

  private String proposerCityName;

  private Integer sellerId;

  private String sellerName;

  private Date submitTime;
  
  // 提交日期 字符串
  private String submitTimeStr;

  private Integer docStatus;

  private String appStatus;

  private Integer procsId;

  private Integer stopBySeller;

  private Integer proposerAge;

  private String proposerSid;

  private String docNo;

  private Date createdAt;

  private Date updatedAt;

  public Integer getPlId() {
    return plId;
  }

  public void setPlId(Integer plId) {
    this.plId = plId;
  }

  public Integer getLoanAmount() {
    return loanAmount;
  }

  public void setLoanAmount(Integer loanAmount) {
    this.loanAmount = loanAmount;
  }

  public String getProposerName() {
    return proposerName;
  }

  public void setProposerName(String proposerName) {
    this.proposerName = proposerName == null ? null : proposerName.trim();
  }

  public Integer getProposerSex() {
    return proposerSex;
  }

  public void setProposerSex(Integer proposerSex) {
    this.proposerSex = proposerSex;
  }

  public String getProposerPhone() {
    return proposerPhone;
  }

  public void setProposerPhone(String proposerPhone) {
    this.proposerPhone = proposerPhone == null ? null : proposerPhone.trim();
  }

  public String getProposerEmail() {
    return proposerEmail;
  }

  public void setProposerEmail(String proposerEmail) {
    this.proposerEmail = proposerEmail == null ? null : proposerEmail.trim();
  }

  public String getProposerCityName() {
    return proposerCityName;
  }

  public void setProposerCityName(String proposerCityName) {
    this.proposerCityName = proposerCityName == null ? null : proposerCityName.trim();
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
    this.sellerName = sellerName == null ? null : sellerName.trim();
  }

  public Date getSubmitTime() {
    return submitTime;
  }

  public void setSubmitTime(Date submitTime) {
    this.submitTime = submitTime;
  }

  public Integer getDocStatus() {
    return docStatus;
  }

  public void setDocStatus(Integer docStatus) {
    this.docStatus = docStatus;
  }

  public String getAppStatus() {
    return appStatus;
  }

  public void setAppStatus(String appStatus) {
    this.appStatus = appStatus == null ? null : appStatus.trim();
  }

  public Integer getProcsId() {
    return procsId;
  }

  public void setProcsId(Integer procsId) {
    this.procsId = procsId;
  }

  public Integer getStopBySeller() {
    return stopBySeller;
  }

  public void setStopBySeller(Integer stopBySeller) {
    this.stopBySeller = stopBySeller;
  }

  public Integer getProposerAge() {
    return proposerAge;
  }

  public void setProposerAge(Integer proposerAge) {
    this.proposerAge = proposerAge;
  }

  public String getProposerSid() {
    return proposerSid;
  }

  public void setProposerSid(String proposerSid) {
    this.proposerSid = proposerSid == null ? null : proposerSid.trim();
  }

  public String getDocNo() {
    return docNo;
  }

  public void setDocNo(String docNo) {
    this.docNo = docNo == null ? null : docNo.trim();
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

  public Integer getLoanAmountTenThousand() {
    return Integer
        .parseInt((new BigDecimal(this.loanAmount).divide(new BigDecimal(1000000))).toString());
  }

  public void setLoanAmountTenThousand(Integer loanAmountTenThousand) {
    this.loanAmountTenThousand = loanAmountTenThousand;
  }
  
  public String getProposerSexStr() {
    if (this.proposerSex == 1) {
      return "男";
    } else {
      return "女";
    }
  }

  public String getSubmitTimeStr() {
    if (submitTime != null) {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return dateFormat.format(this.submitTime);
    }
    return "";
  }
  
  public String getProposerPhoneStr() {
    return com.eleme.util.StringUtils.phoneSecretTo(this.proposerPhone);
  }

  public void setProposerPhoneStr(String proposerPhoneStr) {
    this.proposerPhoneStr = proposerPhoneStr;
  }
  
  public String getProposerSidStr() {
    return com.eleme.util.StringUtils.idSecretTo(this.proposerSid);
  }

  public void setProposerSidStr(String proposerSidStr) {
    this.proposerSidStr = proposerSidStr;
  }
}

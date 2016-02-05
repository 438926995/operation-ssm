package com.eleme.domain.mart.loan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.eleme.constants.LoanStatus;
import com.eleme.domain.mart.product.TFcoRequestRuleVo;

/**
 * 商户借贷申请封装类
 * 
 * @author yonglin.zhu
 *
 */
public class TAppSellerLoan implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 987634630690001684L;

  // 主键ID
  private Integer slId;
  // 金融产品ID
  private Integer fpId;
  // 商户申请借贷金额
  private Integer loanAmount;
  // 商户申请借贷金额（万元）
  private Integer loanAmountTenThousand;
  // 申请人姓名
  private String proposerName;
  // 性别
  private Integer proposerSex;
  // 性别
  private String proposerSexStr;
  // 手机
  private String proposerPhone;
  // 手机
  private String proposerPhoneStr;
  // 邮箱
  private String proposerEmail;
  // 城市名称
  private String proposerCityName;
  // 商户ID
  private Integer sellerId;
  // 商户名称
  private String sellerName;
  // 提交日期
  private Date submitTime;
  // 提交日期 字符串
  private String submitTimeStr;
  // 单据状态
  private Integer docStatus;
  // 审批状态
  private String appStatus;
  // 审批状态
  private String appStatusStr;
  // 关联Activiti流程实例
  private Integer procsId;
  // 是否申请人中断
  private Integer stopBySeller;
  // 年龄
  private Integer proposerAge;
  // 身份证号
  private String proposerSid;
  // 身份证号
  private String proposerSidStr;
  // 贷款申请单号
  private String docNo;
  // 是否可见 1：可见，0:不可见
  private Integer isVisable;
  // 未开通城市贷款申请ID
  private Integer plId;


  // 规则信息
  private List<TFcoRequestRuleVo> ruleList;

  public Integer getSlId() {
    return slId;
  }

  public void setSlId(Integer slId) {
    this.slId = slId;
  }

  public Integer getFpId() {
    return fpId;
  }

  public void setFpId(Integer fpId) {
    this.fpId = fpId;
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

  public String getAppStatusStr() {
    return LoanStatus.getName(this.appStatus);
  }

  public void setAppStatusStr(String appStatusStr) {
    this.appStatusStr = appStatusStr;
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

  public List<TFcoRequestRuleVo> getRuleList() {
    return ruleList;
  }

  public void setRuleList(List<TFcoRequestRuleVo> ruleList) {
    this.ruleList = ruleList;
  }

  public Integer getIsVisable() {
    return isVisable;
  }

  public void setIsVisable(Integer isVisable) {
    this.isVisable = isVisable;
  }

  public Integer getLoanAmountTenThousand() {
    if(this.loanAmount == null){
      return 0;
    }else{
      return Integer
          .parseInt((new BigDecimal(this.loanAmount).divide(new BigDecimal(1000000))).toString());
    }
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

  public Integer getPlId() {
    return plId;
  }

  public void setPlId(Integer plId) {
    this.plId = plId;
  }



}

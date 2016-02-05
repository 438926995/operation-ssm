package com.eleme.domain.mart.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 放贷金融机构发布的产品封装类
 * 
 * @author yonglin.zhu
 *
 */
public class MFinanceProduct implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1397035651204641215L;

  // 产品ID
  private Integer fpId;

  // 产品名称
  private String fpName;

  // 地区
  private String fpArea;

  // 是否有抵押
  private Integer isMortgage;

  // 抵押内容
  private String mortgageContent;

  // 是否有担保
  private Integer isWarrant;

  // 担保内容
  private String warrantContent;

  // 到账时间
  private Integer receivingDate;

  // 最低可贷款
  private Integer minLoanAmount;

  // 最高可贷款
  private Integer maxLoanAmount;

  // 支持还款期限
  private String payLimit;

  // 费率单位
  private Integer raitUnit;

  // 最低费率系数
  private BigDecimal minRaitRatio;

  // 最高费率系数
  private BigDecimal maxRaitRatio;

  // 添加时间
  private Date createTime;

  // 状态
  private Integer productStatus;

  // 金融机构id
  private Integer foId;

  // 开始时间
  private Date startDate;

  // 结束时间
  private Date endDate;

  // 分成方式
  private Integer profitType;

  // 其他费率
  private BigDecimal otherRait;

  // 证件要求
  private String credentialsRequire;

  // 其他说明
  private String otherDesc;

  // 添加时间
  private Date createAt;

  // 是否融资平台添加
  private Integer isFinance;

  // 上传文件表的主键
  private Integer ufId;

  // 产品banner
  private MultipartFile fpLogo;

  public MultipartFile getFpLogo() {
    return fpLogo;
  }

  public void setFpLogo(MultipartFile fpLogo) {
    this.fpLogo = fpLogo;
  }

  public Integer getUfId() {
    return ufId;
  }

  public void setUfId(Integer ufId) {
    this.ufId = ufId;
  }

  public Integer getFpId() {
    return fpId;
  }

  public void setFpId(Integer fpId) {
    this.fpId = fpId;
  }

  public String getFpName() {
    return fpName;
  }

  public void setFpName(String fpName) {
    this.fpName = fpName == null ? null : fpName.trim();
  }

  public String getFpArea() {
    return fpArea;
  }

  public void setFpArea(String fpArea) {
    this.fpArea = fpArea == null ? null : fpArea.trim();
  }

  public Integer getIsMortgage() {
    return isMortgage;
  }

  public void setIsMortgage(Integer isMortgage) {
    this.isMortgage = isMortgage;
  }

  public String getMortgageContent() {
    return mortgageContent;
  }

  public void setMortgageContent(String mortgageContent) {
    this.mortgageContent = mortgageContent == null ? null : mortgageContent.trim();
  }

  public Integer getIsWarrant() {
    return isWarrant;
  }

  public void setIsWarrant(Integer isWarrant) {
    this.isWarrant = isWarrant;
  }

  public String getWarrantContent() {
    return warrantContent;
  }

  public void setWarrantContent(String warrantContent) {
    this.warrantContent = warrantContent == null ? null : warrantContent.trim();
  }

  public Integer getReceivingDate() {
    return receivingDate;
  }

  public void setReceivingDate(Integer receivingDate) {
    this.receivingDate = receivingDate;
  }

  public Integer getMinLoanAmount() {
    return minLoanAmount;
  }

  public void setMinLoanAmount(Integer minLoanAmount) {
    this.minLoanAmount = minLoanAmount;
  }

  public Integer getMaxLoanAmount() {
    return maxLoanAmount;
  }

  public void setMaxLoanAmount(Integer maxLoanAmount) {
    this.maxLoanAmount = maxLoanAmount;
  }

  public String getPayLimit() {
    return payLimit;
  }

  public void setPayLimit(String payLimit) {
    this.payLimit = payLimit == null ? null : payLimit.trim();
  }

  public Integer getRaitUnit() {
    return raitUnit;
  }

  public void setRaitUnit(Integer raitUnit) {
    this.raitUnit = raitUnit;
  }

  public BigDecimal getMinRaitRatio() {
    return minRaitRatio;
  }

  public void setMinRaitRatio(BigDecimal minRaitRatio) {
    this.minRaitRatio = minRaitRatio;
  }

  public BigDecimal getMaxRaitRatio() {
    return maxRaitRatio;
  }

  public void setMaxRaitRatio(BigDecimal maxRaitRatio) {
    this.maxRaitRatio = maxRaitRatio;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Integer getProductStatus() {
    return productStatus;
  }

  public void setProductStatus(Integer productStatus) {
    this.productStatus = productStatus;
  }

  public Integer getFoId() {
    return foId;
  }

  public void setFoId(Integer foId) {
    this.foId = foId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Integer getProfitType() {
    return profitType;
  }

  public void setProfitType(Integer profitType) {
    this.profitType = profitType;
  }

  public BigDecimal getOtherRait() {
    return otherRait;
  }

  public void setOtherRait(BigDecimal otherRait) {
    this.otherRait = otherRait;
  }

  public String getCredentialsRequire() {
    return credentialsRequire;
  }

  public void setCredentialsRequire(String credentialsRequire) {
    this.credentialsRequire = credentialsRequire == null ? null : credentialsRequire.trim();
  }

  public String getOtherDesc() {
    return otherDesc;
  }

  public void setOtherDesc(String otherDesc) {
    this.otherDesc = otherDesc == null ? null : otherDesc.trim();
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }

  public Integer getIsFinance() {
    return isFinance;
  }

  public void setIsFinance(Integer isFinance) {
    this.isFinance = isFinance;
  }
}

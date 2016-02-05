package com.eleme.bean.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.GroupSequence;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.eleme.annotation.validator.ImageFormat;
import com.eleme.annotation.validator.ImageSize;
import com.eleme.annotation.validator.Single;
import com.eleme.constants.StringConstants;
import com.eleme.validatorgroup.First;
import com.eleme.validatorgroup.Four;
import com.eleme.validatorgroup.Second;
import com.eleme.validatorgroup.Third;

/**
 * 融资产品添加界面的封装bean.
 * 
 * @author yonglin.zhu
 *
 */
@GroupSequence({First.class, Second.class, Third.class, Four.class, ProductAddBean.class})
public class ProductAddBean implements Serializable {

  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = -7465664170335798469L;

  // 产品名称
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 100, message = "{field.range}", groups = {Second.class})
  @Single(field = "FP_NAME", tableName = "M_FINANCE_PRODUCT", statusField = "PRODUCT_STATUS",
      status = "0,1,2", message = "{field.name}", groups = {Third.class})
  @Pattern(regexp = StringConstants.Special_Chr, message = "{field.specialchar}",
      groups = {Four.class})
  private String fpName;

  // 最低可贷款
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Digits(integer = 12, fraction = 2, message = "{field.number}", groups = {Second.class})
  private String minLoanAmount;

  // 最高可贷款
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Digits(integer = 12, fraction = 2, message = "{field.number}", groups = {Second.class})
  private String maxLoanAmount;

  // 还款期限
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String payLimit;

  // 费率单位
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String raitUnit;

  // 上线开始时间
  @NotNull(message = "{field.required}", groups = {First.class})
  private Date startDate;

  // 上线结束时间
  @NotNull(message = "{field.required}", groups = {First.class})
  private Date endDate;

  // 投放地区
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String fpArea;

  // 所属机构
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String foId;

  // 最低费率系数
  @NotNull(message = "{field.required}", groups = {First.class})
  @DecimalMin(value = "0.00", message = "{field.number}", groups = {Second.class})
  @DecimalMax(value = "100.00", message = "{field.number}", groups = {Third.class})
  private BigDecimal minRaitRatio;

  // 最高费率系数
  @NotNull(message = "{field.required}", groups = {First.class})
  @DecimalMin(value = "0.00", message = "{field.number}", groups = {Second.class})
  @DecimalMax(value = "100.00", message = "{field.number}", groups = {Third.class})
  private BigDecimal maxRaitRatio;

  // 其他费率
  // @Pattern(regexp = "^(\\d{0,6}.\\d{0,2})?$", message = "{field.number}",
  // groups = {First.class})
  @NotNull(message = "{field.required}", groups = {First.class})
  @DecimalMin(value = "0.00", message = "{field.number}", groups = {Second.class})
  @DecimalMax(value = "100.00", message = "{field.number}", groups = {Third.class})
  private BigDecimal otherRait;

  // 证件要求
  @Length(min = 0, max = 500, message = "{field.range}", groups = {First.class})
  private String credentialsRequire;

  // 其他说明
  @Length(min = 0, max = 500, message = "{field.range}", groups = {First.class})
  private String otherDesc;

  // 产品banner
  @ImageSize(message = "{image.size}",ignoreEmpty = false, maxSize = 5, groups = {First.class})
  @ImageFormat(message = "{image.format}",ignoreEmpty = false, format = "jpg/jpeg/png", groups = {Second.class})
  private MultipartFile fpLogo;

  private Integer productStatus;

  private Integer fpId;

  public String getFpName() {
    return fpName;
  }

  public void setFpName(String fpName) {
    this.fpName = fpName;
  }

  public String getMinLoanAmount() {
    return minLoanAmount;
  }

  public void setMinLoanAmount(String minLoanAmount) {
    this.minLoanAmount = minLoanAmount;
  }

  public String getMaxLoanAmount() {
    return maxLoanAmount;
  }

  public void setMaxLoanAmount(String maxLoanAmount) {
    this.maxLoanAmount = maxLoanAmount;
  }

  public String getPayLimit() {
    return payLimit;
  }

  public void setPayLimit(String payLimit) {
    this.payLimit = payLimit;
  }

  public String getRaitUnit() {
    return raitUnit;
  }

  public void setRaitUnit(String raitUnit) {
    this.raitUnit = raitUnit;
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

  public String getFpArea() {
    return fpArea;
  }

  public void setFpArea(String fpArea) {
    this.fpArea = fpArea;
  }

  public String getFoId() {
    return foId;
  }

  public void setFoId(String foId) {
    this.foId = foId;
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
    this.credentialsRequire = credentialsRequire;
  }

  public String getOtherDesc() {
    return otherDesc;
  }

  public void setOtherDesc(String otherDesc) {
    this.otherDesc = otherDesc;
  }

  public Integer getProductStatus() {
    return productStatus;
  }

  public void setProductStatus(Integer productStatus) {
    this.productStatus = productStatus;
  }

  public Integer getFpId() {
    return fpId;
  }

  public void setFpId(Integer fpId) {
    this.fpId = fpId;
  }

  public MultipartFile getFpLogo() {
    return fpLogo;
  }

  public void setFpLogo(MultipartFile fpLogo) {
    this.fpLogo = fpLogo;
  }

}

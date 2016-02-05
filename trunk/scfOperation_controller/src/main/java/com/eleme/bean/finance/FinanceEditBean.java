package com.eleme.bean.finance;

import java.io.Serializable;

import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.eleme.annotation.validator.ImageFormat;
import com.eleme.annotation.validator.ImageSize;
import com.eleme.constants.StringConstants;
import com.eleme.validatorgroup.First;
import com.eleme.validatorgroup.Four;
import com.eleme.validatorgroup.Second;
import com.eleme.validatorgroup.Third;

/**
 * 金融机构添加界面的封装bean.
 * 
 * @author yonglin.zhu
 *
 */
@GroupSequence({First.class, Second.class, Third.class, Four.class, FinanceEditBean.class})
public class FinanceEditBean implements Serializable {

  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = 8089790095983039694L;


  // 机构名称
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 100, message = "{field.range}", groups = {Second.class})
  @Pattern(regexp = StringConstants.Special_Chr, message = "{field.specialchar}",
      groups = {Third.class})
  private String foName;
  // 机构地址
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 200, message = "{field.range}", groups = {Second.class})
  @Pattern(regexp = StringConstants.Special_Chr, message = "{field.specialchar}",
      groups = {Third.class})
  private String foAddr;
  // 公司网址
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 0, max = 100, message = "{field.range}", groups = {Second.class})
  private String foUrl;
  // 公司电话
  @Pattern(regexp = "^([0-9]{3,4}-)?[0-9]{7,8}$", message = "{field.phone}", groups = {First.class})
  // @Pattern(regexp = "^(1[3-8]{1}\\d{9})?$", message = "{field.phone}", groups = {Second.class})
  private String foPhone;
  // 公司简介
  @Length(min = 0, max = 2000, message = "{field.range}", groups = {First.class})
  @Pattern(regexp = StringConstants.Special_Chr, message = "{field.specialchar}",
      groups = {Second.class})
  private String foDesc;
  // 金融机构log
  @ImageSize(message = "{image.size}",ignoreEmpty = true, maxSize = 5, groups = {First.class})
  @ImageFormat(message = "{image.format}",ignoreEmpty = true, format = "jpg/jpeg/png", groups = {Second.class})
  private MultipartFile foLogo;
  // 机构覆盖城市
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String foCoverCityNames;
  // 状态
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String foStatus;

  private String foId;

  public String getFoName() {
    return foName;
  }

  public void setFoName(String foName) {
    this.foName = foName;
  }

  public String getFoAddr() {
    return foAddr;
  }

  public void setFoAddr(String foAddr) {
    this.foAddr = foAddr;
  }

  public String getFoUrl() {
    return foUrl;
  }

  public void setFoUrl(String foUrl) {
    this.foUrl = foUrl;
  }

  public String getFoPhone() {
    return foPhone;
  }

  public void setFoPhone(String foPhone) {
    this.foPhone = foPhone;
  }

  public String getFoStatus() {
    return foStatus;
  }

  public void setFoStatus(String foStatus) {
    this.foStatus = foStatus;
  }

  public String getFoId() {
    return foId;
  }

  public void setFoId(String foId) {
    this.foId = foId;
  }

  public String getFoCoverCityNames() {
    return foCoverCityNames;
  }

  public void setFoCoverCityNames(String foCoverCityNames) {
    this.foCoverCityNames = foCoverCityNames;
  }

  public String getFoDesc() {
    return foDesc;
  }

  public void setFoDesc(String foDesc) {
    this.foDesc = foDesc;
  }

  public MultipartFile getFoLogo() {
    return foLogo;
  }

  public void setFoLogo(MultipartFile foLogo) {
    this.foLogo = foLogo;
  }

}

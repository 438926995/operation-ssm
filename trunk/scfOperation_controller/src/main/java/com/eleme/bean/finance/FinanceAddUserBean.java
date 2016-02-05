package com.eleme.bean.finance;

import java.io.Serializable;

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.eleme.annotation.validator.Single;
import com.eleme.constants.StringConstants;
import com.eleme.validatorgroup.First;
import com.eleme.validatorgroup.Four;
import com.eleme.validatorgroup.Second;
import com.eleme.validatorgroup.Third;

/**
 * 用户添加界面的封装bean.
 * 
 * @author yonglin.zhu
 *
 */
@GroupSequence({First.class, Second.class, Third.class, Four.class, FinanceAddUserBean.class})
public class FinanceAddUserBean implements Serializable {

  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = -6941992582149717417L;

  // 账号名
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 50, message = "{field.range}", groups = {Second.class})
  @Single(field = "USER_NAME", tableName = "M_MART_USERS", message = "{field.name}",
      groups = {Third.class})
  @Pattern(regexp = StringConstants.Special_Chr, message = "{field.specialchar}",
      groups = {Four.class})
  private String userName;
  // 密码
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 100, message = "{field.range}", groups = {Second.class})
  private String userPswd;
  // 类型
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String userFlag;
  // 昵称
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 5, max = 100, message = "{field.range}", groups = {Second.class})
  private String nickName;
  // 联系人手机号码
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Pattern(regexp = "^(1[3-8]{1}\\d{9})?$", message = "{field.phone}", groups = {Second.class})
  private String mobilePhone;
  // 邮箱
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 200, message = "{field.range}", groups = {Second.class})
  @Email(message = "{field.email}", groups = {Third.class})
  private String userEmail;
  // 状态
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String userStatus;
  // 性别
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String userSex;
  // 年龄
  @Max(value = 999999999)
  private Integer userAge;
  // 身份证号
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(max = 20, message = "{field.range}", groups = {Second.class})
  @Pattern(regexp = "^((\\d{18}$)|(\\d{15}$)|(\\d{17}([0-9]|X)$))", message = "{field.sid}",
      groups = {Third.class})
  private String userSid;
  // 住址
  @Length(max = 200, message = "{field.range}", groups = {First.class})
  private String userAddr;

  // 金融机构ID
  private String foId;
  // 账号ID
  private String userId;
  // 机构名称
  private String foName;
  // 是否为审核人
  private String isApply;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPswd() {
    return userPswd;
  }

  public void setUserPswd(String userPswd) {
    this.userPswd = userPswd;
  }

  public String getUserFlag() {
    return userFlag;
  }

  public void setUserFlag(String userFlag) {
    this.userFlag = userFlag;
  }

  public String getFoId() {
    return foId;
  }

  public void setFoId(String foId) {
    this.foId = foId;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getUserStatus() {
    return userStatus;
  }

  public void setUserStatus(String userStatus) {
    this.userStatus = userStatus;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getFoName() {
    return foName;
  }

  public void setFoName(String foName) {
    this.foName = foName;
  }

  public String getUserSex() {
    return userSex;
  }

  public void setUserSex(String userSex) {
    this.userSex = userSex;
  }

  public Integer getUserAge() {
    return userAge;
  }

  public void setUserAge(Integer userAge) {
    this.userAge = userAge;
  }

  public String getUserSid() {
    return userSid;
  }

  public void setUserSid(String userSid) {
    this.userSid = userSid;
  }

  public String getUserAddr() {
    return userAddr;
  }

  public void setUserAddr(String userAddr) {
    this.userAddr = userAddr;
  }

  public String getIsApply() {
    return isApply;
  }

  public void setIsApply(String isApply) {
    this.isApply = isApply;
  }

}

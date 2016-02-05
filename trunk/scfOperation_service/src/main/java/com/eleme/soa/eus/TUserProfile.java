package com.eleme.soa.eus;

import me.ele.contract.annotation.Index;

public class TUserProfile {
  private @Index(1) int id;
  private @Index(2) int current_address_id;
  private @Index(3) double balance;
  private @Index(4) int point;
  private @Index(5) String email;
  private @Index(6) short is_email_valid;
  private @Index(7) String mobile;
  private @Index(8) short is_mobile_valid;
  private @Index(9) String validate_string;
  private @Index(10) String avatar;
  private @Index(11) Integer current_invoice_id;
  private @Index(12) int user_id;
  private @Index(13) int payment_quota;
  private @Index(14) String payment_password;
  private @Index(15) String name;
  private @Index(16) int certification_type;
  private @Index(17) String referal_code;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCurrent_address_id() {
    return current_address_id;
  }

  public void setCurrent_address_id(int current_address_id) {
    this.current_address_id = current_address_id;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    this.point = point;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public short getIs_email_valid() {
    return is_email_valid;
  }

  public void setIs_email_valid(short is_email_valid) {
    this.is_email_valid = is_email_valid;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public short getIs_mobile_valid() {
    return is_mobile_valid;
  }

  public void setIs_mobile_valid(short is_mobile_valid) {
    this.is_mobile_valid = is_mobile_valid;
  }

  public String getValidate_string() {
    return validate_string;
  }

  public void setValidate_string(String validate_string) {
    this.validate_string = validate_string;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Integer getCurrent_invoice_id() {
    return current_invoice_id;
  }

  public void setCurrent_invoice_id(Integer current_invoice_id) {
    this.current_invoice_id = current_invoice_id;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public int getPayment_quota() {
    return payment_quota;
  }

  public void setPayment_quota(int payment_quota) {
    this.payment_quota = payment_quota;
  }

  public String getPayment_password() {
    return payment_password;
  }

  public void setPayment_password(String payment_password) {
    this.payment_password = payment_password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCertification_type() {
    return certification_type;
  }

  public void setCertification_type(int certification_type) {
    this.certification_type = certification_type;
  }

  public String getReferal_code() {
    return referal_code;
  }

  public void setReferal_code(String referal_code) {
    this.referal_code = referal_code;
  }
}

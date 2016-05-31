package com.eleme.constants;

/**
 * 邮件类型
 * 
 * @author huwenwen
 * @since 2015年12月25日
 */
public enum MailType {


  SELLER_INFO(0, "商户信息邮件");

  int id;
  String desc;

  MailType(int id, String desc) {
    this.id = id;
    this.desc = desc;
  }

  public int getId() {
    return id;
  }

  public String getDesc() {
    return desc;
  }

}

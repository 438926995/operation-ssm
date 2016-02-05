package com.eleme.bean.seller;

import java.io.Serializable;

/**
 * 根据napos组提供的api查询数据后，返回给前台用于展示的封装类
 * 
 * @author penglau
 *
 */
public class NaposRestaurant implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1628315993200969530L;

  // 餐厅naposid
  private String oid;
  // 餐厅名称
  private String name;
  // 手机号码（一个）
  private String mobile;
  // 联系电话（多个）
  private String phone;
  // 地址
  private String address_text;
  // 城市id
  private String city_id;

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress_text() {
    return address_text;
  }

  public void setAddress_text(String address_text) {
    this.address_text = address_text;
  }

  public String getCity_id() {
    return city_id;
  }

  public void setCity_id(String city_id) {
    this.city_id = city_id;
  }

}

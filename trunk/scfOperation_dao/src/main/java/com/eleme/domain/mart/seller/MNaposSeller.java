package com.eleme.domain.mart.seller;

import java.io.Serializable;


/**
 * MNaposSeller表插入数据使用
 * 
 */
public class MNaposSeller implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4932275393704296640L;

  private int id;

  private String address_text;

  private double avg_comment_time;

  private int busy_level;

  private int certification_status;

  private int certification_type;

  private int city_id;

  private int come_from;

  private String created_at;

  private int deliver_spent;

  private String description;

  private int dine_enabled;

  private String flavors;

  private int invoice;

  private double invoice_min_amount;

  private int is_bookable;

  private int is_time_ensure;

  private int is_valid;

  private String keeper_name;

  private String keeper_phone;

  private String method_of_payment;

  private int min_deliver_amount;

  private String mobile;

  private String name;

  private int num_rating_1;

  private int num_rating_2;

  private int num_rating_3;

  private int num_rating_4;

  private int num_rating_5;

  private int oid;

  private int online_payment;

  private String phone;

  private int recent_food_popularity;

  private int recent_order_num;

  private int region_id;

  private int seller_id;

  private int type_code;

  private int user_id;

  public MNaposSeller() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAddress_text() {
    return address_text;
  }

  public void setAddress_text(String address_text) {
    this.address_text = address_text;
  }

  public double getAvg_comment_time() {
    return avg_comment_time;
  }

  public void setAvg_comment_time(double avg_comment_time) {
    this.avg_comment_time = avg_comment_time;
  }

  public int getBusy_level() {
    return busy_level;
  }

  public void setBusy_level(int busy_level) {
    this.busy_level = busy_level;
  }

  public int getCertification_status() {
    return certification_status;
  }

  public void setCertification_status(int certification_status) {
    this.certification_status = certification_status;
  }

  public int getCertification_type() {
    return certification_type;
  }

  public void setCertification_type(int certification_type) {
    this.certification_type = certification_type;
  }

  public int getCity_id() {
    return city_id;
  }

  public void setCity_id(int city_id) {
    this.city_id = city_id;
  }

  public int getCome_from() {
    return come_from;
  }

  public void setCome_from(int come_from) {
    this.come_from = come_from;
  }

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public int getDeliver_spent() {
    return deliver_spent;
  }

  public void setDeliver_spent(int deliver_spent) {
    this.deliver_spent = deliver_spent;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getDine_enabled() {
    return dine_enabled;
  }

  public void setDine_enabled(int dine_enabled) {
    this.dine_enabled = dine_enabled;
  }

  public String getFlavors() {
    return flavors;
  }

  public void setFlavors(String flavors) {
    this.flavors = flavors;
  }

  public int getInvoice() {
    return invoice;
  }

  public void setInvoice(int invoice) {
    this.invoice = invoice;
  }

  public double getInvoice_min_amount() {
    return invoice_min_amount;
  }

  public void setInvoice_min_amount(double invoice_min_amount) {
    this.invoice_min_amount = invoice_min_amount;
  }

  public int getIs_bookable() {
    return is_bookable;
  }

  public void setIs_bookable(int is_bookable) {
    this.is_bookable = is_bookable;
  }

  public int getIs_time_ensure() {
    return is_time_ensure;
  }

  public void setIs_time_ensure(int is_time_ensure) {
    this.is_time_ensure = is_time_ensure;
  }

  public int getIs_valid() {
    return is_valid;
  }

  public void setIs_valid(int is_valid) {
    this.is_valid = is_valid;
  }

  public String getKeeper_name() {
    return keeper_name;
  }

  public void setKeeper_name(String keeper_name) {
    this.keeper_name = keeper_name;
  }

  public String getKeeper_phone() {
    return keeper_phone;
  }

  public void setKeeper_phone(String keeper_phone) {
    this.keeper_phone = keeper_phone;
  }

  public String getMethod_of_payment() {
    return method_of_payment;
  }

  public void setMethod_of_payment(String method_of_payment) {
    this.method_of_payment = method_of_payment;
  }

  public int getMin_deliver_amount() {
    return min_deliver_amount;
  }

  public void setMin_deliver_amount(int min_deliver_amount) {
    this.min_deliver_amount = min_deliver_amount;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getNum_rating_1() {
    return num_rating_1;
  }

  public void setNum_rating_1(int num_rating_1) {
    this.num_rating_1 = num_rating_1;
  }

  public int getNum_rating_2() {
    return num_rating_2;
  }

  public void setNum_rating_2(int num_rating_2) {
    this.num_rating_2 = num_rating_2;
  }

  public int getNum_rating_3() {
    return num_rating_3;
  }

  public void setNum_rating_3(int num_rating_3) {
    this.num_rating_3 = num_rating_3;
  }

  public int getNum_rating_4() {
    return num_rating_4;
  }

  public void setNum_rating_4(int num_rating_4) {
    this.num_rating_4 = num_rating_4;
  }

  public int getNum_rating_5() {
    return num_rating_5;
  }

  public void setNum_rating_5(int num_rating_5) {
    this.num_rating_5 = num_rating_5;
  }

  public int getOid() {
    return oid;
  }

  public void setOid(int oid) {
    this.oid = oid;
  }

  public int getOnline_payment() {
    return online_payment;
  }

  public void setOnline_payment(int online_payment) {
    this.online_payment = online_payment;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public int getRecent_food_popularity() {
    return recent_food_popularity;
  }

  public void setRecent_food_popularity(int recent_food_popularity) {
    this.recent_food_popularity = recent_food_popularity;
  }

  public int getRecent_order_num() {
    return recent_order_num;
  }

  public void setRecent_order_num(int recent_order_num) {
    this.recent_order_num = recent_order_num;
  }

  public int getRegion_id() {
    return region_id;
  }

  public void setRegion_id(int region_id) {
    this.region_id = region_id;
  }

  public int getSeller_id() {
    return seller_id;
  }

  public void setSeller_id(int seller_id) {
    this.seller_id = seller_id;
  }

  public int getType_code() {
    return type_code;
  }

  public void setType_code(int type_code) {
    this.type_code = type_code;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

}

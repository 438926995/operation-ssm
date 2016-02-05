package com.eleme.soa.ers;

import java.util.List;

import me.ele.contract.annotation.Index;

public class TRestaurantQuery {
  private @Index(1) String address;
  private @Index(2) String mobile;
  private @Index(3) String name;
  private @Index(4) String phone;
  private @Index(5) Short busy_level;
  private @Index(6) Short order_mode;
  private @Index(7) Boolean allow_ol_payment;
  private @Index(8) Boolean has_deliver_area;
  private @Index(9) Boolean has_food_img;
  private @Index(10) Boolean has_image_hash;
  private @Index(11) Boolean is_coupon;
  private @Index(12) Boolean is_recommend;
  private @Index(13) Boolean is_premium;
  private @Index(14) Boolean is_valid;
  private @Index(15) List<Integer> city_ids;
  private @Index(16) List<Integer> region_ids;
  private @Index(17) List<Integer> managed_city_ids;
  private @Index(18) List<Integer> managed_region_ids;
  private @Index(19) Integer offset;
  private @Index(20) Integer limit;
  private @Index(21) String keyword;
  private @Index(22) Integer come_from;
  private @Index(23) Integer service_category;
  private @Index(24) Boolean desc;
  private @Index(25) Short certification_type;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Short getBusy_level() {
    return busy_level;
  }

  public void setBusy_level(Short busy_level) {
    this.busy_level = busy_level;
  }

  public Short getOrder_mode() {
    return order_mode;
  }

  public void setOrder_mode(Short order_mode) {
    this.order_mode = order_mode;
  }

  public Boolean getAllow_ol_payment() {
    return allow_ol_payment;
  }

  public void setAllow_ol_payment(Boolean allow_ol_payment) {
    this.allow_ol_payment = allow_ol_payment;
  }

  public Boolean getHas_deliver_area() {
    return has_deliver_area;
  }

  public void setHas_deliver_area(Boolean has_deliver_area) {
    this.has_deliver_area = has_deliver_area;
  }

  public Boolean getHas_food_img() {
    return has_food_img;
  }

  public void setHas_food_img(Boolean has_food_img) {
    this.has_food_img = has_food_img;
  }

  public Boolean getHas_image_hash() {
    return has_image_hash;
  }

  public void setHas_image_hash(Boolean has_image_hash) {
    this.has_image_hash = has_image_hash;
  }

  public Boolean getIs_coupon() {
    return is_coupon;
  }

  public void setIs_coupon(Boolean is_coupon) {
    this.is_coupon = is_coupon;
  }

  public Boolean getIs_recommend() {
    return is_recommend;
  }

  public void setIs_recommend(Boolean is_recommend) {
    this.is_recommend = is_recommend;
  }

  public Boolean getIs_premium() {
    return is_premium;
  }

  public void setIs_premium(Boolean is_premium) {
    this.is_premium = is_premium;
  }

  public Boolean getIs_valid() {
    return is_valid;
  }

  public void setIs_valid(Boolean is_valid) {
    this.is_valid = is_valid;
  }

  public List<Integer> getCity_ids() {
    return city_ids;
  }

  public void setCity_ids(List<Integer> city_ids) {
    this.city_ids = city_ids;
  }

  public List<Integer> getRegion_ids() {
    return region_ids;
  }

  public void setRegion_ids(List<Integer> region_ids) {
    this.region_ids = region_ids;
  }

  public List<Integer> getManaged_city_ids() {
    return managed_city_ids;
  }

  public void setManaged_city_ids(List<Integer> managed_city_ids) {
    this.managed_city_ids = managed_city_ids;
  }

  public List<Integer> getManaged_region_ids() {
    return managed_region_ids;
  }

  public void setManaged_region_ids(List<Integer> managed_region_ids) {
    this.managed_region_ids = managed_region_ids;
  }

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public Integer getCome_from() {
    return come_from;
  }

  public void setCome_from(Integer come_from) {
    this.come_from = come_from;
  }

  public Integer getService_category() {
    return service_category;
  }

  public void setService_category(Integer service_category) {
    this.service_category = service_category;
  }

  public Boolean getDesc() {
    return desc;
  }

  public void setDesc(Boolean desc) {
    this.desc = desc;
  }

  public Short getCertification_type() {
    return certification_type;
  }

  public void setCertification_type(Short certification_type) {
    this.certification_type = certification_type;
  }
}

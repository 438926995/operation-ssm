package com.eleme.soa.ers;

import java.util.List;

import me.ele.contract.annotation.Index;

public class TRestaurant {
  private @Index(1) int id;
  private @Index(2) short is_valid;
  private @Index(3) short agent_fee;
  private @Index(4) short busy_level;
  private @Index(5) String name;
  private @Index(6) Double latitude;
  private @Index(7) Double longitude;
  private @Index(8) String description;
  private @Index(9) String address_text;
  private @Index(10) Short deliver_amount;
  private @Index(11) Short total_status;
  private @Index(12) String wireless_printer_esn;
  private @Index(13) boolean support_online;
  private @Index(14) String open_time_bitmap;
  private @Index(15) String book_time_bitmap;
  private @Index(16) String deliver_description;
  private @Index(17) short num_rating_1;
  private @Index(18) short num_rating_2;
  private @Index(19) short num_rating_3;
  private @Index(20) short num_rating_4;
  private @Index(21) short num_rating_5;
  private @Index(22) long created_at;
  private @Index(23) String image_url;
  private @Index(24) String image_hash;
  private @Index(25) String phone;
  private @Index(26) String mobile;
  private @Index(27) short order_mode;
  private @Index(28) String promotion_info;
  private @Index(29) short one_delivery;
  private @Index(30) String pinyin_name;
  private @Index(31) String name_for_url;
  private @Index(32) short min_deliver_amount;
  private @Index(33) String close_description;
  private @Index(34) short is_saas;
  private @Index(35) String header_image_url;
  private @Index(36) short waimai_num_print_copies;
  private @Index(37) short tangchi_num_print_copies;
  private @Index(38) String printer_version;
  private @Index(39) Short sn;
  private @Index(40) short deliver_radius;
  private @Index(41) Double min_lng;
  private @Index(42) Double max_lng;
  private @Index(43) Double min_lat;
  private @Index(44) Double max_lat;
  private @Index(45) short is_bookable;
  private @Index(46) String flavors;
  private @Index(47) short dine_enabled;
  private @Index(48) Short deliver_spent;
  private @Index(49) short is_time_ensure;
  private @Index(50) String time_ensure_description;
  private @Index(51) Long time_ensure_at;
  private @Index(52) Short time_ensure_spent;
  private @Index(53) String time_ensure_discount;
  private @Index(54) short city_id;
  private @Index(55) short is_phone_hidden;
  private @Index(56) short coupon_enabled;
  private @Index(57) Long coupon_start_at;
  private @Index(58) Long coupon_end_at;
  private @Index(59) Integer coupon_number;
  private @Index(60) Short coupon_discount;
  private @Index(61) String paper_width;
  private @Index(62) short auto_print_tangchi;
  private @Index(63) double speed_coef1;
  private @Index(64) double speed_coef2;
  private @Index(65) double speed_coef3;
  private @Index(66) double avg_comment_time;
  private @Index(68) String activities;
  private @Index(69) short has_food_img;
  private @Index(70) short online_payment;
  private @Index(71) short invoice;
  private @Index(72) double invoice_min_amount;
  private @Index(73) String attribute;
  private @Index(74) String deliver_area;
  private @Index(75) String open_date;
  private @Index(76) double original_order_quantity;
  private @Index(77) String keeper_name;
  private @Index(78) String keeper_phone;
  private @Index(79) String remark;
  private @Index(80) String corporation;
  private @Index(81) int geohash_ranking_weight;
  private @Index(82) List<List<String>> serving_time;
  private @Index(83) List<Short> num_ratings;
  private @Index(84) int recent_food_popularity;
  private @Index(85) short is_premium;
  private @Index(86) List<String> deliver_times;
  private @Index(87) String napos_client_settings;
  private @Index(88) String keeper_identity_card;
  private @Index(89) Boolean support_coupon;
  private @Index(90) String time_ensure_full_description;
  private @Index(91) int come_from;
  private @Index(92) int no_agent_fee_total;
  private @Index(93) int service_category;
  private @Index(94) int dock_corp_id;
  private @Index(95) String deliver_geojson;
  private @Index(96) double service_rating;
  private @Index(97) int recent_order_num;
  private @Index(98) int has_activity;
  private @Index(99) Short certification_type;
  private @Index(100) int oid;
  private @Index(101) Short method_of_payment;
  private @Index(102) short is_exclusive;
  private @Index(103) short type;
  private @Index(104) String open_time;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public short getIs_valid() {
    return is_valid;
  }

  public void setIs_valid(short is_valid) {
    this.is_valid = is_valid;
  }

  public short getAgent_fee() {
    return agent_fee;
  }

  public void setAgent_fee(short agent_fee) {
    this.agent_fee = agent_fee;
  }

  public short getBusy_level() {
    return busy_level;
  }

  public void setBusy_level(short busy_level) {
    this.busy_level = busy_level;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAddress_text() {
    return address_text;
  }

  public void setAddress_text(String address_text) {
    this.address_text = address_text;
  }

  public Short getDeliver_amount() {
    return deliver_amount;
  }

  public void setDeliver_amount(Short deliver_amount) {
    this.deliver_amount = deliver_amount;
  }

  public Short getTotal_status() {
    return total_status;
  }

  public void setTotal_status(Short total_status) {
    this.total_status = total_status;
  }

  public String getWireless_printer_esn() {
    return wireless_printer_esn;
  }

  public void setWireless_printer_esn(String wireless_printer_esn) {
    this.wireless_printer_esn = wireless_printer_esn;
  }

  public boolean getSupport_online() {
    return support_online;
  }

  public void setSupport_online(boolean support_online) {
    this.support_online = support_online;
  }

  public String getOpen_time_bitmap() {
    return open_time_bitmap;
  }

  public void setOpen_time_bitmap(String open_time_bitmap) {
    this.open_time_bitmap = open_time_bitmap;
  }

  public String getBook_time_bitmap() {
    return book_time_bitmap;
  }

  public void setBook_time_bitmap(String book_time_bitmap) {
    this.book_time_bitmap = book_time_bitmap;
  }

  public String getDeliver_description() {
    return deliver_description;
  }

  public void setDeliver_description(String deliver_description) {
    this.deliver_description = deliver_description;
  }

  public short getNum_rating_1() {
    return num_rating_1;
  }

  public void setNum_rating_1(short num_rating_1) {
    this.num_rating_1 = num_rating_1;
  }

  public short getNum_rating_2() {
    return num_rating_2;
  }

  public void setNum_rating_2(short num_rating_2) {
    this.num_rating_2 = num_rating_2;
  }

  public short getNum_rating_3() {
    return num_rating_3;
  }

  public void setNum_rating_3(short num_rating_3) {
    this.num_rating_3 = num_rating_3;
  }

  public short getNum_rating_4() {
    return num_rating_4;
  }

  public void setNum_rating_4(short num_rating_4) {
    this.num_rating_4 = num_rating_4;
  }

  public short getNum_rating_5() {
    return num_rating_5;
  }

  public void setNum_rating_5(short num_rating_5) {
    this.num_rating_5 = num_rating_5;
  }

  public long getCreated_at() {
    return created_at;
  }

  public void setCreated_at(long created_at) {
    this.created_at = created_at;
  }

  public String getImage_url() {
    return image_url;
  }

  public void setImage_url(String image_url) {
    this.image_url = image_url;
  }

  public String getImage_hash() {
    return image_hash;
  }

  public void setImage_hash(String image_hash) {
    this.image_hash = image_hash;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public short getOrder_mode() {
    return order_mode;
  }

  public void setOrder_mode(short order_mode) {
    this.order_mode = order_mode;
  }

  public String getPromotion_info() {
    return promotion_info;
  }

  public void setPromotion_info(String promotion_info) {
    this.promotion_info = promotion_info;
  }

  public short getOne_delivery() {
    return one_delivery;
  }

  public void setOne_delivery(short one_delivery) {
    this.one_delivery = one_delivery;
  }

  public String getPinyin_name() {
    return pinyin_name;
  }

  public void setPinyin_name(String pinyin_name) {
    this.pinyin_name = pinyin_name;
  }

  public String getName_for_url() {
    return name_for_url;
  }

  public void setName_for_url(String name_for_url) {
    this.name_for_url = name_for_url;
  }

  public short getMin_deliver_amount() {
    return min_deliver_amount;
  }

  public void setMin_deliver_amount(short min_deliver_amount) {
    this.min_deliver_amount = min_deliver_amount;
  }

  public String getClose_description() {
    return close_description;
  }

  public void setClose_description(String close_description) {
    this.close_description = close_description;
  }

  public short getIs_saas() {
    return is_saas;
  }

  public void setIs_saas(short is_saas) {
    this.is_saas = is_saas;
  }

  public String getHeader_image_url() {
    return header_image_url;
  }

  public void setHeader_image_url(String header_image_url) {
    this.header_image_url = header_image_url;
  }

  public short getWaimai_num_print_copies() {
    return waimai_num_print_copies;
  }

  public void setWaimai_num_print_copies(short waimai_num_print_copies) {
    this.waimai_num_print_copies = waimai_num_print_copies;
  }

  public short getTangchi_num_print_copies() {
    return tangchi_num_print_copies;
  }

  public void setTangchi_num_print_copies(short tangchi_num_print_copies) {
    this.tangchi_num_print_copies = tangchi_num_print_copies;
  }

  public String getPrinter_version() {
    return printer_version;
  }

  public void setPrinter_version(String printer_version) {
    this.printer_version = printer_version;
  }

  public Short getSn() {
    return sn;
  }

  public void setSn(Short sn) {
    this.sn = sn;
  }

  public short getDeliver_radius() {
    return deliver_radius;
  }

  public void setDeliver_radius(short deliver_radius) {
    this.deliver_radius = deliver_radius;
  }

  public Double getMin_lng() {
    return min_lng;
  }

  public void setMin_lng(Double min_lng) {
    this.min_lng = min_lng;
  }

  public Double getMax_lng() {
    return max_lng;
  }

  public void setMax_lng(Double max_lng) {
    this.max_lng = max_lng;
  }

  public Double getMin_lat() {
    return min_lat;
  }

  public void setMin_lat(Double min_lat) {
    this.min_lat = min_lat;
  }

  public Double getMax_lat() {
    return max_lat;
  }

  public void setMax_lat(Double max_lat) {
    this.max_lat = max_lat;
  }

  public short getIs_bookable() {
    return is_bookable;
  }

  public void setIs_bookable(short is_bookable) {
    this.is_bookable = is_bookable;
  }

  public String getFlavors() {
    return flavors;
  }

  public void setFlavors(String flavors) {
    this.flavors = flavors;
  }

  public short getDine_enabled() {
    return dine_enabled;
  }

  public void setDine_enabled(short dine_enabled) {
    this.dine_enabled = dine_enabled;
  }

  public Short getDeliver_spent() {
    return deliver_spent;
  }

  public void setDeliver_spent(Short deliver_spent) {
    this.deliver_spent = deliver_spent;
  }

  public short getIs_time_ensure() {
    return is_time_ensure;
  }

  public void setIs_time_ensure(short is_time_ensure) {
    this.is_time_ensure = is_time_ensure;
  }

  public String getTime_ensure_description() {
    return time_ensure_description;
  }

  public void setTime_ensure_description(String time_ensure_description) {
    this.time_ensure_description = time_ensure_description;
  }

  public Long getTime_ensure_at() {
    return time_ensure_at;
  }

  public void setTime_ensure_at(Long time_ensure_at) {
    this.time_ensure_at = time_ensure_at;
  }

  public Short getTime_ensure_spent() {
    return time_ensure_spent;
  }

  public void setTime_ensure_spent(Short time_ensure_spent) {
    this.time_ensure_spent = time_ensure_spent;
  }

  public String getTime_ensure_discount() {
    return time_ensure_discount;
  }

  public void setTime_ensure_discount(String time_ensure_discount) {
    this.time_ensure_discount = time_ensure_discount;
  }

  public short getCity_id() {
    return city_id;
  }

  public void setCity_id(short city_id) {
    this.city_id = city_id;
  }

  public short getIs_phone_hidden() {
    return is_phone_hidden;
  }

  public void setIs_phone_hidden(short is_phone_hidden) {
    this.is_phone_hidden = is_phone_hidden;
  }

  public short getCoupon_enabled() {
    return coupon_enabled;
  }

  public void setCoupon_enabled(short coupon_enabled) {
    this.coupon_enabled = coupon_enabled;
  }

  public Long getCoupon_start_at() {
    return coupon_start_at;
  }

  public void setCoupon_start_at(Long coupon_start_at) {
    this.coupon_start_at = coupon_start_at;
  }

  public Long getCoupon_end_at() {
    return coupon_end_at;
  }

  public void setCoupon_end_at(Long coupon_end_at) {
    this.coupon_end_at = coupon_end_at;
  }

  public Integer getCoupon_number() {
    return coupon_number;
  }

  public void setCoupon_number(Integer coupon_number) {
    this.coupon_number = coupon_number;
  }

  public Short getCoupon_discount() {
    return coupon_discount;
  }

  public void setCoupon_discount(Short coupon_discount) {
    this.coupon_discount = coupon_discount;
  }

  public String getPaper_width() {
    return paper_width;
  }

  public void setPaper_width(String paper_width) {
    this.paper_width = paper_width;
  }

  public short getAuto_print_tangchi() {
    return auto_print_tangchi;
  }

  public void setAuto_print_tangchi(short auto_print_tangchi) {
    this.auto_print_tangchi = auto_print_tangchi;
  }

  public double getSpeed_coef1() {
    return speed_coef1;
  }

  public void setSpeed_coef1(double speed_coef1) {
    this.speed_coef1 = speed_coef1;
  }

  public double getSpeed_coef2() {
    return speed_coef2;
  }

  public void setSpeed_coef2(double speed_coef2) {
    this.speed_coef2 = speed_coef2;
  }

  public double getSpeed_coef3() {
    return speed_coef3;
  }

  public void setSpeed_coef3(double speed_coef3) {
    this.speed_coef3 = speed_coef3;
  }

  public double getAvg_comment_time() {
    return avg_comment_time;
  }

  public void setAvg_comment_time(double avg_comment_time) {
    this.avg_comment_time = avg_comment_time;
  }

  public String getActivities() {
    return activities;
  }

  public void setActivities(String activities) {
    this.activities = activities;
  }

  public short getHas_food_img() {
    return has_food_img;
  }

  public void setHas_food_img(short has_food_img) {
    this.has_food_img = has_food_img;
  }

  public short getOnline_payment() {
    return online_payment;
  }

  public void setOnline_payment(short online_payment) {
    this.online_payment = online_payment;
  }

  public short getInvoice() {
    return invoice;
  }

  public void setInvoice(short invoice) {
    this.invoice = invoice;
  }

  public double getInvoice_min_amount() {
    return invoice_min_amount;
  }

  public void setInvoice_min_amount(double invoice_min_amount) {
    this.invoice_min_amount = invoice_min_amount;
  }

  public String getAttribute() {
    return attribute;
  }

  public void setAttribute(String attribute) {
    this.attribute = attribute;
  }

  public String getDeliver_area() {
    return deliver_area;
  }

  public void setDeliver_area(String deliver_area) {
    this.deliver_area = deliver_area;
  }

  public String getOpen_date() {
    return open_date;
  }

  public void setOpen_date(String open_date) {
    this.open_date = open_date;
  }

  public double getOriginal_order_quantity() {
    return original_order_quantity;
  }

  public void setOriginal_order_quantity(double original_order_quantity) {
    this.original_order_quantity = original_order_quantity;
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

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getCorporation() {
    return corporation;
  }

  public void setCorporation(String corporation) {
    this.corporation = corporation;
  }

  public int getGeohash_ranking_weight() {
    return geohash_ranking_weight;
  }

  public void setGeohash_ranking_weight(int geohash_ranking_weight) {
    this.geohash_ranking_weight = geohash_ranking_weight;
  }

  public List<List<String>> getServing_time() {
    return serving_time;
  }

  public void setServing_time(List<List<String>> serving_time) {
    this.serving_time = serving_time;
  }

  public List<Short> getNum_ratings() {
    return num_ratings;
  }

  public void setNum_ratings(List<Short> num_ratings) {
    this.num_ratings = num_ratings;
  }

  public int getRecent_food_popularity() {
    return recent_food_popularity;
  }

  public void setRecent_food_popularity(int recent_food_popularity) {
    this.recent_food_popularity = recent_food_popularity;
  }

  public short getIs_premium() {
    return is_premium;
  }

  public void setIs_premium(short is_premium) {
    this.is_premium = is_premium;
  }

  public List<String> getDeliver_times() {
    return deliver_times;
  }

  public void setDeliver_times(List<String> deliver_times) {
    this.deliver_times = deliver_times;
  }

  public String getNapos_client_settings() {
    return napos_client_settings;
  }

  public void setNapos_client_settings(String napos_client_settings) {
    this.napos_client_settings = napos_client_settings;
  }

  public String getKeeper_identity_card() {
    return keeper_identity_card;
  }

  public void setKeeper_identity_card(String keeper_identity_card) {
    this.keeper_identity_card = keeper_identity_card;
  }

  public Boolean getSupport_coupon() {
    return support_coupon;
  }

  public void setSupport_coupon(Boolean support_coupon) {
    this.support_coupon = support_coupon;
  }

  public String getTime_ensure_full_description() {
    return time_ensure_full_description;
  }

  public void setTime_ensure_full_description(String time_ensure_full_description) {
    this.time_ensure_full_description = time_ensure_full_description;
  }

  public int getCome_from() {
    return come_from;
  }

  public void setCome_from(int come_from) {
    this.come_from = come_from;
  }

  public int getNo_agent_fee_total() {
    return no_agent_fee_total;
  }

  public void setNo_agent_fee_total(int no_agent_fee_total) {
    this.no_agent_fee_total = no_agent_fee_total;
  }

  public int getService_category() {
    return service_category;
  }

  public void setService_category(int service_category) {
    this.service_category = service_category;
  }

  public int getDock_corp_id() {
    return dock_corp_id;
  }

  public void setDock_corp_id(int dock_corp_id) {
    this.dock_corp_id = dock_corp_id;
  }

  public String getDeliver_geojson() {
    return deliver_geojson;
  }

  public void setDeliver_geojson(String deliver_geojson) {
    this.deliver_geojson = deliver_geojson;
  }

  public double getService_rating() {
    return service_rating;
  }

  public void setService_rating(double service_rating) {
    this.service_rating = service_rating;
  }

  public int getRecent_order_num() {
    return recent_order_num;
  }

  public void setRecent_order_num(int recent_order_num) {
    this.recent_order_num = recent_order_num;
  }

  public int getHas_activity() {
    return has_activity;
  }

  public void setHas_activity(int has_activity) {
    this.has_activity = has_activity;
  }

  public Short getCertification_type() {
    return certification_type;
  }

  public void setCertification_type(Short certification_type) {
    this.certification_type = certification_type;
  }

  public int getOid() {
    return oid;
  }

  public void setOid(int oid) {
    this.oid = oid;
  }

  public Short getMethod_of_payment() {
    return method_of_payment;
  }

  public void setMethod_of_payment(Short method_of_payment) {
    this.method_of_payment = method_of_payment;
  }

  public short getIs_exclusive() {
    return is_exclusive;
  }

  public void setIs_exclusive(short is_exclusive) {
    this.is_exclusive = is_exclusive;
  }

  public short getType() {
    return type;
  }

  public void setType(short type) {
    this.type = type;
  }

  public String getOpen_time() {
    return open_time;
  }

  public void setOpen_time(String open_time) {
    this.open_time = open_time;
  }
}

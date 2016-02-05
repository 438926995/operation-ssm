package com.eleme.soa.gaia;

import me.ele.contract.annotation.Index;

public class TCity {
  private @Index(1) int id;
  private @Index(2) String name;
  private @Index(3) String abbr;
  private @Index(4) String hint;
  private @Index(5) String area_code;
  private @Index(6) String company_name;
  private @Index(7) String company_address;
  private @Index(8) int sort;
  private @Index(9) String notice_emails;
  private @Index(10) short is_valid;
  private @Index(11) int district_code;
  private @Index(12) String boundary;
  private @Index(13) double latitude;
  private @Index(14) double longitude;
  private @Index(15) String company_abbr;
  private @Index(16) int country_region_id;
  private @Index(17) short is_map;
  private @Index(18) String pinyin;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAbbr() {
    return abbr;
  }

  public void setAbbr(String abbr) {
    this.abbr = abbr;
  }

  public String getHint() {
    return hint;
  }

  public void setHint(String hint) {
    this.hint = hint;
  }

  public String getArea_code() {
    return area_code;
  }

  public void setArea_code(String area_code) {
    this.area_code = area_code;
  }

  public String getCompany_name() {
    return company_name;
  }

  public void setCompany_name(String company_name) {
    this.company_name = company_name;
  }

  public String getCompany_address() {
    return company_address;
  }

  public void setCompany_address(String company_address) {
    this.company_address = company_address;
  }

  public int getSort() {
    return sort;
  }

  public void setSort(int sort) {
    this.sort = sort;
  }

  public String getNotice_emails() {
    return notice_emails;
  }

  public void setNotice_emails(String notice_emails) {
    this.notice_emails = notice_emails;
  }

  public short getIs_valid() {
    return is_valid;
  }

  public void setIs_valid(short is_valid) {
    this.is_valid = is_valid;
  }

  public int getDistrict_code() {
    return district_code;
  }

  public void setDistrict_code(int district_code) {
    this.district_code = district_code;
  }

  public String getBoundary() {
    return boundary;
  }

  public void setBoundary(String boundary) {
    this.boundary = boundary;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getCompany_abbr() {
    return company_abbr;
  }

  public void setCompany_abbr(String company_abbr) {
    this.company_abbr = company_abbr;
  }

  public int getCountry_region_id() {
    return country_region_id;
  }

  public void setCountry_region_id(int country_region_id) {
    this.country_region_id = country_region_id;
  }

  public short getIs_map() {
    return is_map;
  }

  public void setIs_map(short is_map) {
    this.is_map = is_map;
  }

  public String getPinyin() {
    return pinyin;
  }

  public void setPinyin(String pinyin) {
    this.pinyin = pinyin;
  }
}

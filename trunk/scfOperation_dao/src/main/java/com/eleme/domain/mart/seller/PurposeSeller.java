package com.eleme.domain.mart.seller;

import java.io.Serializable;

/**
 * 意向商户查询封装Bean.
 * 
 * @author penglau
 *
 */
public class PurposeSeller implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1928706396699616749L;

  // 主键
  private Integer id;
  // 餐厅名称
  private String res_name;
  // 手机号
  private String mobile;
  // 餐厅地址
  private String res_addr;
  // napos餐厅id
  private Integer napos_resid;
  // 餐厅id
  private Integer res_id;
  // 批次
  private Integer batch;
  // 合作方
  private String partner;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRes_name() {
    return res_name;
  }

  public void setRes_name(String res_name) {
    this.res_name = res_name;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getRes_addr() {
    return res_addr;
  }

  public void setRes_addr(String res_addr) {
    this.res_addr = res_addr;
  }

  public Integer getNapos_resid() {
    return napos_resid;
  }

  public void setNapos_resid(Integer napos_resid) {
    this.napos_resid = napos_resid;
  }

  public Integer getRes_id() {
    return res_id;
  }

  public void setRes_id(Integer res_id) {
    this.res_id = res_id;
  }

  public Integer getBatch() {
    return batch;
  }

  public void setBatch(Integer batch) {
    this.batch = batch;
  }

  public String getPartner() {
    return partner;
  }

  public void setPartner(String partner) {
    this.partner = partner;
  }

}

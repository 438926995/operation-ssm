package com.eleme.bean.seller;

import java.io.Serializable;

/**
 * 意向商户查询封装bean.
 * 
 * @author penglau
 *
 */
public class PurposeSellerQueryBean implements Serializable {

  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = -5050003859188475505L;

  // 商户名称
  private String sellerName;
  // 批次
  private String batch;
  // 合作方
  private String partner;

  public String getSellerName() {
    return sellerName;
  }

  public void setSellerName(String sellerName) {
    this.sellerName = sellerName;
  }

  public String getBatch() {
    return batch;
  }

  public void setBatch(String batch) {
    this.batch = batch;
  }

  public String getPartner() {
    return partner;
  }

  public void setPartner(String partner) {
    this.partner = partner;
  }

}

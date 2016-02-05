package com.eleme.bean.seller;

import com.eleme.domain.mart.PageQueryBean;

/**
 * 商户列表，查询条件封装类。
 * 
 * @author penglau
 *
 */
public class SellerQueryBean extends PageQueryBean {

  // 商户名称
  private String sellerName;
  /**
   * 店主
   */
  private String keeperName;
  /**
   * 电话号码
   */
  private String keeperPhone;
  // 商户id
  private String sellerId;
  // Napos餐厅id
  private String naposResOid;
  // 是否仅napos
  private String onlyNapos;
  // 是否导入
  private String isImport;
  // 期数
  private String batch;
  /**
   * 是否有效
   */
  private String isValid;


  public String getIsImport() {
    return isImport;
  }

  public void setIsImport(String isImport) {
    this.isImport = isImport;
  }

  public String getBatch() {
    return batch;
  }

  public void setBatch(String batch) {
    this.batch = batch;
  }

  public String getSellerName() {
    return sellerName;
  }

  public void setSellerName(String sellerName) {
    this.sellerName = sellerName;
  }

  public String getSellerId() {
    return sellerId;
  }

  public void setSellerId(String sellerId) {
    this.sellerId = sellerId;
  }

  public String getNaposResOid() {
    return naposResOid;
  }

  public void setNaposResOid(String naposResOid) {
    this.naposResOid = naposResOid;
  }

  public String getOnlyNapos() {
    return onlyNapos;
  }

  public void setOnlyNapos(String onlyNapos) {
    this.onlyNapos = onlyNapos;
  }

  public String getKeeperName() {
    return keeperName;
  }

  public void setKeeperName(String keeperName) {
    this.keeperName = keeperName;
  }

  public String getKeeperPhone() {
    return keeperPhone;
  }

  public void setKeeperPhone(String keeperPhone) {
    this.keeperPhone = keeperPhone;
  }

  public String getIsValid() {
    return isValid;
  }

  public void setIsValid(String isValid) {
    this.isValid = isValid;
  }

}

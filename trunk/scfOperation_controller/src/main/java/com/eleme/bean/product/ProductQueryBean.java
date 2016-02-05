package com.eleme.bean.product;

/**
 * 
 * 融资产品询条件封装类
 * 
 * @author yonglin.zhu
 *
 */
public class ProductQueryBean {
  // 产品名称
  private String ptName;
  // 金融机构ID
  private String foId;

  public String getPtName() {
    return ptName;
  }

  public void setPtName(String ptName) {
    this.ptName = ptName;
  }

  public String getFoId() {
    return foId;
  }

  public void setFoId(String foId) {
    this.foId = foId;
  }



}

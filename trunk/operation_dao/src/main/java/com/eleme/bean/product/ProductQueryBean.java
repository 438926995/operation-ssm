package com.eleme.bean.product;

public class ProductQueryBean {

  private String fpName;
  private Integer productStatus;
  private Integer offset;
  private Integer limit;

  public String getFpName() {
    return fpName;
  }

  public void setFpName(String fpName) {
    this.fpName = fpName;
  }

  public Integer getProductStatus() {
    return productStatus;
  }

  public void setProductStatus(Integer productStatus) {
    this.productStatus = productStatus;
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

}

package com.eleme.domain.mart.finance;

import java.util.List;

import com.eleme.domain.mart.product.MFinanceProduct;

/**
 * 金融机构页面显示封装类
 * 
 * @author yonglin.zhu
 *
 */
public class MFinanceOrgVo extends MFinanceOrg {
  private static final long serialVersionUID = -3345487600082624859L;
  
  // 贷款产品列表
  private List<MFinanceProduct> financeProducts;

  public List<MFinanceProduct> getFinanceProducts() {
    return financeProducts;
  }

  public void setFinanceProducts(List<MFinanceProduct> financeProducts) {
    this.financeProducts = financeProducts;
  }
}

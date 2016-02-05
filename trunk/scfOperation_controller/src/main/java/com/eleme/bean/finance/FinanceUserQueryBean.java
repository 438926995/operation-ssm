package com.eleme.bean.finance;

/**
 * 
 * 金融机构账号查询条件封装类
 * 
 * @author yonglin.zhu
 *
 */
public class FinanceUserQueryBean {
  // 机构名称
  private String userName;
  // 金融机构ID
  private String foId;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFoId() {
    return foId;
  }

  public void setFoId(String foId) {
    this.foId = foId;
  }

}

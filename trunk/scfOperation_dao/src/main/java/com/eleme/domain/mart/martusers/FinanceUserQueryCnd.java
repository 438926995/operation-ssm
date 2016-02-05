package com.eleme.domain.mart.martusers;

/**
 * 
 * 金融机构账号查询条件封装类
 * 
 * @author yonglin.zhu
 *
 */
public class FinanceUserQueryCnd {
  // 分页开始
  private Integer startRecord;
  // 页面显示条数
  private Integer pageSize;
  // 账号名称
  private String userName;
  // 所属金融机构ID
  private Integer foId;
  // 账户ID
  private Integer userId;

  public Integer getStartRecord() {
    return startRecord;
  }

  public void setStartRecord(Integer startRecord) {
    this.startRecord = startRecord;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getFoId() {
    return foId;
  }

  public void setFoId(Integer foId) {
    this.foId = foId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

}

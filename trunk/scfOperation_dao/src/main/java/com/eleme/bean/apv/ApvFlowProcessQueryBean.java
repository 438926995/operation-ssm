package com.eleme.bean.apv;

/**
 * 审批流列表 查询条件封装类
 * 
 * @author xudong.gu
 *
 */
public class ApvFlowProcessQueryBean {

  // 审批流名
  private String procsName;

  // 当前页面
  private Integer startPage;

  // 页面总数
  private Integer pageSize;

  public String getProcsName() {
    return procsName;
  }

  public void setProcsName(String procsName) {
    this.procsName = procsName;
  }

  public Integer getStartPage() {
    return startPage;
  }

  public void setStartPage(Integer startPage) {
    this.startPage = startPage;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

}

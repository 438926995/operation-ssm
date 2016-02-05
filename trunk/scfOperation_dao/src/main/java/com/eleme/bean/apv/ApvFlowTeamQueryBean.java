package com.eleme.bean.apv;

/**
 * 审批组列表 查询条件封装类
 * 
 * @author xudong.gu
 *
 */
public class ApvFlowTeamQueryBean {

  // 审批组名
  private String teamName;

  // 当前页面
  private Integer startPage;

  // 页面总数
  private Integer pageSize;

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
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

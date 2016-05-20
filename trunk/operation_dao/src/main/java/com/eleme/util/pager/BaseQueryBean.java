package com.eleme.util.pager;

import com.eleme.constants.PagerConstants;

public abstract class BaseQueryBean implements IQueryBean {
  private static final long serialVersionUID = -4909265981713996416L;

  private Integer currentPage = 1;
  private Integer pageSize = PagerConstants.PAGE_SIZE;
  private Integer startRecord = 0;
  
  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = (currentPage < 1) ? 1 : currentPage;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = (pageSize < 1) ? PagerConstants.PAGE_SIZE : pageSize;
  }
  
  public void noLimitMode() {
    this.currentPage = 1;
    this.pageSize = Integer.MAX_VALUE;
  }

  public Integer getStartRecord() {
    this.startRecord = ((currentPage - 1) * pageSize);
    return startRecord;
  }

  public void setStartRecord(Integer startRecord) {
    this.startRecord = startRecord;
  }
  
  
}

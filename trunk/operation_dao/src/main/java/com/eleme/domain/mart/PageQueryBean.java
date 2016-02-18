package com.eleme.domain.mart;

import com.eleme.constants.PagerConstants;

/**
 * 分页查询
 * 
 * @author huwenwen
 */
public class PageQueryBean {

  /**
   * 当前页码
   */
  private int currentPage = 0;
  
  /**
   * 每页分页条数 默认10条
   */
  private int pageSize = PagerConstants.PAGE_SIZE;

  /**
   * 开始条目
   */
  private int startItem = 0;

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getStartItem() {
    return startItem;
  }

  public void setStartItem(int startItem) {
    this.startItem = startItem;
  }

}

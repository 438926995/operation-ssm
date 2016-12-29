package com.wen.util.pager;

/**
 * 公用分页 页码数据封装类.
 * 
 * @author huwenwen
 *
 */
public class PgInfo {

  /**
   * 总共记录数.
   */
  private Integer totalCount;
  /**
   * 每页条目数.
   */
  private Integer pageSize;
  /**
   * 总页数.
   */
  private Integer sumPage;
  /**
   * 当前页.
   */
  private Integer pageNo;
  /**
   * 是否起始页.
   */
  private boolean first;
  /**
   * 是否末尾页.
   */
  private boolean last;

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getSumPage() {
    return sumPage;
  }

  public void setSumPage(Integer sumPage) {
    this.sumPage = sumPage;
  }

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public boolean isFirst() {
    return first;
  }

  public void setFirst(boolean first) {
    this.first = first;
  }

  public boolean isLast() {
    return last;
  }

  public void setLast(boolean last) {
    this.last = last;
  }

}

package com.eleme.util.pager;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面分页条数据封装类.
 * 
 * @author penglau
 *
 */
public class PageInfo {

  /**
   * 页码.
   */
  private Integer pageNo;

  /**
   * 是否是当前页.
   */
  private Boolean isCurrentPage;

  /**
   * 是否隐藏.
   */
  private Boolean isHidden;

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Boolean getIsCurrentPage() {
    return isCurrentPage;
  }

  public void setIsCurrentPage(Boolean isCurrentPage) {
    this.isCurrentPage = isCurrentPage;
  }

  public Boolean getIsHidden() {
    return isHidden;
  }

  public void setIsHidden(Boolean isHidden) {
    this.isHidden = isHidden;
  }

  /**
   * init method
   * 
   * @param currentPage 当前页
   * @param sumPage 总页数.
   * @return list
   */
  public static List<PageInfo> packagingPageParams(Integer currentPage, Integer sumPage) {
    List<PageInfo> pageList = new ArrayList<PageInfo>();
    if (sumPage != null && currentPage != null) {
      Integer[] numArray = getShowPageArray(sumPage, currentPage);
      Integer firstPage = numArray[0];
      Integer lastPage = numArray[1];
      for (int i = 1; i <= sumPage; i++) {
        if (i >= firstPage && i <= lastPage) {
          pageList.add(fillPage(i, i == currentPage, false));
        } else {
          pageList.add(fillPage(i, false, true));
        }
      }
    }
    return pageList;
  }

  /**
   * 根据参数构造PageInfo数据。
   * 
   * @param pageNo 第几页
   * @param isCurrentPage 是否当前页
   * @param isHidden 是否隐藏.
   * @return 构造出的PageInfo数据
   */
  private static PageInfo fillPage(Integer pageNo, Boolean isCurrentPage, Boolean isHidden) {
    PageInfo page = new PageInfo();
    page.setIsCurrentPage(isCurrentPage);
    page.setIsHidden(isHidden);
    page.setPageNo(pageNo);
    return page;
  }

  /**
   * get page array.
   * 
   * @param sumPage 总页数
   * @param currentPage 当前页
   * @return array
   */
  private static Integer[] getShowPageArray(Integer sumPage, Integer currentPage) {
    Integer[] pageArray = new Integer[2];
    if (sumPage != null && currentPage != null) {
      if (sumPage <= 10) {
        pageArray[0] = 1;
        pageArray[1] = sumPage;
      } else {
        if (currentPage > sumPage / 2) {
          if (currentPage + 5 > sumPage) {
            pageArray[0] = currentPage - 4 + sumPage - currentPage - 5;
            pageArray[1] = sumPage;
          } else {
            pageArray[0] = currentPage - 4;
            pageArray[1] = currentPage + 5;
          }
        } else {
          if (currentPage - 4 < 1) {
            pageArray[0] = 1;
            pageArray[1] = 10;
          } else {
            pageArray[0] = currentPage - 4;
            pageArray[1] = currentPage + 5;
          }
        }
      }
    }
    return pageArray;
  }

}

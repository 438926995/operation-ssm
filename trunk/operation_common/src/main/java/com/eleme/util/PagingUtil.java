package com.eleme.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 * 
 * @author huwenwen
 *
 */
public class PagingUtil {

  /**
   * 手动分页
   * 
   * @param list
   * @param currentPage
   * @param pageSize
   * @return
   */
  public static <T> List<T> handPaging(List<T> list, int currentPage, int pageSize) {
    List<T> results = new ArrayList<>();
    int pageTotal = list.size();
    if (currentPage * pageSize > pageTotal) {
      for (int i = (currentPage - 1) * pageSize; i < pageTotal; i++) {
        results.add(list.get(i));
      }
    } else {
      for (int i = (currentPage - 1) * pageSize; i < currentPage * pageSize; i++) {
        results.add(list.get(i));
      }
    }
    return results;
  }
}

package com.wen.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

  /**
   * 可判断字符串参数中，是否有为空的字符串.
   * 
   * @param strs 参数个数动态的字符串参数
   * @return true 有为空的字符串
   */
  public static boolean isEmpty(String... strs) {
    for (int i = 0; i < strs.length; i++) {
      if (StringUtils.isEmpty(strs[i])) {
        return true;
      }
    }
    return false;
  }

  /**
   * 数组合并 字符串
   * 
   * @author huwenwen
   * @since 2015年12月29日
   * @param orgArr 原始字符数组
   * @param contactStr 连接字符串
   * @return 合并后的字符串
   */
  public static final String arrJoin(String[] orgArr, String contactStr) {
    StringBuilder result = new StringBuilder();
    if (orgArr != null) {
      for (String orgStr : orgArr) {
        result.append(contactStr);
        result.append(orgStr);
      }
    }

    if (contactStr != null && result.length() > 0) {
      return result.substring(contactStr.length());
    }
    return result.toString();

  }

}

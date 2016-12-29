package com.wen.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 通用工具类.
 * 
 * @author huwenwen
 *
 */
public class CommonUtil {
  /**
   * 根据Exception对象获取异常详细信息.
   * 
   * @param ex Exception对象
   * @return 异常详细信息
   */
  public static String getErrorMessage(Exception ex) {
    StringWriter sw = new StringWriter();
    ex.printStackTrace(new PrintWriter(sw, true));
    return sw.toString();
  }

  /**
   * 根据Throwable对象获取异常详细信息.
   * 
   * @param e Throwable对象
   * @return 异常详细信息
   */
  public static String getThrowableMessage(Throwable throwable) {
    StringWriter sw = new StringWriter();
    throwable.printStackTrace(new PrintWriter(sw, true));
    return sw.toString();
  }

  /**
   * 根据Throwable对象获取异常简要信息.
   * 
   * @param ex Exception对象.
   * @return 异常简要信息.
   */
  @Deprecated
  public static String getErrorDescription(Exception ex) {
    if (ex.getCause() != null && ex.getCause().getClass() != null) {
      String simpleName = ex.getCause().getClass().getSimpleName();
      String message = ex.getCause().getMessage();
      String error =
          "Error occured caused by exception <strong>%s</strong>:<br/> <strong>%s</strong>";
      return String.format(error, simpleName, message);
    } else {
      return String.format("Error : %s", ex.getMessage());
    }
  }

  /**
   * 根据request对象获取ip地址.
   * 
   * @param request 请求对象
   * @return IP地址.
   */
  public static String getRemoteIP(HttpServletRequest request) {
    if (request == null) {
      return "";
    }
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  /**
   * 判断多个对象或者为空.
   * 
   * @param objs 对象数组.
   * @return 有一个为空返回true,否则返回false.
   * @throws Exception error
   */
  public static Boolean verifyIsNullOfOr(Object... objs) throws Exception {
    if (objs == null || objs.length == 0) {
      return true;
    }
    for (Object obj : objs) {
      if (obj == null) {
        return true;
      }
      if (obj instanceof String) {
        if (StringUtils.isEmpty(obj.toString())) {
          return true;
        }
        continue;
      } else if (obj instanceof Collection) {
        if (((Collection<?>) obj).size() == 0) {
          return true;
        }
        continue;
      } else if (obj instanceof Map) {
        if (((Map<?, ?>) obj).size() == 0) {
          return true;
        }
        continue;
      }
    }
    return false;
  }

  public static boolean equals(final Object o1, final Object o2) {
    if (o1 == o2) {
      return true;
    }
    if (o1 == null || o2 == null) {
      return false;
    }
    return o1.equals(o2);
  }
}

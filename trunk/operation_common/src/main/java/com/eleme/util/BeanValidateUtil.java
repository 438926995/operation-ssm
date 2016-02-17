package com.eleme.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.eleme.bean.ErrorMessage;

/**
 * spring bean 验证工具类
 * 
 * @author penglau
 *
 */
public class BeanValidateUtil {

  private static WebApplicationContext wc;

  public static void setWebApplicationContext(WebApplicationContext wc) {
    BeanValidateUtil.wc = wc;
  }

  /**
   * 返回一个Bean
   * 
   * @param clz
   * @return
   */
  public static synchronized <T> T getSpringBean(Class<T> clz) {
    if (wc == null) {
      HttpServletRequest request = getHttpServletRequest();
      ServletContext sc = request.getSession().getServletContext();
      wc = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
    }
    return wc.getBean(clz);
  }

  /**
   * 返回一个Bean
   * 
   * @Title: getSpringBean
   * @Description:
   * @param clz
   * @return
   */
  public static synchronized Object getSpringBean(String clz) {
    if (wc == null) {
      HttpServletRequest request = getHttpServletRequest();
      ServletContext sc = request.getSession().getServletContext();
      wc = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
    }
    return wc.getBean(clz);
  }

  /**
   * 获取当前的request对象
   * 
   * @Title: getHttpServletRequest
   * @Description:
   * @return
   */
  public static synchronized HttpServletRequest getHttpServletRequest() {
    ServletRequestAttributes ra =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    return ra.getRequest();
  }

  /**
   * 获取当前请求的uri eg:http://localhost:8080/oa/workorder/saveWorkOrderAdd则返回/workorder/saveWorkOrderAdd
   * 
   * @Title: getUri
   * @Description:
   * @return
   */
  public static synchronized String getUri() {
    return getHttpServletRequest().getServletPath();
  }

  /**
   * 已对象形似返回数据校验的错误信息
   * 
   * @Title: getErrorMessage
   * @Description:
   * @param result
   * @return
   */
  public static List<ErrorMessage> getErrorMessage(BindingResult result) {
    List<ErrorMessage> list = new ArrayList<ErrorMessage>();
    List<FieldError> fieldErrors = result.getFieldErrors();
    for (FieldError fieldError : fieldErrors) {
      list.add(new ErrorMessage(fieldError.getField(), fieldError.getCode()));
    }
    return list;
  }

  /**
   * 返回结果集中的错误信息
   * 
   * @Title: getResultErrors
   * @Description:
   * @param result
   * @return
   */
  public static String getResultErrors(BindingResult result) {
    List<FieldError> fieldErrors = result.getFieldErrors();
    StringBuilder sb = new StringBuilder();
    for (FieldError fieldError : fieldErrors) {
      sb.append(fieldError.getField() + "=" + "\"" + fieldError.getCode() + "\",");
    }
    String str = "";
    if (sb.length() > 0) {
      str = sb.substring(0, sb.length() - 1);
    }
    return str;
  }

  /**
   * 向客户端返回信息(必须是key=value格式)
   * 
   * @Title: writeErrorMessage2Client
   * @Description:
   * @param response
   * @param result
   * @throws IOException
   */
  public static void writeErrorMessage2Client(HttpServletResponse response, BindingResult result)
      throws IOException {
    response.setContentType("text/html; charset=utf-8");
    response.setCharacterEncoding("utf-8");
    String str = BeanValidateUtil.getResultErrors(result);
    response.getOutputStream().write(str.getBytes());
  }

}

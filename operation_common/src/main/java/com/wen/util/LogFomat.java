package com.wen.util;

import org.springframework.web.method.HandlerMethod;

import java.util.Objects;

/**
 * 日志格式化工具类.
 * 
 * @author huwenwen
 *
 */
public class LogFomat {


  /**
   * 使用^A^分隔格式化日志.
   * 
   * @param handlerMethod 响应的处理器.
   * @param uri 请求路径.
   * @param ip 登录用户IP.
   * @param userId 登录用户的ID
   * @return 使用^A^分隔后的日志.
   */
  public static String formatControllerLogger(HandlerMethod handlerMethod, String uri, String ip,
      String userId, long time) {
    StringBuilder sb = new StringBuilder();
    sb.append(handlerMethod.getBean().getClass().getSimpleName());
    sb.append("^A^");
    sb.append(handlerMethod.getMethod().getName());
    sb.append("^A^USERACT^A^");
    sb.append(uri);
    sb.append("^A^");
    sb.append(ip);
    sb.append("^A^");
    sb.append(userId);
    sb.append("^A^");
    sb.append(time);
    return sb.toString();
  }

  /**
   * 格式化Service日志信息.
   * 
   * @param className 类名
   * @param methodName 方法名
   * @param parameters 参数数组
   * @param returnValue 方法返回值
   * @param executeTime 执行耗时
   * @param message ?
   * @param aopType aop类型，before/after
   * @return 格式化后的日志信息
   */
  public static String formatServiceLogger(String className, String methodName, Object[] parameters,
      Object returnValue, Long executeTime, String message, String aopType) {
    StringBuffer sbBuffer = new StringBuffer();
    sbBuffer.append("message:" + message);
    sbBuffer.append("^A^");
    sbBuffer.append("className:" + className);
    sbBuffer.append("^A^");
    sbBuffer.append("method:" + methodName);
    sbBuffer.append("^A^");
    if ("before".equals(aopType)) {
      sbBuffer.append("parameters:");
      for (int i = 0; i < parameters.length; i++) {
        Object param = parameters[i];
        sbBuffer.append(Objects.toString(param, ""));
        if (i < parameters.length - 1) {
          sbBuffer.append(",");
        }
      }
    }
    if ("after".equals(aopType)) {
      sbBuffer.append("^A^");
      sbBuffer.append(" returnValue:" + Objects.toString(returnValue, ""));
      sbBuffer.append("^A^");
      sbBuffer.append(" executeTime:" + executeTime + "ms");
    }
    return sbBuffer.toString();
  }

  /**
   * 格式化Controller日志信息.
   * 
   * @param classz 拦截的类
   * @param requestUrl 访问的URL
   * @param method 拦截的方法
   * @param executeTime 消耗时间
   * @param ip 请求者IP
   * @return 格式化后的日志信息.
   */
  public static String formatControllerLogger(Class<?> classz, String requestUrl, String method,
      Long executeTime, String ip) {
    StringBuffer sbBuffer = new StringBuffer();
    sbBuffer.append("className:" + classz.getName());
    sbBuffer.append("^A^");
    sbBuffer.append("method:" + method);
    sbBuffer.append("^A^");
    if (executeTime != null) {
      sbBuffer.append("executeTime:" + executeTime + "ms");
      sbBuffer.append("^A^");
    }
    sbBuffer.append("uri:" + requestUrl);
    sbBuffer.append("^A^");
    sbBuffer.append("ip:" + ip);
    return sbBuffer.toString();
  }

}

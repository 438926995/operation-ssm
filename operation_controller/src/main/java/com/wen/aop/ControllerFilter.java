package com.wen.aop;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * 日志过滤器，定义如果是ControllerInterceptor拦截器类，则不记录日志.
 * 
 * @author huwenwen
 */
public class ControllerFilter extends Filter<ILoggingEvent> {
  @Override
  public FilterReply decide(ILoggingEvent event) {
    if (event.getLoggerName() != null
        && event.getLoggerName().equals("com.wen.aop.ControllerInterceptor")) {
      // 请求被抛弃
      return FilterReply.DENY;
    } else {
      // 继续下一步
      return FilterReply.NEUTRAL;
    }
  }
}

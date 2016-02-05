package com.eleme.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.eleme.annotation.form.AvoidDuplicateSubmission;
import com.eleme.bean.SessionBean;
import com.eleme.util.CommonUtil;
import com.eleme.util.HttpSessionUtil;
import com.eleme.util.LogFomat;
import com.eleme.util.TokenProcessor;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * Controller拦截器，记录执行耗时.
 * 
 * @author penglau
 *
 */
public class ControllerInterceptor extends HandlerInterceptorAdapter {
  // log实例.
  private Log log = LogFactory.getLog(ControllerInterceptor.class);
  // spring提供的命名ThreadLocal实现.
  private NamedThreadLocal<Long> startTimeThreadLocal =
      new NamedThreadLocal<Long>("StopWatch-StartTime");
  // 传递到logback日志中.
  private final static String USER_KEY = "userId";

  /**
   * 预处理回调方法.
   * 
   * @param request
   * @param response
   * @param handler 响应的处理器.
   * @return true表示继续流程,false表示流程中断.
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    long beginTime = System.currentTimeMillis();
    // 线程绑定变量（该数据只有当前请求的线程可见）
    startTimeThreadLocal.set(beginTime);
    Object obj = request.getSession().getAttribute("userinfo");
    SessionBean sb = HttpSessionUtil.buildUserInfo(obj);
    if (sb == null) {
      sb = new SessionBean();
    }
    if (sb.getUserId() != null) {
      log.info("(sb.getUserId() == null :)" + (sb.getUserId() == null));
//      MDC.put(USER_KEY, String.valueOf(sb.getUserId()));
    }
    // 获取method的注解
    if (handler instanceof HandlerMethod) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      Method method = handlerMethod.getMethod();
      AvoidDuplicateSubmission annotation = method.getAnnotation(AvoidDuplicateSubmission.class);
      /**
       * TODO 1. 判断表单是否已过期（防止回退后，token失效，用户再次提交表单后，提示二次提交，影响用户体验）。 2. 表单二次提交，需要给用户提示，目前仅做了日志记录。
       */
      if (annotation != null) {
        // needSaveToken 向session中设置防重复提交的token
        boolean needSaveSession = annotation.needSaveToken();
        if (needSaveSession) {
          request.getSession(false).setAttribute("token",
              TokenProcessor.getInstance().generateToken(request));
        }
        // needSaveToken 判断是否重复提交，然后移除token
        boolean needRemoveSession = annotation.needRemoveToken();
        if (needRemoveSession) {
          if (isRepeatSubmit(request)) {
            log.warn("please don't repeat submit,[user:" + sb.getUserName() + ",url:"
                + request.getServletPath() + "]");
            return false;
          }
          request.getSession(false).removeAttribute("token");
        }
      }
    }

    return super.preHandle(request, response, handler);
  }

  /**
   * 重写请求处理完毕回调方法(视图渲染完毕时).
   * 
   * @param request
   * @param response
   * @param handler 响应的处理器.
   * @param ex
   */
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    // TODO 可增加一个页面渲染后的耗时.
    // 捕获异常
    if (ex != null) {
      if (handler instanceof HandlerMethod) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        StringBuilder sb = new StringBuilder();
        sb.append(handlerMethod.getBean().getClass().getSimpleName());
        sb.append("^A^");
        sb.append(handlerMethod.getMethod().getName());
        sb.append("^A^");
        sb.append(CommonUtil.getErrorMessage(ex));
        log.error(sb.toString());
      } else {
        StringBuilder sb = new StringBuilder();
        sb.append(CommonUtil.getErrorMessage(ex));
        log.error(sb.toString());
      }
    }
//    MDC.remove(USER_KEY);
    super.afterCompletion(request, response, handler, ex);
  }

  /**
   * 重写后处理回调方法(渲染视图之前).
   * 
   * @param request
   * @param response
   * @param handler 响应的处理器.
   * @param modelAndView 模型和视图对象.
   */
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    if (handler instanceof HandlerMethod) {
      String uri = request.getRequestURI();
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      String ip = CommonUtil.getRemoteIP(request);
      Object obj = request.getSession().getAttribute("userinfo");
      SessionBean sb = HttpSessionUtil.buildUserInfo(obj);
      if (sb == null) {
        sb = new SessionBean();
      }
      super.postHandle(request, response, handler, modelAndView);
      // 结束时间
      long endTime = System.currentTimeMillis();
      // 得到线程绑定的局部变量（开始时间）
      long beginTime = startTimeThreadLocal.get();
      // 计算消耗的时间
      long consumeTime = endTime - beginTime;
      log.info(LogFomat.formatControllerLogger(handlerMethod, uri, ip,
          String.valueOf(sb.getUserId()), consumeTime));
    } else {
      super.postHandle(request, response, handler, modelAndView);
    }
  }

  /**
   * 判断请求是否为重复提交.
   * 
   * @param request
   * @return
   */
  private boolean isRepeatSubmit(HttpServletRequest request) {
    String serverToken = (String) request.getSession(false).getAttribute("token");
    if (serverToken == null) {
      return true;
    }
    String clinetToken = request.getParameter("token");
    if (clinetToken == null) {
      return true;
    }
    if (!serverToken.equals(clinetToken)) {
      return true;
    }
    return false;
  }

}

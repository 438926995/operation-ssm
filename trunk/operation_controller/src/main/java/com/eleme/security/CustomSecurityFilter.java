package com.eleme.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.eleme.bean.SessionBean;
import com.eleme.constants.GlobalConstants;
import com.eleme.service.security.ISecurityService;
import com.eleme.util.CommonUtil;
import com.eleme.util.HttpSessionUtil;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 安全过滤器.
 * 
 * @author penglau
 *
 */
public class CustomSecurityFilter extends AbstractSecurityInterceptor implements Filter {

  /**
   * 定制安全元数据来源对象(CustomSecurityMetadataSource)
   */
  private FilterInvocationSecurityMetadataSource securityMetadataSource;
  /**
   * log实例.
   */
  private static final Log log = LogFactory.getLog(CustomSecurityFilter.class);
  /**
   * 用户权限业务逻辑
   */
  private ISecurityService securityService;
  private final static String USER_KEY = "userId";

  /**
   * 获取资源元数据源
   */
  @Override
  public SecurityMetadataSource obtainSecurityMetadataSource() {
    return this.securityMetadataSource;
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    FilterInvocation fi = new FilterInvocation(request, response, chain);
    invoke(fi);
  }

  private void invoke(FilterInvocation fi) throws IOException, ServletException {
    String requestUrl = fi.getRequestUrl();
    HttpServletRequest request = fi.getRequest();
    Object userInfo = request.getSession().getAttribute("userinfo");
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) {
      logger.info("spring security context authentication为空,Userinfo置为null");
      userInfo = null;
    }
    SessionBean sb = HttpSessionUtil.buildUserInfo(userInfo);
    if (sb != null && sb.getUserId() != null && auth != null) {
      // MDC.put(USER_KEY, String.valueOf(sb.getUserId()));
      log.info("判断用户{}是否具有权限：{}", sb.getUserName(), requestUrl);
    } else if (sb != null && auth == null) {
      log.info("用户{}权限为空", sb.getUserName());
      userInfo = null;
    } else if (sb != null && sb.getUserName() == null) {
      log.info("用户Session中的数据为空");
      userInfo = null;
    } else {
      log.info("未登录用户正在请求：{}", requestUrl);
      userInfo = null;
    }
    // 如果当前登陆者是管理员,不做权限拦截
    if (sb != null && sb.isAdmin() != null && sb.isAdmin()) {
      log.info("判断用户{}是否为管理员：{}", sb.getUserName(), sb.isAdmin());
      fi.getChain().doFilter(request, fi.getResponse());
      return;
    }
    InterceptorStatusToken token = super.beforeInvocation(fi);
    if (token != null) {
      if ((null == sb) || (sb != null && sb.getUserName() == null)) {
        log.info("sessionBean is null");
        request.getSession().invalidate();
        HttpServletResponse response = fi.getResponse();
        response.sendRedirect(GlobalConstants.PROJECT_URL + "/logout");
      }
      try {
        fi.getChain().doFilter(request, fi.getResponse());
      } catch (Exception ex) {
        log.error(CommonUtil.getErrorMessage(ex));
      } finally {
        super.afterInvocation(token, null);
      }
    } else {
      if (sb != null) {
        log.info("匹配失败,用户{}没有请求[{}]的权限", sb.getUserName(), requestUrl);
      }
      throw new AccessDeniedException("您没有权限访问！");
    }
  }

  public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
    return securityMetadataSource;
  }

  public void setSecurityMetadataSource(
      FilterInvocationSecurityMetadataSource securityMetadataSource) {
    this.securityMetadataSource = securityMetadataSource;
  }

  public void init(FilterConfig arg0) throws ServletException {}

  public void destroy() {}

  @Override
  public Class<? extends Object> getSecureObjectClass() {
    // 下面的MyAccessDecisionManager的supports方面必须放回true,否则会提醒类型错误
    return FilterInvocation.class;
  }

  /**
   * @return the securityService
   */
  public ISecurityService getSecurityService() {
    return securityService;
  }

  /**
   * @param securityService the securityService to set
   */
  public void setSecurityService(ISecurityService securityService) {
    this.securityService = securityService;
  }

}

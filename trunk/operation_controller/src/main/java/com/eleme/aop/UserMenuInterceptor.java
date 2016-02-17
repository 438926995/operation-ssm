package com.eleme.aop;

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.bean.SessionBean;
import com.eleme.domain.ops.user.MenuTree;
import com.eleme.mapper.ops.user.IUserMapper;
import com.eleme.util.HttpSessionUtil;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 用户菜单拦截器，用于获取用户对应菜单信息.
 * 
 * @author zhangqiongbiao
 */
public class UserMenuInterceptor extends HandlerInterceptorAdapter {

  @Inject
  private IUserMapper userMapper;

  // log实例.
  private Log log = LogFactory.getLog(UserMenuInterceptor.class);

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    if (handler instanceof HandlerMethod) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      Method method = handlerMethod.getMethod();
      UserMenu annotation = method.getAnnotation(UserMenu.class);
      if (annotation != null) {

        Object obj = request.getSession().getAttribute("userinfo");
        SessionBean sb = HttpSessionUtil.buildUserInfo(obj);
        String curUrl = UrlUtils.buildRequestUrl(request);
        
        log.info("方法{}.{}被拦截，获取用户{}对应菜单树。", method.getDeclaringClass(), method.getName(), sb.getUserName());

        MenuTree menuTree = new MenuTree(userMapper.selectMenusByUserId(sb.getUserId()));
        menuTree.setCurMenu(curUrl);
        modelAndView.addObject("userMenuTree", menuTree);
        
        // 面包屑导航
        // <#list userMenuTree.breadcrumbNavigationList as breadcrumbNavigation>${breadcrumbNavigation}</#list>
      }
    }
    super.postHandle(request, response, handler, modelAndView);
  }
}
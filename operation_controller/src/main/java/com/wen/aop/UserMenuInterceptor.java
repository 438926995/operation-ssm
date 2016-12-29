package com.wen.aop;

import com.wen.annotation.controller.UserMenu;
import com.wen.bean.SessionBean;
import com.wen.domain.user.MenuTree;
import com.wen.mapper.user.IUserMapper;
import com.wen.util.HttpSessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 用户菜单拦截器，用于获取用户对应菜单信息.
 * 
 * @author huwenwen
 */
public class UserMenuInterceptor extends HandlerInterceptorAdapter {

  @Inject
  private IUserMapper userMapper;

  // log实例.
  private Logger log = LoggerFactory.getLogger(UserMenuInterceptor.class);

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

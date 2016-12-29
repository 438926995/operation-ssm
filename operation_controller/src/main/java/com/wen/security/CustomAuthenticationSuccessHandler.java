package com.wen.security;

import com.wen.bean.SessionBean;
import com.wen.bean.security.LoginBean;
import com.wen.service.user.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录成功后相关处理类.
 * 
 * @author huwenwen
 *
 */
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	/**
	 * 日志类
	 */
	private static final Logger log =
			LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

	@Inject
	private IUserService userService;

	/**
	 * 登录成功回调
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		User user = (User) authentication.getPrincipal();
		LoginBean lb = userService.findByUserName(user.getUsername());
		long userId = lb.getUserId();

		SessionBean sessionBean = userService.findSessionBeanByUserId(userId);
		log.info("当前登录者{}是否为管理员：{}", sessionBean.getUserName(), sessionBean.isAdmin());

		request.getSession().setAttribute("userinfo", sessionBean);
		// List<MenuBean> menuList = securityService.findAllMenuList(userId,
		// isSuperAdmin);
		// request.getSession().setAttribute("menuList", menuList);
		// request.getSession().setAttribute("devModel", Constants.DEVMODEL);

		log.info("{}用户名密码匹配,验证成功", lb.getUserName());
		super.onAuthenticationSuccess(request, response, authentication);
	}

}

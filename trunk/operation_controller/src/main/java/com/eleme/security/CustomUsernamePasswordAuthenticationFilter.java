package com.eleme.security;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eleme.bean.security.LoginBean;
import com.eleme.security.exception.BadCaptchaException;
import com.eleme.service.user.IUserService;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 用户名密码身份验证过滤器.
 * 
 * @author penglau
 *
 */
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Inject
	private IUserService userService;

	private Boolean forwardToDestination;

	@Inject
	private Md5PasswordEncoder passwordEncoder;

	@Inject
	private ImageCaptchaService imageCaptchaService;
	
	/**
	 * 日志对象
	 */
	private final static Log log = LogFactory.getLog(CustomUsernamePasswordAuthenticationFilter.class);

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("非POST提交。 ");
		}
		// 得到登陆页面的用户登陆工号和密码
		String userName = obtainUsername(request);
		String userPass = obtainPassword(request);
		log.info("用户[" + userName + "]登录");
		LoginBean lb = null;
		// 根据员工姓名查询用户对象
		lb = userService.findByUserName(userName);
		// 判断查询出来的用户对象是否为空 并且输入的密码与查询出来的密码匹配
		// System.out.println(passwordEncoder.encodePassword(userPass,
		// userName));
		if (lb == null) {
			loginFailHandle(request, userName, "用户不存在！");
			// 所查询出来的用户为空，或者密码不匹配，则抛出异常
			log.error("用户[" + userName + "]不存在！");
		} else if (!lb.getHashedUserPass().equals(passwordEncoder.encodePassword(userPass, userName))) {
			loginFailHandle(request, userName, "密码错误！");
			// 所查询出来的用户为空，或者密码不匹配，则抛出异常
			log.error("密码错误！");
		}
		String captchaId = request.getSession().getId();
		String code = request.getParameter("code");
		Boolean isCorrect = false;
		try {
			isCorrect = imageCaptchaService.validateResponseForID(captchaId, code);
		} catch (CaptchaServiceException e) {
			loginFailHandle(request, code, "请填写验证码！");
		}
		if (!isCorrect) {
			throw new BadCaptchaException(
					this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCaptcha", "Bad captcha"));
		}
		// UsernamePasswordAuthenticationToken实现 Authentication
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, userPass);
		// 允许子类设置详细属性
		setDetails(request, authRequest);
		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);

		// return super.attemptAuthentication(request, response);
	}

	/*
	 * 在我们配置的simpleUrlAuthenticationFailureHandler处理登录失败的处理类在这么一段
	 * 这样我们可以在登录失败后，向用户提供相应的信息。
	 */
	private void loginFailHandle(HttpServletRequest request, String userName, String msg) {
		AuthenticationServiceException exception = new AuthenticationServiceException(userName + "##" + msg);
		if (forwardToDestination) {
			request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
		} else {
			HttpSession session = request.getSession(false);

			if (session != null || getAllowSessionCreation()) {
				request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
			}
		}
		throw exception;
	}

	/**
	 * @return the forwardToDestination
	 */
	public Boolean getForwardToDestination() {
		return forwardToDestination;
	}

	/**
	 * @param forwardToDestination
	 *            the forwardToDestination to set
	 */
	public void setForwardToDestination(Boolean forwardToDestination) {
		this.forwardToDestination = forwardToDestination;
	}

}

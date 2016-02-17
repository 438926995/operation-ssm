package com.eleme.controller.account;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.bean.SessionBean;
import com.eleme.constants.GlobalConstants;
import com.eleme.controller.BaseController;
import com.eleme.util.CommonUtil;
import com.eleme.util.HttpSessionUtil;
import com.octo.captcha.service.image.ImageCaptchaService;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 用户帐号相关方法控制器。
 * 
 * @author penglau
 *
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseController {

	// @Inject
	// private IAccountService accountServiceImpl;
//	@Inject
//	protected ISecurityService securityServiceImpl;
	@Inject
	private ImageCaptchaService imageCaptchaService;
	/**
	 * log
	 */
	private static Log log = LogFactory.getLog(AccountController.class);

	/**
	 * 登录页面.
	 * 
	 * @param request
	 * @return 登陆页面
	 */
	@RequestMapping(value = "/login")
	@UserMenu
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView mvn = new ModelAndView("account/login");
		// 登录失败
		Object loginFail = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if (loginFail != null) {
			Exception error = (Exception) loginFail;
			if (error.getCause() != null) {
				log.error(CommonUtil.getThrowableMessage(error.getCause()));
			}
			String[] errorMsg = error.getMessage().split("##");
			if (errorMsg.length == 2) {
				mvn.addObject("userName", errorMsg[0]);
				mvn.addObject("loginError", errorMsg[1]);
			} else if (errorMsg.length == 1) {
				mvn.addObject("loginError", errorMsg[0]);
			}
		}
		return mvn;
	}

	/**
	 * 登录成功后去首页请求.
	 * 
	 * @param request
	 * @return 首页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@UserMenu
	public ModelAndView index(HttpServletRequest request) throws Exception {
		Object userInfo = request.getSession().getAttribute("userinfo");
		SessionBean sb = HttpSessionUtil.buildUserInfo(userInfo);
		ModelAndView mav = new ModelAndView("account/index");
		if (sb != null && sb.getUserId() != null) {
			try {
				// 可做相关查询
				// accountServiceImpl.xxx
				mav.addObject("username", sb.getUserName());
			} catch (Exception e) {
				log.error(CommonUtil.getErrorMessage(e));
			}
		} else {
			mav.addObject("project_url", GlobalConstants.PROJECT_URL);
			mav.setViewName("account/sessionclosed");
		}
		return mav;
	}

	/**
	 * session失效.
	 * 
	 * @param request
	 * @return 返回到登录页
	 */
	@RequestMapping(value = "/sessionclosed", method = RequestMethod.GET)
	public ModelAndView sessionClosed(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("account/sessionclosed");
		request.getSession().invalidate();
		return mav;
	}

	/**
	 * 404.
	 * 
	 * @param request
	 * @return account/notfound
	 */
	@RequestMapping(value = "/notfound", method = RequestMethod.GET)
	public ModelAndView notfound(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("account/notfound");
		return mav;
	}

	/**
	 * 500.
	 * 
	 * @param request
	 * @return account/errorpage
	 */
	@RequestMapping(value = "/errorpage", method = RequestMethod.GET)
	public ModelAndView errorpage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("account/errorpage");
		return mav;
	}

	/**
	 * 没有权限页面。
	 * 
	 * @param request
	 * @param response
	 * @return 跳转到无权限页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public ModelAndView accessDenied(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		SessionBean sb = getSessionBean(request);
		mav.addObject("project_url", GlobalConstants.PROJECT_URL);
		// session 失效
		if ((null == sb) || (sb != null && sb.getUserName() == null)) {
			log.info("sessionBean is null");
			Object loginFail = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
			if (loginFail != null) {
				Exception error = (Exception) loginFail;
				if (error.getCause() != null) {
					log.error(CommonUtil.getThrowableMessage(error.getCause()));
				}
			}
			/**
			 * 不直接进行response.sendRedirect(GlobalConstants.PROJECT_URL +
			 * "/logout");
			 */
			request.getSession().invalidate();
			mav.setViewName("account/sessionclosed");
		} else {
			log.info("sessionBean is not null,name is {},url is {}", sb.getUserName(), request.getRequestURL());
			mav.setViewName("account/accessdenied");
		}
		return mav;
	}

	/**
	 * 生成验证码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void getCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
			String captchaId = request.getSession().getId();

			BufferedImage challenge = (BufferedImage) imageCaptchaService.getChallengeForID(captchaId,
					request.getLocale());

			ImageIO.write(challenge, "jpeg", jpegOutputStream);
			byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			ServletOutputStream responseOutputStream = response.getOutputStream();
			responseOutputStream.write(captchaChallengeAsJpeg);
			responseOutputStream.flush();
			responseOutputStream.close();
		} catch (Exception e) {
			log.error(CommonUtil.getThrowableMessage(e.getCause()));
		}

	}
}

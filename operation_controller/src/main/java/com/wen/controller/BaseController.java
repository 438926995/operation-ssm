package com.wen.controller;

import com.wen.bean.SessionBean;
import com.wen.controller.bind.StringEscapeEditor;
import com.wen.util.CommonUtil;
import com.wen.util.HttpSessionUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 控制器基础类
 * 
 * @author huwenwen
 *
 */
public abstract class BaseController extends MultiActionController {
	/**
	 * 日志
	 */
	protected static final Logger log = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 获取session中的用户对象
	 * 
	 * @param request
	 * @return
	 */
	protected SessionBean getSessionBean(HttpServletRequest request) {
		Object userInfo = request.getSession().getAttribute("userinfo");
		return HttpSessionUtil.buildUserInfo(userInfo);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringEscapeEditor(false, false, false));
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormatUtils.ISO_DATE_FORMAT.getPattern());
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
		binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
	}

	/**
	 * 直接输出纯字符串
	 */
	public void renderText(HttpServletResponse response, String content) {
		try {
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			log.error(CommonUtil.getErrorMessage(e));
		}
	}

	/**
	 * 直接输出纯HTML
	 */
	public void renderHtml(HttpServletResponse response, String content) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(content);
		} catch (IOException e) {
			log.error(CommonUtil.getErrorMessage(e));
		}
	}

	/**
	 * 直接输出纯XML
	 */
	public void renderXML(HttpServletResponse response, String content) {
		try {
			response.setContentType("text/xml;charset=UTF-8");
			response.getWriter().write(content);
		} catch (IOException e) {
			log.error(CommonUtil.getErrorMessage(e));
		}
	}

	/**
	 * 直接输出json
	 */
	public void renderJson(HttpServletResponse response, String content) {
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(content);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

}

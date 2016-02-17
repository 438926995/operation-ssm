package com.eleme.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.eleme.bean.JSONMessage;
import com.eleme.util.CommonUtil;
import com.eleme.util.JsonUtil;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 自定义spring全局异常处理.
 * 
 * @author penglau
 *
 */
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

	private static final Log log = LogFactory.getLog(SimpleMappingExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (request.getHeader("accept").contains("application/json") || (request.getHeader("X-Requested-With") != null
				&& request.getHeader("X-Requested-With").contains("XMLHttpRequest"))) {
			// @ResponseBody，返回json时进入此处
			response.setContentType("application/json;charset=utf-8");
			JSONMessage jsonMes = new JSONMessage(false, CommonUtil.getErrorDescription(ex));
			String jsonErrorString = JsonUtil.toJson(jsonMes);
			// jquery ajax得到的，实际上是
			// {"readyState":0,"status":0,"statusText":"error"} 的形式
			try {
				PrintWriter writer = response.getWriter();
				writer.write(jsonErrorString);
				writer.flush();
			} catch (IOException e) {
				log.error(CommonUtil.getErrorMessage(e));
			}
			return null;
		} else {
			// 记录日志
			log.error(CommonUtil.getErrorMessage(ex));
			// 跳转到配置的spring mvc异常页面
			String viewName = determineViewName(ex, request);
			if (viewName != null) {
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else {
				return null; // 未获得spring mvc异常配置页面,交由web.xml error-page处理
			}
		}
	}

}

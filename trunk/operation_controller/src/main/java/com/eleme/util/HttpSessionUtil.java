package com.eleme.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.eleme.bean.SessionBean;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 对象转换成sessionbean工具类.
 * 
 * @author penglau
 *
 */
public class HttpSessionUtil {

	private static Log log = LogFactory.getLog(HttpSessionUtil.class);

	private HttpSessionUtil() {
	}

	/**
	 * 根据userInfo类型不同，通过不同的方式返回sessionBean对象。
	 * 
	 * @param userInfo
	 *            userinfo对象
	 * @return sessionbean
	 */
	public static SessionBean buildUserInfo(Object userInfo) {
		if (userInfo instanceof SessionBean) {
			log.debug("userinfo为SessionBean对象");
			return (SessionBean) userInfo;
		} else if (userInfo instanceof LinkedHashMap) {
			log.debug("SessionBean为LinkedHashMap，Map转换为SessionBean");
			return buildUserInfoBean((LinkedHashMap<String, Object>) userInfo);
		} else {
			if (userInfo == null) {
				log.warn("userInfo为null，返回null");
				return null;
			}
			log.warn("userInfo为其他类型,新建sessionbean。" + userInfo.getClass().getName());
			return new SessionBean();
		}
	}

	/**
	 * 根据redis存储的userInfoMap对象，构建出sessionbean对象。
	 * 
	 * @param userInfoMap
	 * @return
	 */
	private static SessionBean buildUserInfoBean(Map<String, Object> userInfoMap) {
		SessionBean result = new SessionBean();

		Object userId = userInfoMap.get("userId");
		result.setUserId(Long.valueOf(String.valueOf(userId == null ? "" : userId)));

		Object userName = userInfoMap.get("userName");
		result.setUserName(String.valueOf(userName == null ? "" : userName));

		Object isAdmin = userInfoMap.get("isAdmin");
		result.setAdmin(isAdmin == null ? false : Boolean.parseBoolean(String.valueOf(isAdmin)));

		// result.setAdmin(false);

		return result;
	}

}

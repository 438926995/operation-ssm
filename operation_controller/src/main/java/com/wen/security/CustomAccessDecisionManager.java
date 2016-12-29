package com.wen.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * 访问权限管理器.
 * 
 * @author huwenwen
 *
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {

	/**
	 * log实例
	 */
	private static final Logger log = LoggerFactory.getLogger(CustomAccessDecisionManager.class);

	/**
	 * 匹配用户所对应的权限
	 */
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}
		// 所请求的资源拥有的权限(一个资源对多个权限)
		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		Collection<? extends GrantedAuthority> authentications = authentication.getAuthorities();
		while (ite.hasNext()) {
			// 访问所请求资源所需要的权限 (needPermission ?? )
			String authId = ((SecurityConfig) ite.next()).getAttribute();
			// 遍历权限集合并判断用户该请求的权限是否与用户所拥有的权限匹配
			for (GrantedAuthority ga : authentications) {
				if (authId.equals(ga.getAuthority())) { // ga is user's role.
					return;
				}
			}
		}
		throw new AccessDeniedException("您没有权限访问！");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

}

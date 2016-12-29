package com.wen.security;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.wen.service.security.ISecurityService;

/**
 * 用户登录验证业务逻辑实现类.
 * 
 * @author huwenwen
 *
 */
public class CustomUserDetailService implements UserDetailsService {
  /**
   * 权限业务逻辑
   */
  @Inject
  private ISecurityService securityService;

  /**
   * 登录验证，并将用户信息[包括用户所拥有的权限]封装成spring security 的User对象
   */
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return securityService.loadUserByUsername(username);
  }

}

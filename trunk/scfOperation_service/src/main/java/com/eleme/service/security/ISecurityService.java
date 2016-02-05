package com.eleme.service.security;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.eleme.bean.security.AuthoritiesBean;
import com.eleme.bean.security.RolesBean;

/**
 * 权限相关业务接口.
 * 
 * @author penglau
 *
 */
public interface ISecurityService {

  /**
   * 获取所有权限
   * 
   * @return 资源集合
   */
  List<AuthoritiesBean> findAllAuthorities();

  /**
   * 根据用户名查询用户信息
   * 
   * @param userId
   * @return 用户权限信息
   */
  List<RolesBean> findUsersSecurityByUserId(long userId);

  /**
   * 根据用户姓名，查找数据并封装成spring security的User对象
   * 
   * @param username 用户姓名
   * @return
   * @throws UsernameNotFoundException
   */
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

  /**
   * 根据角色列表取得用户的权限
   * 
   * @param roleList 角色列表
   * @return
   */
  public Set<GrantedAuthority> obtionGrantedAuthorities(List<RolesBean> roleList);

}

package com.eleme.dao.security;

import java.util.List;
import java.util.Map;

/**
 * 权限控制dao接口。
 * 
 * @author huwenwen
 *
 */
public interface IUserSecurityDao {

  /**
   * 查询所有权限集合
   * 
   * @return 权限封装集合
   */
  List<Map<String, Object>> findAllAuthorities();

  /**
   * 根据用户名查询用户信息
   * 
   * @param userId 用户id
   * @param isAdmin 是否为管理员
   * @return 用户权限信息
   */
  List<Map<String, Object>> findUsersSecurityByUserId(long userId, boolean iAdmin);

}

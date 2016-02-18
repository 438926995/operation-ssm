package com.eleme.dao.user;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.eleme.bean.SessionBean;
import com.eleme.bean.security.LoginBean;
import com.eleme.bean.user.MUsers;
import com.eleme.bean.user.UserQueryBean;

/**
 * 用户相关dao层方法.
 * 
 * @author huwenwen
 *
 */
public interface IUserDao {
  /**
   * 根据userId查询sessionbean
   * 
   * @param userId 用户id
   * @return SessionBean，会有null的情况。
   */
  SessionBean findSessionBeanByUserId(long userId);

  /**
   * 用户列表查询。
   * 
   * @param currentPage 当前页。
   * @param uqb 用户列表查询参数封装bean。
   * @return 列表数据集合
   */
  public List<Map<String, Object>> userList(Integer currentPage, UserQueryBean uqb)
      throws SQLException, ParseException;


  /**
   * 符合查询条件的用户条目数。
   * 
   * @param uqb 用户列表查询参数封装bean。
   * @return 总条目数
   */
  public Integer userListCount(UserQueryBean uqb) throws SQLException;

  /**
   * 根据用户名查找用户记录.
   * 
   * @param username 用户名
   * @return 根据username查找的记录，如果找不到，返回null.
   * @throws Exception
   */
  public Map<String, Object> findUserByName(String username) throws Exception;

  /**
   * 根据用户名获取登录信息
   * 
   * @param userName 用户名
   * @return 登录信息封装类
   */
  LoginBean findUserByUserName(String userName);

  /**
   * 保存用户。
   * 
   * @param mu 用户表对应的实体对象（对象中所有field数据都已存在，user_status、create_date例外）。
   * @return
   */
  public int userAdd(MUsers mu) throws SQLException;

  /**
   * 根据userId查询密码。
   * 
   * @param userId
   * @return 用户密码
   */
  String findPwdByUserId(long userId);

}

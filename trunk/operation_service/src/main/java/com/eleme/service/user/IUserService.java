package com.eleme.service.user;

import java.sql.SQLException;
import java.util.List;

import com.eleme.bean.SessionBean;
import com.eleme.bean.security.LoginBean;
import com.eleme.bean.user.UserQueryBean;
import com.eleme.domain.ops.UserRole;
import com.eleme.util.pager.TbData;

public interface IUserService {

  /**
   * 系统用户列表.
   * 
   * @param currentPage 当前页。
   * @param uqb 用户列表查询条件封装bean
   * @return 用户列表的TbData封装类.
   * @throws Exception
   */
  public TbData userList(Integer currentPage, UserQueryBean uqb) throws Exception;

  /**
   * 根据用户名判断是否已存在同名user记录
   * 
   * @param username 用户名
   * @return true(null，根据username查找无结果) 唯一，可用；false 不唯一，不可用.
   */
  public boolean judgeIfUserNameSingle(String username) throws Exception;

  /**
   * 用户添加.
   * 
   * @param mu 用户表对应的实体对象。（暂只考虑添加用户form，user_name、pswd、is_admin已不为空)
   * @return 影响行数.
   */
  public int userAdd(UserRole ur) throws SQLException;

  /**
   * 根据userId获得的密码
   * 
   * @param userNo
   * @return
   */
  String findPwdByUserId(long userId);

  /**
   * 根据用户名查询用户登录信息
   * 
   * @param userName 用户名
   * @return 用户登录信息封装类
   */
  LoginBean findByUserName(String userName);

  /**
   * 根据登录用户ID查出SessionBean中的内容
   * 
   * @param userId 登录用户id
   * @return SessionBean对象
   */
  SessionBean findSessionBeanByUserId(long userId);

  /**
   * 根据userId判断是否为超级管理员
   * 
   * @param userId 用户ID.
   * @return
   */
  boolean isAdminByUserId(long userId);

  /**
   * 验证密码输入是否正确
   * 
   * @param userId
   * @param pwd
   * @return
   * @throws Exception
   */
  public boolean judgePwd(long userId, String pwd) throws Exception;

  /**
   * 修改用户信息
   * 
   * @param mu
   * @return
   * @throws SQLException
   */
  public int userEdit(com.eleme.domain.ops.MUsers mu) throws SQLException;

  /**
   * 根据用户Id查询用户信息
   */
  public UserRole getUserInfoById(Integer userId);

  /**
   * update用户和用户角色关联表
   */
  public int updateUserAndRole(UserRole ur);

  /**
   * 删除用户
   */
  public int updateUser(Integer id);
  /**
   * 
   * @param roleId
   * @return
   */
  public List<UserRole> getUserInfoByRoleId(Integer roleId);
  /**
   * 删除用户对应的角色
   */
  public int delUserAndRole(UserRole ur);

}

package com.eleme.mapper.ops.user;

import java.util.List;

import com.eleme.domain.ops.MUsers;
import com.eleme.domain.ops.UserRole;
import com.eleme.domain.ops.user.Resource;

/**
 * 系统用户基础信息接口
 * 
 * @author huwenwen
 *
 */
public interface IUserMapper {

  /**
   * 根据用户ID获取用户对应菜单
   * 
   * @param userId
   * @return
   */
  public List<Resource> selectMenusByUserId(Long userId);

  /**
   * 新增系统用户基础信息
   * 
   * @param record
   * @return
   */
  int insert(MUsers record);

  /**
   * 根据主键用户ID查询系统用户基础信息
   * 
   * @param userId
   * @return
   */
  MUsers selectById(Integer userId);

  /**
   * 根据用户ID修改系统用户基础信息
   * 
   * @param record
   * @return
   */
  int updateById(MUsers record);

  /**
   * 增加用户
   */
  int insertUserAndRole(UserRole userRoleBean);

  /**
   * 根据用户id查询用户信息
   */
  UserRole userInfo(Integer userId);

  /**
   * 更新用户和角色关联表
   * 
   * @param ur
   * @return
   */
  int updateUserAndRole(UserRole ur);

  /**
   * 查询用户和角色表中是否存在用户ID
   */
  int isUserIdExist(Integer userId);

  /**
   * 删除用户信息，其实就是更行用户，让其状态变为冻结
   */
  int updateUserOfDel(Integer id);

  /**
   * 根据角色ID查用户信息
   * 
   * @param roleId
   * @return
   */
  List<UserRole> selectUserByRoleId(Integer roleId);

  /**
   * 根据角色ID和用户ID删除该用户的该角色
   * 
   * @param ur
   * @return
   */
  int deleteUserAndRole(UserRole ur);

}

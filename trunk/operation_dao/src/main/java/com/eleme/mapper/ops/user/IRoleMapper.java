package com.eleme.mapper.ops.user;

import java.util.List;
import java.util.Map;

import com.eleme.bean.security.RolesBean;
import com.eleme.bean.user.RoleBean;

/**
 * 角色操作接口
 * 
 * @author huwenwen
 *
 */
public interface IRoleMapper {
  /**
   * 查询角色 分页
   */
  public List<RolesBean> getRolesPageList(Map<String, Object> map);
  /**
   * 查询角色 不分页
   */
  public List<RolesBean> getRolesList();
  /**
   * 查询角色总记录
   */
  public int selectCount();
  /**
   * 根据角色名查找
   */
  public int findRoleByName(String roleName);
  /**
   * 添加角色
   */
  public int insertRole(RoleBean rab);
  /**
   * 添加角色权限关联
   */
  public int insertRoleAndAuth(RoleBean rab);
  /**
   * 根据角色Id查询角色信息
   */
  public List<RoleBean> selectRoleById(Integer id); 
  /**
   * 根据角色Id删除角色权限
   */
  public int deleteAuth(Integer roleId);
  /**
   * update 角色
   */
  public int updateRole(RoleBean rab);
  /**
   * 根据角色Id查询是否有对应的权限
   */
  public int isAuthById(Integer roleId);
  /**
   * 根据角色Id删除角色
   */
  public int deleteRole(Integer roleId);
  /**
   * 根据角色Id删除角色对应的权限
   */
  public int deleteRoleAndAuth(Integer roleId);
}

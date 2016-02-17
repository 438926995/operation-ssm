package com.eleme.service.user;

import java.util.List;

import com.eleme.bean.security.RolesBean;
import com.eleme.bean.user.RoleBean;
import com.eleme.util.pager.TbData;

public interface IRoleService {
  /**
   * 得到角色的集合 分页
   */
  public TbData rolesPageList(Integer currentPage);
  /**
   * 得到角色的集合 分页
   */
  public List<RolesBean> rolesList();
  /**
   * 根据角色名判断是否已存在同名的记录
   */
  public boolean judgeIfRoleNameSingle(String roleName);
  /**
   * 添加角色和角色权限关联表
   * @param rad
   * @return
   */
  public int insertRole(RoleBean rab);
  /**
   * 根据角色Id获得角色信息集合
   */
  public List<RoleBean> getRoleInfoById(Integer id);
  /**
   * 编辑角色和角色权限关联表
   */
  public int updateRole(RoleBean rab);
  /**
   * 根据角色Id删除角色信息
   */
  public int deleteRole(Integer roleId);
}
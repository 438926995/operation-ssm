package com.eleme.service.user.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.eleme.bean.security.RolesBean;
import com.eleme.bean.user.RoleBean;
import com.eleme.constants.PagerConstants;
import com.eleme.mapper.ops.user.IRoleMapper;
import com.eleme.service.BaseService;
import com.eleme.service.user.IRoleService;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

@Service
public class RoleServiceImpl extends BaseService implements IRoleService {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(RoleServiceImpl.class);

  @Inject
  private IRoleMapper roleMapper;
  
  @Override
  public TbData rolesPageList(Integer currentPage) {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    // 封装sql查询条件
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("startRecord", (currentPage - 1) * PagerConstants.PAGE_SIZE);
    map.put("pageSize", PagerConstants.PAGE_SIZE);
    List<RolesBean> rolesList = roleMapper.getRolesPageList(map);
    int totalCount = roleMapper.selectCount();
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, rolesList);
  }
  
  @Override
  public List<RolesBean> rolesList(){
    return roleMapper.getRolesList();
  }

  @Override
  public boolean judgeIfRoleNameSingle(String roleName) {
    int line = roleMapper.findRoleByName(roleName);
    return line == 0;
  }

  @Override
  public int insertRole(RoleBean rab) {
    //添加角色
    int lines = roleMapper.insertRole(rab);
    //添加角色权限关联表
    //判断:是否选取了权限
    if(rab.getAuthList() == null) {
      return lines;
    } else {
      lines = roleMapper.insertRoleAndAuth(rab);
    }
    return lines;
  }

  @Override
  public List<RoleBean> getRoleInfoById(Integer id) {
    return roleMapper.selectRoleById(id);
  }

  @Override
  public int updateRole(RoleBean rab) {
    // 更行角色
    rab.setUpdateAt(new Date());
    int lines = roleMapper.updateRole(rab);
    // 删除之前看对应的roleId有没有数据
    int authCount = roleMapper.isAuthById(rab.getRoleId());
    if (authCount > 0) {
      // 更行之前删除roleId对应的权限
      roleMapper.deleteAuth(rab.getRoleId());
    }
    //判断:是否选取了权限
    if(rab.getAuthList() == null) {
      return lines;
    } else {
      lines = roleMapper.insertRoleAndAuth(rab);
    }
    return lines;
  }

  @Override
  public int deleteRole(Integer roleId) {
    // 删除角色对应权限
    roleMapper.deleteRoleAndAuth(roleId);
    // 删除对应角色
    int lines = roleMapper.deleteRole(roleId);
    return lines;
  }
}

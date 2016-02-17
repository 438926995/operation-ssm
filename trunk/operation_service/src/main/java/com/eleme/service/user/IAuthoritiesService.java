package com.eleme.service.user;

import java.util.List;

import com.eleme.domain.ops.user.AuthoritieBean;
import com.eleme.domain.ops.user.Authorities;
import com.eleme.domain.ops.user.AuthoritiesModule;
import com.eleme.domain.ops.user.AuthoritiesQueryBean;
import com.eleme.domain.ops.user.Resource;
import com.eleme.util.pager.TbData;

/**
 * 权限管理接口
 * 
 * @author zhangqiongbiao
 */
public interface IAuthoritiesService {

  /**
   * 查询权限列表
   * 
   * @param currentPage
   * @param aqb
   * @return
   * @throws Exception
   */
  public TbData queryAuthoritiesTbData(Integer currentPage, AuthoritiesQueryBean aqb)
      throws Exception;

  /**
   * 查询权限模块列表信息
   * 
   * @param currentPage
   * @param aqb
   * @return
   * @throws Exception
   */
  public TbData queryAuthoritiesModuleTbData(Integer currentPage, AuthoritiesQueryBean aqb)
      throws Exception;


  /**
   * 查询所有权限模块信息
   * 
   * @return
   * @throws Exception
   */
  public List<AuthoritiesModule> queryAuthoritiesModuleListAll() throws Exception;

  /**
   * 根据ID查询权限对象
   * 
   * @param authoritiesId
   * @return
   * @throws Exception
   */
  public Authorities queryAuthoritiesById(Integer authoritiesId) throws Exception;

  /**
   * 根据ID查询权限模块对象
   * 
   * @param authModuleId
   * @return
   * @throws Exception
   */
  public AuthoritiesModule queryAuthoritiesModuleById(Integer authModuleId) throws Exception;

  /**
   * 保存一条权限记录
   * 
   * @param authorities
   * @return
   * @throws Exception
   */
  public int saveAuthorities(Authorities authorities) throws Exception;

  /**
   * 保存一条权限模块记录
   * 
   * @param authoritiesModule
   * @return
   * @throws Exception
   */
  public int saveAuthoritiesModule(AuthoritiesModule authoritiesModule) throws Exception;

  /**
   * 判断权限名是否唯一
   * 
   * @param authName
   * @return
   */
  public boolean judgeIfAuthNameSingle(String authName);

  /**
   * 判断权限名模块是否唯一
   * 
   * @param authName
   * @return
   */
  public boolean judgeIfAuthNameSingle2(String moduleName);

  /**
   * 获得权限的集合
   */
  public List<AuthoritieBean> authList();
  /**
   * 判断资源对应的url是否唯一
   * @param authId
   * @return
   */
  public List<Resource> judgeUrlSingle(Integer resourceId);
}

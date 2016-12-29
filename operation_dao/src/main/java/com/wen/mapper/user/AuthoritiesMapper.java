package com.wen.mapper.user;

import java.util.List;

import com.wen.domain.user.AuthoritieBean;
import com.wen.domain.user.Authorities;
import com.wen.domain.user.AuthoritiesModule;
import com.wen.domain.user.AuthoritiesQueryBean;
import com.wen.domain.user.Resource;

/**
 * 权限相关
 * 
 * @author huwenwen
 */
public interface AuthoritiesMapper {

  /**
   * 查询权限列表数量
   * 
   * @param aqb
   * @return
   */
  int countAuthoritiesList(AuthoritiesQueryBean aqb);

  /**
   * 查询权限列表信息
   * 
   * @param aqb
   * @return
   */
  List<Authorities> queryAuthoritiesList(AuthoritiesQueryBean aqb);


  /**
   * 根据ID查询权限信息
   * 
   * @param authoritiesId
   * @return
   */
  Authorities queryAuthoritiesById(Integer authoritiesId);

  /**
   * 根据权限ID查询所有对应的资源
   * 
   * @param authoritiesId
   * @return
   */
  List<Integer> queryResourceIdsByAuthId(Integer authoritiesId);

  /**
   * 新增一条权限记录
   * 
   * @param authorities
   * @return
   */
  int insertAuthorities(Authorities authorities);

  /**
   *  更新一条权限记录
   * 
   * @param authorities
   * @return
   */
  int updateAuthoritiesById(Authorities authorities);

  /**
   * 保存权限资源关联关系
   * 
   * @param authorities
   * @return
   */
  int insertResourceAuthorities(Authorities authorities);

  /**
   * 根据权限ID删除对应资源信息
   * 
   * @param authoritiesId
   * @return
   */
  int deleteResourceAuthoritiesByAuthoritiesId(Integer authoritiesId);

  /**
   * 查询权限列表数量
   * 
   * @param aqb
   * @return
   */
  int countAuthoritiesModuleList(AuthoritiesQueryBean aqb);

  /**
   * 查询权限列表信息
   * 
   * @param aqb
   * @return
   */
  List<AuthoritiesModule> queryAuthoritiesModuleList(AuthoritiesQueryBean aqb);

  /**
   * 根据ID查询权限模块信息
   * 
   * @param authoritiesModuleId
   * @return
   */
  AuthoritiesModule queryAuthoritiesModuleById(Integer authoritiesModuleId);

  /**
   * 新增一条权限模块记录
   * 
   * @param authoritiesModule
   * @return
   */
  int insertAuthoritiesModule(AuthoritiesModule authoritiesModule);

  /**
   * 更新一条权限模块记录
   * 
   * @param authoritiesModule
   * @return
   */
  int updateAuthoritiesModuleById(AuthoritiesModule authoritiesModule);

  /**
   * 判断权限名是否唯一
   * 
   * @param authName
   * @return
   */
  int findAuthByName(String authName);

  /**
   * 判断权限模块名是否唯一
   * 
   * @param authName
   * @return
   */
  int findModuleByName(String moduleName);

  /**
   * 获得权限的集合
   */
  List<AuthoritieBean> getAuthoritieList();
  /**
   * 根据对应的资源ID找URL
   * @param resourceId
   * @return
   */
  List<Resource> findUrlById(Integer resourceId);
}

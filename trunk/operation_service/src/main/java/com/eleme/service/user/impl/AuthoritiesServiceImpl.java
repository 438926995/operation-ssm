package com.eleme.service.user.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eleme.constants.PagerConstants;
import com.eleme.domain.ops.user.AuthoritieBean;
import com.eleme.domain.ops.user.Authorities;
import com.eleme.domain.ops.user.AuthoritiesModule;
import com.eleme.domain.ops.user.AuthoritiesQueryBean;
import com.eleme.domain.ops.user.Resource;
import com.eleme.mapper.ops.user.AuthoritiesMapper;
import com.eleme.service.BaseService;
import com.eleme.service.user.IAuthoritiesService;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 权限管理实现类
 * 
 * @author huwenwen
 */
@Service
public class AuthoritiesServiceImpl extends BaseService implements IAuthoritiesService {

  private static Log log = LogFactory.getLog(AuthoritiesServiceImpl.class);

  @Inject
  private AuthoritiesMapper authoritiesMapper;

  @Transactional(readOnly = true)
  public TbData queryAuthoritiesTbData(Integer currentPage, AuthoritiesQueryBean aqb) throws Exception {
    currentPage = (currentPage == null) ? 1 : currentPage;
    aqb.setOffset((currentPage - 1) * PagerConstants.PAGE_SIZE);
    aqb.setLimit(PagerConstants.PAGE_SIZE);

    int totalCount = authoritiesMapper.countAuthoritiesList(aqb);
    List<Authorities> list = authoritiesMapper.queryAuthoritiesList(aqb);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, list);
  }

  @Transactional(readOnly = true)
  public TbData queryAuthoritiesModuleTbData(Integer currentPage, AuthoritiesQueryBean aqb) throws Exception {
    currentPage = (currentPage == null) ? 1 : currentPage;
    aqb.setOffset((currentPage - 1) * PagerConstants.PAGE_SIZE);
    aqb.setLimit(PagerConstants.PAGE_SIZE);

    int totalCount = authoritiesMapper.countAuthoritiesModuleList(aqb);
    List<AuthoritiesModule> list = authoritiesMapper.queryAuthoritiesModuleList(aqb);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, list);
  }

  @Transactional(readOnly = true)
  public List<AuthoritiesModule> queryAuthoritiesModuleListAll() throws Exception {
    return authoritiesMapper.queryAuthoritiesModuleList(AuthoritiesQueryBean.EMPTY);
  }

  @Transactional(readOnly = true)
  public Authorities queryAuthoritiesById(Integer authoritiesId) throws Exception {
    Authorities authorities = authoritiesMapper.queryAuthoritiesById(authoritiesId);
    authorities.setResourcesIds(authoritiesMapper.queryResourceIdsByAuthId(authoritiesId).toArray(ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY));
    return authorities;
  }

  @Transactional(readOnly = true)
  public AuthoritiesModule queryAuthoritiesModuleById(Integer classId) throws Exception {
    return authoritiesMapper.queryAuthoritiesModuleById(classId);
  }

  @Transactional(rollbackFor = Throwable.class)
  public int saveAuthorities(Authorities authorities) throws Exception {
    int line = 0;
    authorities.setUpdatedAt(new Date());
    if (authorities.getAuthoritiesId() == null) {
      authorities.setCreatedAt(new Date());
      line = authoritiesMapper.insertAuthorities(authorities);
    } else {
      line = authoritiesMapper.updateAuthoritiesById(authorities);
      authoritiesMapper.deleteResourceAuthoritiesByAuthoritiesId(authorities.getAuthoritiesId());
    }
    if (ArrayUtils.isNotEmpty(authorities.getResourcesIds())) {
      authoritiesMapper.insertResourceAuthorities(authorities);
    }
    return line;
  }

  @Transactional(rollbackFor = Throwable.class)
  public int saveAuthoritiesModule(AuthoritiesModule authoritiesModule) throws Exception {
    int line = 0;
    authoritiesModule.setUpdatedAt(new Date());
    if (authoritiesModule.getAuthModuleId() == null) {
      authoritiesModule.setCreatedAt(new Date());
      line = authoritiesMapper.insertAuthoritiesModule(authoritiesModule);
    } else {
      line = authoritiesMapper.updateAuthoritiesModuleById(authoritiesModule);
    }
    return line;
  }

  @Override
  public boolean judgeIfAuthNameSingle(String authName) {
    
    return authoritiesMapper.findAuthByName(authName) == 0;
  }

  @Override
  public boolean judgeIfAuthNameSingle2(String moduleName) {
    return authoritiesMapper.findModuleByName(moduleName) == 0;
  }
  
  @Override
  public List<AuthoritieBean> authList() {
    return authoritiesMapper.getAuthoritieList();
  }

  @Override
  public List<Resource> judgeUrlSingle(Integer resourceId) {
    return authoritiesMapper.findUrlById(resourceId);
  }
}

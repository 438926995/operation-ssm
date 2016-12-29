package com.wen.service.user.impl;

import com.wen.constants.PagerConstants;
import com.wen.domain.user.AuthoritieBean;
import com.wen.domain.user.Authorities;
import com.wen.domain.user.AuthoritiesModule;
import com.wen.domain.user.AuthoritiesQueryBean;
import com.wen.domain.user.Resource;
import com.wen.mapper.user.AuthoritiesMapper;
import com.wen.service.BaseService;
import com.wen.service.user.IAuthoritiesService;
import com.wen.util.pager.TbData;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * 权限管理实现类
 * 
 * @author huwenwen
 */
@Service
public class AuthoritiesServiceImpl extends BaseService implements IAuthoritiesService {

  private static Logger log = LoggerFactory.getLogger(AuthoritiesServiceImpl.class);

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

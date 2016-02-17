package com.eleme.service.user.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eleme.constants.PagerConstants;
import com.eleme.domain.ops.user.MenuTree;
import com.eleme.domain.ops.user.Resource;
import com.eleme.domain.ops.user.ResourceQueryBean;
import com.eleme.mapper.ops.user.ResourceMapper;
import com.eleme.service.BaseService;
import com.eleme.service.user.IResourceService;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 资源管理实现类
 * 
 * @author zhangqiongbiao
 *
 */
@Service
public class ResourceServiceImpl extends BaseService implements IResourceService {

  private static Log log = LogFactory.getLog(ResourceServiceImpl.class);

  @Inject
  private ResourceMapper resourceMapper;
  
  @Transactional(readOnly = true)
  public MenuTree queryMenuTree() throws Exception {
    ResourceQueryBean rqb = ResourceQueryBean.EMPTY;
    rqb.setIsEnabled("1");
    List<Resource> resources = resourceMapper.queryResourceList(rqb);
    return new MenuTree(resources);
  }

  @Transactional(readOnly = true)
  public TbData queryResourceTbData(Integer currentPage, ResourceQueryBean rqb) throws Exception {
    currentPage = (currentPage == null) ? 1 : currentPage;
    rqb.setOffset((currentPage - 1) * PagerConstants.PAGE_SIZE);
    rqb.setLimit(PagerConstants.PAGE_SIZE);

    int totalCount = resourceMapper.countResourceList(rqb);
    List<Resource> list = resourceMapper.queryResourceList(rqb);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, list);
  }

  @Transactional(readOnly = true)
  public List<Resource> queryResourceListByParentId(Integer parentId) throws Exception {
    ResourceQueryBean rqb = new ResourceQueryBean(0, Integer.MAX_VALUE);
    rqb.setParentId(parentId);
    return resourceMapper.queryResourceList(rqb);
  }

  @Transactional(readOnly = true)
  public Resource queryResourceById(Integer resourceId) throws Exception {
    Resource resource = resourceMapper.queryResourceById(resourceId);
    
    List<Integer> parentIds = new ArrayList<Integer>();
    Integer parentId = resource.getParentId();
    while (parentId.intValue() != 0) {
      parentIds.add(parentId);
      parentId = resourceMapper.queryParentIdById(parentId);
    }
    Collections.reverse(parentIds);
    resource.setParentIds(parentIds.toArray(ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY));
    return resource;
  }

  @Transactional(rollbackFor = Throwable.class)
  public int saveResource(Resource resource) throws Exception {
    int line = 0;
    resource.setUpdatedAt(new Date());
    if (resource.getResourceId() == null) {
      resource.setCreatedAt(new Date());
      log.info("添加资源，插入操作");
      line = resourceMapper.insertResource(resource);
    } else {
      log.info("更新资源，更新操作，id:{}",resource.getResourceId());
      line = resourceMapper.updateResourceById(resource);
    }
    return line;
  }

}

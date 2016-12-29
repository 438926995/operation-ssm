package com.wen.service.user.impl;

import com.wen.constants.PagerConstants;
import com.wen.domain.user.MenuTree;
import com.wen.domain.user.Resource;
import com.wen.domain.user.ResourceQueryBean;
import com.wen.mapper.user.ResourceMapper;
import com.wen.service.BaseService;
import com.wen.service.user.IResourceService;
import com.wen.util.pager.TbData;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 资源管理实现类
 * 
 * @author huwenwen
 *
 */
@Service
public class ResourceServiceImpl extends BaseService implements IResourceService {

  private static Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

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

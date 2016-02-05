package com.eleme.service.user;

import java.util.List;

import com.eleme.domain.ops.user.MenuTree;
import com.eleme.domain.ops.user.Resource;
import com.eleme.domain.ops.user.ResourceClassName;
import com.eleme.domain.ops.user.ResourceQueryBean;
import com.eleme.util.pager.TbData;

/**
 * 资源管理接口
 * 
 * @author zhangqiongbiao
 */
public interface IResourceService {

  /**
   * 查询所有有效的资源列表（树结构）
   * 
   * @return
   * @throws Exception
   */
  public MenuTree queryMenuTree() throws Exception;

  /**
   * 查询资源列表
   * 
   * @param currentPage
   * @param rqb
   * @return
   * @throws Exception
   */
  public TbData queryResourceTbData(Integer currentPage, ResourceQueryBean rqb) throws Exception;

  /**
   * 查询资源组列表信息
   * 
   * @param currentPage
   * @param rqb
   * @return
   * @throws Exception
   */
  public TbData queryResourceClassTbData(Integer currentPage, ResourceQueryBean rqb)
      throws Exception;

  /**
   * 根据parentId查询资源列表
   * 
   * @param parentId
   * @return
   * @throws Exception
   */
  public List<Resource> queryResourceListByParentId(Integer parentId) throws Exception;

  /**
   * 查询所有资源组信息
   * 
   * @return
   * @throws Exception
   */
  public List<ResourceClassName> queryResourceClassListAll() throws Exception;

  /**
   * 根据ID查询资源对象
   * 
   * @param resourceId
   * @return
   * @throws Exception
   */
  public Resource queryResourceById(Integer resourceId) throws Exception;

  /**
   * 根据ID查询资源组对象
   * 
   * @param classId
   * @return
   * @throws Exception
   */
  public ResourceClassName queryResourceClassById(Integer classId) throws Exception;

  /**
   * 保存一条资源记录
   * 
   * @param resource
   * @return
   * @throws Exception
   */
  public int saveResource(Resource resource) throws Exception;

  /**
   * 保存一条资源组记录
   * 
   * @param resourceClassName
   * @return
   * @throws Exception
   */
  public int saveResourceClass(ResourceClassName resourceClassName) throws Exception;

  /**
   * 判断资源组名是否唯一
   * 
   * @param className
   * @return
   */
  public boolean judgeIfClassNameSingle(String className);

}

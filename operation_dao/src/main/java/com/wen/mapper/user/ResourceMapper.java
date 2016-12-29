package com.wen.mapper.user;

import java.util.List;

import com.wen.domain.user.Resource;
import com.wen.domain.user.ResourceQueryBean;

/**
 * 资源相关处理
 * 
 * @author huwenwen
 */
public interface ResourceMapper {

  /**
   * 查询资源列表数量
   * 
   * @param rqb
   * @return
   */
  int countResourceList(ResourceQueryBean rqb);

  /**
   * 查询资源列表信息
   * 
   * @param rqb
   * @return
   */
  List<Resource> queryResourceList(ResourceQueryBean rqb);

  /**
   * 根据ID删除一条资源纪录
   * 
   * @param resourceId
   * @return
   */
  int deleteResourceById(Integer resourceId);

  /**
   * 新增一条资源纪录
   * 
   * @param resource
   * @return
   */
  int insertResource(Resource resource);

  /**
   * 根据ID查询一条资源纪录
   * 
   * @param resourceId
   * @return
   */
  Resource queryResourceById(Integer resourceId);

  /**
   * 查询指定菜单的父ID
   * 
   * @param resourceId
   * @return
   */
  Integer queryParentIdById(Integer resourceId);

  /**
   * 更新一条资源纪录
   * 
   * @param resource
   * @return
   */
  int updateResourceById(Resource resource);


  /**
   * 查询资源组列表数量
   * 
   * @param rqb
   * @return
   */
  int countResourceClassList(ResourceQueryBean rqb);
  
}

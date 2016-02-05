package com.eleme.domain.ops.user;

import java.io.Serializable;

/**
 * 资源信息查询
 * 
 * @author zhangqiongbiao
 *
 */
public class ResourceQueryBean implements Serializable {
  private static final long serialVersionUID = 6762737615029921652L;

  public static final ResourceQueryBean EMPTY = new ResourceQueryBean(0, Integer.MAX_VALUE);

  public ResourceQueryBean() {}

  public ResourceQueryBean(Integer offset, Integer limit) {
    this.offset = offset;
    this.limit = limit;
  }

  // 资源名称
  private String resourceName;
  // 资源组名称
  private String className;
  // 资源ID
  private Integer resourceId;
  // 资源父ID
  private Integer parentId;
  // 是否可用
  private String isEnabled;

  // 用于分页 limit :offset, :limit
  private Integer offset;
  private Integer limit;

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public Integer getResourceId() {
    return resourceId;
  }

  public void setResourceId(Integer resourceId) {
    this.resourceId = resourceId;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(String isEnabled) {
    this.isEnabled = isEnabled;
  }
}
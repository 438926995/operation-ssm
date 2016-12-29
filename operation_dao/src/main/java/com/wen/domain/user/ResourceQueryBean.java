package com.wen.domain.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源信息查询
 * 
 * @author huwenwen
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
  // 添加人
  private Integer createUser;
  // 添加时间开始
  private Date createdFrom;
  // 添加时间结束
  private Date createdTo;
  // 更新时间开始
  private Date updatedFrom;
  // 更新时间结束
  private Date updatedTo;
  // 导出flg
  private Integer exportFlg;
  
  // 用于分页 limit :offset, :limit
  private Integer offset;
  private Integer limit;
  
  public Integer getExportFlg() {
    return exportFlg;
  }

  public void setExportFlg(Integer exportFlg) {
    this.exportFlg = exportFlg;
  }

  public Date getCreatedFrom() {
    return createdFrom;
  }

  public void setCreatedFrom(Date createdFrom) {
    this.createdFrom = createdFrom;
  }

  public Date getCreatedTo() {
    return createdTo;
  }

  public void setCreatedTo(Date createdTo) {
    this.createdTo = createdTo;
  }

  public Date getUpdatedFrom() {
    return updatedFrom;
  }

  public void setUpdatedFrom(Date updatedFrom) {
    this.updatedFrom = updatedFrom;
  }

  public Date getUpdatedTo() {
    return updatedTo;
  }

  public void setUpdatedTo(Date updatedTo) {
    this.updatedTo = updatedTo;
  }

  public Integer getCreateUser() {
    return createUser;
  }

  public void setCreateUser(Integer createUser) {
    this.createUser = createUser;
  }

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

package com.eleme.domain.ops.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源信息
 * 
 * @author zhangqiongbiao
 *
 */
public class Resource implements Serializable {
  private static final long serialVersionUID = -7657269964888695380L;

  // 资源编号
  private Integer resourceId;

  // 资源名称
  private String resourceName;

  // 资源描述
  private String resourceDesc;

  // 资源类型（URL、METHOD）
  private String resourceType;

  // url链接或方法名称
  private String resourceString;

  // 是否可用
  private String isEnabled;

  // 排序
  private Integer sortIndex;

  // 是否在左侧树显示
  private String showNav;

  // 属于左侧某一栏目下
  private Integer parentId;
 
  // 属于哪个栏目
  private String parentName;
  
  // 父节点的url
  private String parentString;

  // 菜单级别
  private String grade;

  // 系统标识
  private String prjType = "0";

  // 添加人
  private Integer createUser;

  // 添加时间
  private Date createdAt;

  // 更新时间
  private Date updatedAt;

  // 所有父节点ID
  private Integer[] parentIds;

  public String getParentString() {
    return parentString;
  }

  public void setParentString(String parentString) {
    this.parentString = parentString;
  }

  public Integer getResourceId() {
    return resourceId;
  }

  public void setResourceId(Integer resourceId) {
    this.resourceId = resourceId;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public String getResourceDesc() {
    return resourceDesc;
  }

  public void setResourceDesc(String resourceDesc) {
    this.resourceDesc = resourceDesc;
  }

  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

  public String getResourceString() {
    return resourceString;
  }

  public void setResourceString(String resourceString) {
    this.resourceString = resourceString;
  }

  public String getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(String isEnabled) {
    this.isEnabled = isEnabled;
  }

  public Integer getSortIndex() {
    return sortIndex;
  }

  public void setSortIndex(Integer sortIndex) {
    this.sortIndex = sortIndex;
  }

  public String getShowNav() {
    return showNav;
  }

  public void setShowNav(String showNav) {
    this.showNav = showNav;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public String getPrjType() {
    return prjType;
  }

  public void setPrjType(String prjType) {
    this.prjType = prjType;
  }

  public Integer getCreateUser() {
    return createUser;
  }

  public void setCreateUser(Integer createUser) {
    this.createUser = createUser;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Integer[] getParentIds() {
    return parentIds;
  }

  public void setParentIds(Integer[] parentIds) {
    this.parentIds = parentIds;
  }

  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }
}
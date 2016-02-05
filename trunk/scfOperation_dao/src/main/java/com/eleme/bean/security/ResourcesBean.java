package com.eleme.bean.security;

import java.io.Serializable;

/**
 * 资源信息封装类.
 * 
 * @author penglau
 *
 */
public class ResourcesBean implements Serializable {

  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = 4127408723128506977L;
  /**
   * 资源编号
   */
  private Long resourceId;
  /**
   * 资源路径
   */
  private String resourceString;
  /**
   * 资源名称
   */
  private String resourceName;
  /**
   * 标识
   */
  private Boolean flag;
  /**
   * 参数模板
   */
  private String paramTPL;
  /**
   * 资源类型
   */
  private String resourceType;

  /**
   * property parentId.
   */
  private Long parentId;
  /**
   * 菜单级别
   */
  private String grade;
  /**
   * 是否可用
   */
  private String isEnabled;
  /**
   * 默认排序
   */
  private Integer sortIndex;
  /**
   * 
   */
  private String showNav;
  /**
   * 资源描述
   */
  private String resourceDesc;
  /**
   * 所属类
   */
  private Long classId;

  public Long getResourceId() {
    return resourceId;
  }

  public void setResourceId(Long resourceId) {
    this.resourceId = resourceId;
  }

  public String getResourceString() {
    return resourceString;
  }

  public void setResourceString(String resourceString) {
    this.resourceString = resourceString;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public Boolean getFlag() {
    return flag;
  }

  public void setFlag(Boolean flag) {
    this.flag = flag;
  }

  public String getParamTPL() {
    return paramTPL;
  }

  public void setParamTPL(String paramTPL) {
    this.paramTPL = paramTPL;
  }

  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
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

  public String getResourceDesc() {
    return resourceDesc;
  }

  public void setResourceDesc(String resourceDesc) {
    this.resourceDesc = resourceDesc;
  }

  public Long getClassId() {
    return classId;
  }

  public void setClassId(Long classId) {
    this.classId = classId;
  }

}

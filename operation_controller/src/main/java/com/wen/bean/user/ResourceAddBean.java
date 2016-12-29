package com.wen.bean.user;

import java.io.Serializable;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.wen.validatorgroup.First;
import com.wen.validatorgroup.Four;
import com.wen.validatorgroup.Second;
import com.wen.validatorgroup.Third;

/**
 * 资源添加界面的封装bean.
 * 
 * @author huwenwen
 */
@GroupSequence({First.class, Second.class, Third.class, Four.class, ResourceAddBean.class})
public class ResourceAddBean implements Serializable {
  private static final long serialVersionUID = -6167250879515978411L;

  // 资源编号
  private Integer resourceId;

  // 资源名称
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 50, message = "{field.range}", groups = {Second.class})
  private String resourceName;

  // 资源描述
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 0, max = 200, message = "{field.range}", groups = {Second.class})
  private String resourceDesc;

  // 资源类型（URL、METHOD）
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String resourceType;

  // url链接或方法名称
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 0, max = 200, message = "{field.range}", groups = {Second.class})
  private String resourceString;

  // 是否可用
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String isEnabled;

  // 排序
  @NotNull(message = "{field.required}", groups = {First.class})
  private Integer sortIndex;

  // 是否在左侧树显示
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String showNav;

  // 属于左侧某一栏目下
  @NotNull(message = "{field.required}", groups = {First.class})
  private Integer parentId;

  // 菜单级别
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String grade;

  // 所有的父节点ID
  private Integer[] parentIds;

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

  public Integer[] getParentIds() {
    return parentIds;
  }

  public void setParentIds(Integer[] parentIds) {
    this.parentIds = parentIds;
  }
}

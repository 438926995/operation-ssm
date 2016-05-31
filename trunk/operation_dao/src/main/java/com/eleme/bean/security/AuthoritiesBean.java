package com.eleme.bean.security;

import java.io.Serializable;
import java.util.List;

/**
 * 用户权限信息封装类.
 * 
 * @author huwenwen
 *
 */
public class AuthoritiesBean implements Serializable {

  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = 1740337208123524338L;
  /**
   * 权限编号
   */
  private Long authId;
  /**
   * 权限名称
   */
  private String authName;
  /**
   * 是否拥有标识
   */
  private Boolean flag;
  /**
   * 资源集合
   */
  private List<ResourcesBean> resourceList;

  public Long getAuthId() {
    return authId;
  }

  public void setAuthId(Long authId) {
    this.authId = authId;
  }

  public String getAuthName() {
    return authName;
  }

  public void setAuthName(String authName) {
    this.authName = authName;
  }

  public Boolean getFlag() {
    return flag;
  }

  public void setFlag(Boolean flag) {
    this.flag = flag;
  }

  public List<ResourcesBean> getResourceList() {
    return resourceList;
  }

  public void setResourceList(List<ResourcesBean> resourceList) {
    this.resourceList = resourceList;
  }

}

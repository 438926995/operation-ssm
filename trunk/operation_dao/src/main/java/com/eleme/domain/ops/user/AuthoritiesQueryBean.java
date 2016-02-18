package com.eleme.domain.ops.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限信息查询
 * 
 * @author huwenwen
 *
 */
public class AuthoritiesQueryBean implements Serializable {
  private static final long serialVersionUID = 3758706006902530935L;

  public static final AuthoritiesQueryBean EMPTY = new AuthoritiesQueryBean(0, Integer.MAX_VALUE);

  public AuthoritiesQueryBean() {}

  public AuthoritiesQueryBean(Integer offset, Integer limit) {
    this.offset = offset;
    this.limit = limit;
  }

  // 权限名称
  private String authName;
  // 权限组名称
  private String authModuleName;
  // 权限ID
  private Integer authoritiesId;
  // 是否可用
  private String isEnabled;
  // 权限组id
  private Integer authModuleId;
  // 创建时间开始
  private Date createdFrom;
  // 创建时间结束
  private Date createdEnd;
  // 导出标志
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

  public Integer getAuthModuleId() {
    return authModuleId;
  }

  public void setAuthModuleId(Integer authModuleId) {
    this.authModuleId = authModuleId;
  }

  public Date getCreatedFrom() {
    return createdFrom;
  }

  public void setCreatedFrom(Date createdFrom) {
    this.createdFrom = createdFrom;
  }

  public Date getCreatedEnd() {
    return createdEnd;
  }

  public void setCreatedEnd(Date createdEnd) {
    this.createdEnd = createdEnd;
  }

  public String getAuthName() {
    return authName;
  }

  public void setAuthName(String authName) {
    this.authName = authName;
  }

  public String getAuthModuleName() {
    return authModuleName;
  }

  public void setAuthModuleName(String authModuleName) {
    this.authModuleName = authModuleName;
  }

  public Integer getAuthoritiesId() {
    return authoritiesId;
  }

  public void setAuthoritiesId(Integer authoritiesId) {
    this.authoritiesId = authoritiesId;
  }

  public String getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(String isEnabled) {
    this.isEnabled = isEnabled;
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
}
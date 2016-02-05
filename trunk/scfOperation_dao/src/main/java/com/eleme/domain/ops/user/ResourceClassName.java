package com.eleme.domain.ops.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源分组信息
 * 
 * @author zhangqiongbiao
 */
public class ResourceClassName implements Serializable {
  private static final long serialVersionUID = 7475968739506973348L;
  
  // 主键
  private Integer classId;
  // 资源组名称
  private String className;
  // 系统标识
  private String prjType = "0";
  // 添加时间
  private Date createdAt;
  // 修改时间
  private Date updatedAt;

  public Integer getClassId() {
    return classId;
  }

  public void setClassId(Integer classId) {
    this.classId = classId;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getPrjType() {
    return prjType;
  }

  public void setPrjType(String prjType) {
    this.prjType = prjType;
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
}
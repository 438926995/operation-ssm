package com.eleme.bean.file;

import java.util.Date;

/**
 * 上传文件对象
 * 
 * @author sunwei
 * @since 2015年12月14日
 *
 */
public class SysUploadFileBean {

  /**
   * 主键
   */
  private Integer ufId;
  /**
   * 项目名称
   */
  private String projectName;
  /**
   * 文件上传虚拟路径名称
   */
  private String contextPath;
  /**
   * 文件源名称
   */
  private String sourceName;
  /**
   * 文件上传之后的名称
   */
  private String fileEditName;
  /**
   * 文件的mimeType
   */
  private String mimeType;
  /**
   * 文件后缀
   */
  private String postFix;
  /**
   * 上传文件的大小
   */
  private Long fileSize;
  /**
   * 文件访问地址
   */
  private String url;
  /**
   * 上传人
   */
  private String userOfUpload;
  /**
   * 图片分辨率
   */
  private String imageResolution;
  /**
   * 分类的主键
   */
  private Integer containId;
  /**
   * 属于哪个分类
   */
  private String containType;

  /**
   * 创建时间
   */
  private Date createdAt;

  /**
   * 更新时间
   */
  private Date updatedAt;
  /**
   * 是否是后台人员
   */
  private Integer isOps;

  public Integer getUfId() {
    return ufId;
  }

  public void setUfId(Integer ufId) {
    this.ufId = ufId;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getContextPath() {
    return contextPath;
  }

  public void setContextPath(String contextPath) {
    this.contextPath = contextPath;
  }

  public String getSourceName() {
    return sourceName;
  }

  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }

  public String getFileEditName() {
    return fileEditName;
  }

  public void setFileEditName(String fileEditName) {
    this.fileEditName = fileEditName;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public String getPostFix() {
    return postFix;
  }

  public void setPostFix(String postFix) {
    this.postFix = postFix;
  }

  public Long getFileSize() {
    return fileSize;
  }

  public void setFileSize(Long fileSize) {
    this.fileSize = fileSize;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUserOfUpload() {
    return userOfUpload;
  }

  public void setUserOfUpload(String userOfUpload) {
    this.userOfUpload = userOfUpload;
  }

  public String getImageResolution() {
    return imageResolution;
  }

  public void setImageResolution(String imageResolution) {
    this.imageResolution = imageResolution;
  }

  public Integer getContainId() {
    return containId;
  }

  public void setContainId(Integer containId) {
    this.containId = containId;
  }

  public String getContainType() {
    return containType;
  }

  public void setContainType(String containType) {
    this.containType = containType;
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

  public Integer getIsOps() {
    return isOps;
  }

  public void setIsOps(Integer isOps) {
    this.isOps = isOps;
  }

}

package com.eleme.domain.mart.finance;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 金融机构页面显示封装类
 * 
 * @author yonglin.zhu
 *
 */
public class MFinanceOrg implements Serializable {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -295364334689403674L;
  // 主键ID
  private Integer foId;
  // 机构名称
  private String foName;
  // 机构简介
  private String foDesc;
  // 联系人
  private String contactName;
  // 联系人手机号码
  private String contactPhone;
  // 添加时间
  private Date createTime;
  // 添加时间
  private String createTimeStr;
  // 状态
  private Integer foStatus;
  // 状态
  private String foStatusStr;
  // 公司地址
  private String foAddr;
  // 公司网址
  private String foUrl;
  // 状态
  private String foPhone;
  // 上传文件表的主键
  private Integer ufId;
  // 金融机构logo
  private MultipartFile foLogo;

  public MultipartFile getFoLogo() {
    return foLogo;
  }

  public void setFoLogo(MultipartFile foLogo) {
    this.foLogo = foLogo;
  }

  public Integer getUfId() {
    return ufId;
  }

  public void setUfId(Integer ufId) {
    this.ufId = ufId;
  }

  public Integer getFoId() {
    return foId;
  }

  public void setFoId(Integer foId) {
    this.foId = foId;
  }

  public String getFoName() {
    return foName;
  }

  public void setFoName(String foName) {
    this.foName = foName == null ? null : foName.trim();
  }

  public String getFoDesc() {
    return foDesc;
  }

  public void setFoDesc(String foDesc) {
    this.foDesc = foDesc == null ? null : foDesc.trim();
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName == null ? null : contactName.trim();
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone == null ? null : contactPhone.trim();
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Integer getFoStatus() {
    return foStatus;
  }

  public void setFoStatus(Integer foStatus) {
    this.foStatus = foStatus;
  }

  public String getCreateTimeStr() {
    if (this.createTime != null) {
      return DateFormatUtils.format(this.createTime,
          com.eleme.util.DateUtil.ISO_NO_T_DATETIME_FORMAT);
    }
    return createTimeStr;
  }

  public void setCreateTimeStr(String createTimeStr) {
    this.createTimeStr = createTimeStr;
  }

  public String getFoStatusStr() {
    if (this.foStatus != null) {
      if (this.foStatus.intValue() == 0) {
        return "洽谈中";
      } else if (this.foStatus.intValue() == 1) {
        return "合作中";
      } else if (this.foStatus.intValue() == 2) {
        return "取消合作";
      }
    }
    return foStatusStr;
  }

  public void setFoStatusStr(String foStatusStr) {
    this.foStatusStr = foStatusStr;
  }

  public String getFoAddr() {
    return foAddr;
  }

  public void setFoAddr(String foAddr) {
    this.foAddr = foAddr;
  }

  public String getFoUrl() {
    return foUrl;
  }

  public void setFoUrl(String foUrl) {
    this.foUrl = foUrl;
  }

  public String getFoPhone() {
    return foPhone;
  }

  public void setFoPhone(String foPhone) {
    this.foPhone = foPhone;
  }

}

package com.eleme.domain.mart.loan;

import java.util.Date;

/**
 * 审批流程
 * 
 * @author xudong.gu
 *
 */
public class TApvFlowProcess {

  // 主键
  private Integer procsID;

  // 流程名称
  private String procsName;

  // 是否有效
  private Integer isValid;

  // 流程描述
  private String procsDesc;

  // 金融机构id
  private Integer foID;

  // 金融产品id
  private Integer fpID;

  // 创建时间
  private Date createAt;

  // 更新时间
  private Date updateAt;

  public Integer getProcsID() {
    return procsID;
  }

  public void setProcsID(Integer procsID) {
    this.procsID = procsID;
  }

  public String getProcsName() {
    return procsName;
  }

  public void setProcsName(String procsName) {
    this.procsName = procsName;
  }

  public Integer getIsValid() {
    return isValid;
  }

  public void setIsValid(Integer isValid) {
    this.isValid = isValid;
  }

  public String getProcsDesc() {
    return procsDesc;
  }

  public void setProcsDesc(String procsDesc) {
    this.procsDesc = procsDesc;
  }

  public Integer getFoID() {
    return foID;
  }

  public void setFoID(Integer foID) {
    this.foID = foID;
  }

  public Integer getFpID() {
    return fpID;
  }

  public void setFpID(Integer fpID) {
    this.fpID = fpID;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }

  public Date getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(Date updateAt) {
    this.updateAt = updateAt;
  }

}

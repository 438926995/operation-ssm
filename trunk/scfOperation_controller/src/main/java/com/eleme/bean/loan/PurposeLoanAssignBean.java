package com.eleme.bean.loan;

import java.io.Serializable;

/**
 * 商户借贷意向申请分配机构封装
 * 
 * @author yonglin.zhu
 */
public class PurposeLoanAssignBean implements Serializable {
  private static final long serialVersionUID = 9144028040362952365L;

  // 申请ID
  private Integer[] plIds;
  // 机构产品ID
  private Integer fpId;
  // 是否拉去流水 1:是 0:否
  private String isGetFlow;

  public Integer[] getPlIds() {
    return plIds;
  }

  public void setPlIds(Integer[] plIds) {
    this.plIds = plIds;
  }

  public Integer getFpId() {
    return fpId;
  }

  public void setFpId(Integer fpId) {
    this.fpId = fpId;
  }

  public String getIsGetFlow() {
    return isGetFlow;
  }

  public void setIsGetFlow(String isGetFlow) {
    this.isGetFlow = isGetFlow;
  }


}

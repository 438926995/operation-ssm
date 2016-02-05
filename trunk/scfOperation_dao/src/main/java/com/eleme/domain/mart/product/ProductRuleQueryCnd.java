package com.eleme.domain.mart.product;

/**
 * 
 * 融资产品规则查询条件封装类
 * 
 * @author yonglin.zhu
 *
 */
public class ProductRuleQueryCnd {
  // 分页开始
  private Integer startRecord;
  // 页面显示条数
  private Integer pageSize;
  // 产品ID
  private Integer fpId;
  // 规则名称ID
  private Integer ruleName;
  // 规则主键
  private Integer ruleId;
  // 手机端调用标志
  private String isMobileUse;

  public Integer getStartRecord() {
    return startRecord;
  }

  public void setStartRecord(Integer startRecord) {
    this.startRecord = startRecord;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getFpId() {
    return fpId;
  }

  public void setFpId(Integer fpId) {
    this.fpId = fpId;
  }

  public Integer getRuleName() {
    return ruleName;
  }

  public void setRuleName(Integer ruleName) {
    this.ruleName = ruleName;
  }

  public Integer getRuleId() {
    return ruleId;
  }

  public void setRuleId(Integer ruleId) {
    this.ruleId = ruleId;
  }

  public String getIsMobileUse() {
    return isMobileUse;
  }

  public void setIsMobileUse(String isMobileUse) {
    this.isMobileUse = isMobileUse;
  }


}

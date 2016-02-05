package com.eleme.domain.mart.product;

/**
 * 融资产品封装类
 * 
 * @author yonglin.zhu
 *
 */
public class TFcoRequestRule {
  // 主键
  private Integer ruleId;

  // 规则名外键关联到基础数据表
  private Integer ruleName;

  // 是否启用
  private Integer isUse;

  // 外键关联到产品表
  private Integer fpId;

  // 区间初始符号
  private String fromSymbol;

  // 区间初始数值
  private Integer fromNum;

  // 区间结束符号
  private String endSymbol;

  // 区间结束数值
  private Integer endNum;

  public Integer getRuleId() {
    return ruleId;
  }

  public void setRuleId(Integer ruleId) {
    this.ruleId = ruleId;
  }

  public Integer getRuleName() {
    return ruleName;
  }

  public void setRuleName(Integer ruleName) {
    this.ruleName = ruleName;
  }

  public Integer getIsUse() {
    return isUse;
  }

  public void setIsUse(Integer isUse) {
    this.isUse = isUse;
  }

  public Integer getFpId() {
    return fpId;
  }

  public void setFpId(Integer fpId) {
    this.fpId = fpId;
  }

  public String getFromSymbol() {
    return fromSymbol;
  }

  public void setFromSymbol(String fromSymbol) {
    this.fromSymbol = fromSymbol == null ? null : fromSymbol.trim();
  }

  public Integer getFromNum() {
    return fromNum;
  }

  public void setFromNum(Integer fromNum) {
    this.fromNum = fromNum;
  }

  public String getEndSymbol() {
    return endSymbol;
  }

  public void setEndSymbol(String endSymbol) {
    this.endSymbol = endSymbol == null ? null : endSymbol.trim();
  }

  public Integer getEndNum() {
    return endNum;
  }

  public void setEndNum(Integer endNum) {
    this.endNum = endNum;
  }
}

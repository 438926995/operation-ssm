package com.eleme.bean.product;

import java.io.Serializable;

import javax.validation.GroupSequence;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.eleme.validatorgroup.First;
import com.eleme.validatorgroup.Four;
import com.eleme.validatorgroup.Second;
import com.eleme.validatorgroup.Third;

/**
 * 融资产品规则添加界面的封装bean.
 * 
 * @author yonglin.zhu
 *
 */
@GroupSequence({First.class, Second.class, Third.class, Four.class, ProductRuleAddBean.class})
public class ProductRuleAddBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -8651487950299580193L;

  // 规则名称
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String ruleName;
  // 区间初始数值
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 10, message = "{field.range}", groups = {Second.class})
  @Digits(integer=10,fraction = 0,message = "请输入整数", groups = {Third.class})
  private String fromNum;
  // 区间结束数值
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 10, message = "{field.range}", groups = {Second.class})
  @Digits(integer=10,fraction = 0,message = "请输入整数", groups = {Third.class})
  private String endNum;
  // 是否启用
  @NotNull(message = "{field.required}", groups = {First.class})
  private Integer isUse;
  // 外键关联到产品表
  private Integer fpId;
  // 规则主键
  private Integer ruleId;


  public String getRuleName() {
    return ruleName;
  }

  public void setRuleName(String ruleName) {
    this.ruleName = ruleName;
  }

  public String getFromNum() {
    return fromNum;
  }

  public void setFromNum(String fromNum) {
    this.fromNum = fromNum;
  }

  public String getEndNum() {
    return endNum;
  }

  public void setEndNum(String endNum) {
    this.endNum = endNum;
  }

  public Integer getFpId() {
    return fpId;
  }

  public void setFpId(Integer fpId) {
    this.fpId = fpId;
  }

  public Integer getIsUse() {
    return isUse;
  }

  public void setIsUse(Integer isUse) {
    this.isUse = isUse;
  }

  public Integer getRuleId() {
    return ruleId;
  }

  public void setRuleId(Integer ruleId) {
    this.ruleId = ruleId;
  }


}

package com.eleme.bean.basic;

import java.io.Serializable;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.eleme.validatorgroup.First;
import com.eleme.validatorgroup.Four;
import com.eleme.validatorgroup.Second;
import com.eleme.validatorgroup.Third;

/**
 * 基础数据添加验证bean
 * 
 * @author huwenwen
 *
 */
@GroupSequence({First.class, Second.class, Third.class, Four.class, BasicAddBean.class})
public class BasicAddBean implements Serializable {

  /**
   * serial VersionUID
   */
  private static final long serialVersionUID = 9004465450529732491L;
  // 类型CD
  private String typeCd;

  // 分类ID
  private Integer typeId;
  
  // 分类ID名称
  private String typeIdName;

  // 类型CD名称
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 50, message = "{field.range}", groups = {Second.class})
  private String typeCdName;

  // 排序
  @NotNull(message = "{field.required}", groups = {First.class})
  private Integer sortIndex;

  // 是否可见
  private Integer isVisible;

  // 备注（使用目的）
  private String remarks;

  public String getTypeCd() {
    return typeCd;
  }

  public void setTypeCd(String typeCd) {
    this.typeCd = typeCd;
  }

  public Integer getTypeId() {
    return typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }

  public String getTypeIdName() {
    return typeIdName;
  }

  public void setTypeIdName(String typeIdName) {
    this.typeIdName = typeIdName;
  }

  public String getTypeCdName() {
    return typeCdName;
  }

  public void setTypeCdName(String typeCdName) {
    this.typeCdName = typeCdName;
  }

  public Integer getSortIndex() {
    return sortIndex;
  }

  public void setSortIndex(Integer sortIndex) {
    this.sortIndex = sortIndex;
  }

  public Integer getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Integer isVisible) {
    this.isVisible = isVisible;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

}

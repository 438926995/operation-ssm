package com.eleme.domain.mart.product;

import java.io.Serializable;

/**
 * 融资产品封装类
 * 
 * @author yonglin.zhu
 *
 */
public class TFcoRequestRuleVo extends TFcoRequestRule implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1764459143487141335L;
  // 规则名称
  private String typeCdName;

  public String getTypeCdName() {
    return typeCdName;
  }

  public void setTypeCdName(String typeCdName) {
    this.typeCdName = typeCdName;
  }

}

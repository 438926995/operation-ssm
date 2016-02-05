package com.eleme.domain.mart.martusers;

import java.io.Serializable;

/**
 * 用户基础信息画面显示封装类
 * 
 * @author yonglin.zhu
 *
 */
public class MMartUsersVo extends MMartUsers implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1804429986603382641L;

  /**
   * 机构名称
   */
  private String foName;

  public String getFoName() {
    return foName;
  }

  public void setFoName(String foName) {
    this.foName = foName;
  }

}

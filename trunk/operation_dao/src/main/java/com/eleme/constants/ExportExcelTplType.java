package com.eleme.constants;

/**
 * Created by huwenwen on 16/1/26.
 */
public enum ExportExcelTplType {

  // 默认类型
  DEFAULT(0),
  // 贷款申请导出
  APP(1);

  int type = 0 ;

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  ExportExcelTplType(int type) {
    this.type = type;
  }
}

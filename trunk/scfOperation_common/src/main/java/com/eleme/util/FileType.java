package com.eleme.util;

/**
 * 文件类型枚举
 * 
 * @author sunwei
 * @since 2015年12月14日
 *
 */
public enum FileType {

  // excel 模板
  EXCEL_TEMPLATE,
  // 对应M_FINANCE_ORG表
  FINANCE_LOGO {
    public String getName() {
      return "金融机构LOGO";
    }
  },
  PRODUCT_BANNER

}

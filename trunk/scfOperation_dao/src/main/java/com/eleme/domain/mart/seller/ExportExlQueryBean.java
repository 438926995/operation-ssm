package com.eleme.domain.mart.seller;

import com.eleme.bean.seller.SellerQueryBean;

/**
 * 导出时请求参数对象
 * 
 * @author sunwei
 * @since 2016年1月5日
 */
public class ExportExlQueryBean extends SellerQueryBean {

  // 导出方式：0：根据搜索条件查询 1：根据导入的excel查询
  private String exportExlType;
  // 导出Excel时设置需要导出的字段
  private String exportExlColumn;
  // 导出Excel查询条件
  private String exportSellerIdsStr;


  public String getExportExlColumn() {
    return exportExlColumn;
  }

  public void setExportExlColumn(String exportExlColumn) {
    this.exportExlColumn = exportExlColumn;
  }

  public String getExportSellerIdsStr() {
    return exportSellerIdsStr;
  }

  public void setExportSellerIdsStr(String exportSellerIdsStr) {
    this.exportSellerIdsStr = exportSellerIdsStr;
  }

  public String getExportExlType() {
    return exportExlType;
  }

  public void setExportExlType(String exportExlType) {
    this.exportExlType = exportExlType;
  }

}

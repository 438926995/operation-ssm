package com.eleme.domain.mart.loan;

/**
 * 导出意向贷款列表数据
 * Created by sunwei on 16/1/20.
 */
public class ExportPurposeLoanQueryCnd extends PurposeLoanQueryCnd {

  // 导出Excel时设置需要导出的字段
  private String exportExlColumn;

  public String getExportExlColumn() {
    return exportExlColumn;
  }

  public void setExportExlColumn(String exportExlColumn) {
    this.exportExlColumn = exportExlColumn;
  }
}

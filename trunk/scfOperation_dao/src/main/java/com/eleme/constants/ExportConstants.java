package com.eleme.constants;


import com.eleme.domain.mart.export.ExportExlColumnBean;
import com.eleme.domain.mart.export.ExportExlColumnTypeBean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ExportConstants {

  // 导出列的基础数据
  public static final Map<Integer, ExportExlColumnBean> columnMap = new LinkedHashMap<Integer, ExportExlColumnBean>();

  // 申请单导出
  public static final List<ExportExlColumnTypeBean> approveLoanList = new ArrayList<ExportExlColumnTypeBean>();

  static {
    columnMap.put(1, new ExportExlColumnBean(1, "docNo", "订单编号", true));
    columnMap.put(2, new ExportExlColumnBean(2, "proposerName", "申请人姓名", true));
    columnMap.put(3, new ExportExlColumnBean(3, "submitTimeStr", "申请时间"));
    columnMap.put(4, new ExportExlColumnBean(4, "loanAmountTenThousand", "申请金额"));
    columnMap.put(5, new ExportExlColumnBean(5, "proposerPhone", "申请人手机号"));
    columnMap.put(6, new ExportExlColumnBean(6, "proposerSid", "身份证号码"));
    columnMap.put(7, new ExportExlColumnBean(7, "proposerAge", "年龄"));
    columnMap.put(8, new ExportExlColumnBean(8, "proposerSexStr", "性别"));
    columnMap.put(9, new ExportExlColumnBean(9, "naposResOid", "餐厅id", true));
    columnMap.put(10, new ExportExlColumnBean(10, "sellerName", "餐厅名称", true));
    columnMap.put(11, new ExportExlColumnBean(11, "addressText", "餐厅地址"));
    columnMap.put(12, new ExportExlColumnBean(12, "proposerName", "餐厅店主"));
    columnMap.put(13, new ExportExlColumnBean(13, "proposerPhone", "店主电话"));
    columnMap.put(14, new ExportExlColumnBean(14, "createdDate", "平台注册时间"));

    ExportExlColumnTypeBean applyLoanInfo = new ExportExlColumnTypeBean(1, "申请贷款信息");
    applyLoanInfo.addExportExlColumnBeans(columnMap.get(1));
    applyLoanInfo.addExportExlColumnBeans(columnMap.get(2));
    applyLoanInfo.addExportExlColumnBeans(columnMap.get(3));
    applyLoanInfo.addExportExlColumnBeans(columnMap.get(4));
    applyLoanInfo.addExportExlColumnBeans(columnMap.get(5));
    applyLoanInfo.addExportExlColumnBeans(columnMap.get(6));
    applyLoanInfo.addExportExlColumnBeans(columnMap.get(7));
    applyLoanInfo.addExportExlColumnBeans(columnMap.get(8));

    ExportExlColumnTypeBean sellerDataInfo = new ExportExlColumnTypeBean(2, "餐厅数据信息");
    sellerDataInfo.addExportExlColumnBeans(columnMap.get(9));
    sellerDataInfo.addExportExlColumnBeans(columnMap.get(10));
    sellerDataInfo.addExportExlColumnBeans(columnMap.get(11));
    sellerDataInfo.addExportExlColumnBeans(columnMap.get(12));
    sellerDataInfo.addExportExlColumnBeans(columnMap.get(13));
    sellerDataInfo.addExportExlColumnBeans(columnMap.get(14));

    approveLoanList.add(applyLoanInfo);
    approveLoanList.add(sellerDataInfo);
  }

  public static ExportExlColumnBean getExportExlColumnBean(Integer id) {
    return columnMap.get(id);
  }

}

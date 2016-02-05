package com.eleme.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.junit.Test;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import com.eleme.domain.mart.seller.TSdcTradeFlow;
import com.eleme.mapper.mart.seller.TSdcTradeFlowMapper;

public class TestTradeFlowDetailExport extends BaseTest {
  @Inject
  TSdcTradeFlowMapper tradeFlowMapper;


  @Test
  public void Test() throws ParseException, IOException {

    try (InputStream is = testJxls.class.getResourceAsStream("trade_flow_detail.xls")) {
      try (OutputStream os = new FileOutputStream("/Users/sunwei/temp/trade_flow_detail.xls")) {
        List<TSdcTradeFlow> tradeFlowList = tradeFlowMapper.selectBySellerId(4);
        Context context = new Context();
        context.putVar("tradeFlows", tradeFlowList);
        long startTime = new Date().getTime();
        System.out.println("开始");
        JxlsHelper.getInstance().processTemplate(is, os, context);
        System.out.println(
            "导出" + tradeFlowList.size() + "条，耗时：" + (new Date().getTime() - startTime) + "ms");
      }
    }
  }

//  @Test
//  public void TestOrg() throws ParseException, IOException {
//    List<Employee> employees = generateSampleEmployeeData();
//    try (InputStream is = testJxls.class.getResourceAsStream("object_collection_template.xls")) {
//      try (OutputStream os =
//          new FileOutputStream("/Users/sunwei/temp/object_collection_output.xls")) {
//        Context context = new Context();
//        context.putVar("employees", employees);
//        long startTime = new Date().getTime();
//        System.out.println("开始");
//        JxlsHelper.getInstance().processTemplate(is, os, context);
//        System.out.println(
//            "导出" + employees.size() + "条，耗时：" + (new Date().getTime() - startTime) + "ms");
//      }
//    }
//  }

  public static List<Employee> generateSampleEmployeeData() throws ParseException {
    List<Employee> employees = new ArrayList<Employee>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
    for (int i = 0; i < 4000; i++) {
      employees.add(new Employee("Elsa", dateFormat.parse("1970-Jul-10"), 1500, 0.15));
      employees.add(new Employee("Oleg", dateFormat.parse("1973-Apr-30"), 2300, 0.25));
      employees.add(new Employee("Neil", dateFormat.parse("1975-Oct-05"), 2500, 0.00));
      employees.add(new Employee("Maria", dateFormat.parse("1978-Jan-07"), 1700, 0.15));
      employees.add(new Employee("John", dateFormat.parse("1969-May-30"), 2800, 0.20));
    }
    return employees;
  }

}

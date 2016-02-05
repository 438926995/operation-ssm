package com.eleme.test;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.eleme.util.POIUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;


public class testJxls {
  public static void main(String[] args) throws ParseException, IOException {
    System.out.println(TransformerFactory.getTransformerName());
    List<Employee> employees = generateSampleEmployeeData();
    XSSFWorkbook workbook =new XSSFWorkbook(new FileInputStream("/Users/sunwei/temp/aa.xls"));
    POIUtil.deleteColumn(workbook.getSheetAt(0),1);
    workbook.write(new FileOutputStream("/Users/sunwei/temp/ttttt2.xls"));
//    try (InputStream is = new FileInputStream("/Users/sunwei/temp/ttttt2.xls")) {
//      try (OutputStream os =
//          new FileOutputStream("/Users/sunwei/temp/object_collection_output2.xls")) {
//        Context context = new Context();
//        context.putVar("employees", employees);
//        long startTime = new Date().getTime();
//        JxlsHelper.getInstance().processTemplate(is, os, context);
//        System.out.println(new Date().getTime() - startTime);
//      }
//    }
  }
  
  

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


package com.eleme.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TestDateFormat {

  @Test
  public void Test() {

    String aa = "aa|bb";
    String[] aaArr = aa.split("\\|");
    System.out.println(aaArr[1]);
    // DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    // System.out.println(dateFormat.format(new Date()));
    // System.out.println(dateFormat.format(new Date()));
    // System.out.println(dateFormat.format(new Date()));
    // System.out.println(dateFormat.format(new Date()));
  }
}

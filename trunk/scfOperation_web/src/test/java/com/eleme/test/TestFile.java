package com.eleme.test;

import java.io.File;

import org.junit.Test;

import com.eleme.util.FileUtil;


public class TestFile {

  @Test
  public void testFile() {

     boolean isSuccess = FileUtil.createDir("/Users/sunwei/excel/");
     System.out.println("isSuccess:" + isSuccess);
//    boolean isSuccess = true;
//    String filePath = "/Users/sunwei/excel";
//    File fp = new File(filePath);
//    // 创建目录
//    if (!fp.exists()) {
//      isSuccess = fp.mkdirs();// 目录不存在的情况下，创建目录。
//    }
    System.out.println("isSuccess:" + isSuccess);

  }

}

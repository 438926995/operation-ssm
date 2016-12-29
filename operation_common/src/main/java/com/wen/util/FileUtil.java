package com.wen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 文件相关方法
 * 
 * @author huwenwen
 *
 */
public class FileUtil {

  /**
   * log
   */
  private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

  /**
   * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件.
   * 
   * @param fileName 文件的名.
   */
  public static String readFileByBytes(String fileName) throws IOException {
    StringBuffer fileContext = new StringBuffer("");
    File file = new File(fileName);
    InputStream in = null;
    try {
      // 一次读一个字节
      in = new FileInputStream(file);
      int tempbyte;
      while ((tempbyte = in.read()) != -1) {
        fileContext.append(tempbyte);
      }
      in.close();
    } catch (IOException e) {
      logger.error(CommonUtil.getErrorMessage(e));
    }
    return fileContext.toString();
  }

  /**
   * 以字符为单位读取文件，常用于读文本，数字等类型的文件.
   * 
   * @param fileName 文件名.
   * @param encoding
   */
  public static String readFileByChars(String fileName, String encoding) throws IOException {
    StringBuffer fileContext = new StringBuffer("");
    File file = new File(fileName);
    Reader reader = null;
    try {
      // 一次读一个字符
      reader = new InputStreamReader(new FileInputStream(file), encoding);
      int tempchar;
      while ((tempchar = reader.read()) != -1) {
        // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
        // 但如果这两个字符分开显示时，会换两次行。
        // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
        if (((char) tempchar) != '\r') {
          fileContext.append((char) tempchar);
        }
      }
      reader.close();
    } catch (IOException e) {
      logger.error(CommonUtil.getErrorMessage(e));
      throw e;
    }
    return fileContext.toString();
  }

  /**
   * 创建文件夹
   * 
   * @author huwenwen
   * @since 2016年1月3日
   * @param destDirName
   * @return
   */
  public static boolean createDir(String destDirName) {
    File dir = new File(destDirName);
    if (dir.exists()) {
      return false;
    }
    if (dir.mkdirs()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 删除文件
   * 
   * @author huwenwen
   * @since 2016年1月3日
   * @param destFileName
   * @return
   */
  public static boolean deleteFile(String destFileName) {
    try {
      File file = new File(destFileName);
      return file.delete();
    } catch (Exception e) {
      logger.error(CommonUtil.getErrorMessage(e));
      return false;
    }

  }
}

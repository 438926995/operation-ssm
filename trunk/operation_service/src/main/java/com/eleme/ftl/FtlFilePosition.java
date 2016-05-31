package com.eleme.ftl;

import java.io.Serializable;

/**
 * ftl文件定位类.
 * 
 * @author huwenwen
 */
@SuppressWarnings("serial")
public class FtlFilePosition implements Serializable {

  /**
   * 获取当前类在项目中的路径.
   */
  public final static String PATH = FtlFilePosition.class.getResource("").getPath();
}

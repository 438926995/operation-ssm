package com.eleme.ftl.ajax;

import com.eleme.ftl.email.EmailFtlPosition;

/**
 * ajax ftl 文件定位类.
 * 
 * @author huwenwen
 *
 */
public class AjaxFtlPosition {
  /**
   * 获取当前类在项目中的路径.
   */
  public final static String PATH = EmailFtlPosition.class.getResource("").getPath();
}

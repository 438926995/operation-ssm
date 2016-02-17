package com.eleme.util;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 日期相关工具类.
 * 
 * @author penglau
 *
 */
public class DateUtil {

	/**
	 * log实例
	 */
	private static Log log = LogFactory.getLog(DateUtil.class);

	public static final String ISO_NO_T_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String ISO_DATETIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

	public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

	public static final String[] datePattern = new String[] { "MM/dd/yyyy", ISO_DATE_FORMAT };

}

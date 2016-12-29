package com.wen.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期相关工具类.
 * 
 * @author huwenwen
 *
 */
public class DateUtil {

	public static final String ISO_NO_T_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String ISO_DATETIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

	public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

	public static final String[] datePattern = new String[] { "MM/dd/yyyy", ISO_DATE_FORMAT };

	public static String dateFormatString(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String dateFormatDateString(Date date){
		return dateFormatString(date, ISO_DATE_FORMAT);
	}

	public static Date dateFormatDate(Date date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(ISO_DATE_FORMAT);
		return sdf.parse(sdf.format(date));
	}

}

package com.eleme.util;

/**
 * 字符处理工具类
 * 
 * @author yonglin.zhu
 *
 */
public class StringUtils {

	/**
	 * 处理手机号码：手机号显示前3后4位
	 * 
	 * @param str
	 * @return
	 */
	public static String phoneSecretTo(String str) {

		if (str == null)
			str = "";
		StringBuilder sb2 = new StringBuilder(str);

		if (sb2.length() > 3) {
			sb2.replace(3, sb2.length() - 4, "****");
		} else {
			sb2.replace(0, sb2.length(), "*");
		}

		return sb2.toString();
	}
	
	/**
	 * 处理身份证：身份证显示前8位
	 * 
	 * @param str
	 * @return
	 */
	public static String idSecretTo(String str) {

		if (str == null)
			str = "";
		StringBuilder sb2 = new StringBuilder(str);

		if (sb2.length() > 8) {
			sb2.replace(8, sb2.length(), "**********");
		} else {
			sb2.replace(0, sb2.length(), "*");
		}

		return sb2.toString();
	}

}

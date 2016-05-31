package com.eleme.constants;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 常量类初始化及获取工具类.
 * 
 * @author huwenwen
 *
 */
public class ConfigurableConstants {

	// log实例
	protected static Log log = LogFactory.getLog(ConfigurableConstants.class);
	protected static Properties p = new Properties();

	/**
	 * 静态读入属性文件到Properties p变量中.
	 * 
	 * @param propertyFileName
	 *            属性文件名称.
	 */
	protected static void init(String propertyFileName) {
		InputStream in = null;
		try {
			in = ConfigurableConstants.class.getClassLoader().getResourceAsStream(propertyFileName);
			if (in != null) {
				p.load(in);
			}
		} catch (IOException e) {
			log.error("load " + propertyFileName + " into Constants error!");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("close " + propertyFileName + " error!");
				}
			}
		}
	}

	/**
	 * 根据属性key值获取value.
	 * 
	 * @param key
	 *            属性key值.
	 * @param defaultValue
	 *            当使用property key在properties中取不到值时的默认值.
	 * @return 属性value值.
	 */
	protected static String getProperty(String key, String defaultValue) {
		return p.getProperty(key, defaultValue);
	}

	/**
	 * 根据属性key值获取value,模版的value可带模式，使用参数params来替换.
	 * 
	 * @param key
	 *            属性key值。
	 * @param params
	 *            替换value模版占位符的参数
	 * @return 属性value值
	 */
	protected static String getProperty(String key, Object... params) {
		String value = getProperty(key);
		return MessageFormat.format(value, params);
	}

	/**
	 * 根据属性key值获取value,模版的value可带模式，使用参数params来替换.
	 * 
	 * @param key
	 *            属性key值。
	 * @param defaultValue
	 *            当使用property key在properties中取不到值时的默认值.
	 * @param params
	 *            替换value模版占位符的参数
	 * @return 属性value值
	 */
	protected static String getProperty(String key, String defaultValue, Object... params) {
		String value = getProperty(key, defaultValue);
		return MessageFormat.format(value, params);
	}

	/**
	 * 根据属性key值获取value，取不到值时，默认为空串.
	 * 
	 * @param key
	 *            属性key值.
	 * @return 属性value值.
	 */
	protected static String getProperty(String key) {
		return p.getProperty(key, "");
	}

}

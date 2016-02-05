package com.eleme.util;

import java.util.Map;

/**
 * map 相关工具类.
 * 
 * @author penglau
 *
 */
public class MapUtil {

  /**
   * 根据传入键值得到map中的值是否为空.
   * 
   * @param key 键值.
   * @param map 值集合.
   * @return 返回map集合中key的值是否为空，若不为空返回true,否则返回false.
   */
  public synchronized static Boolean verifyMapIsNull(String key, Map<String, Object> map) {
    if (map == null) {
      return false;
    }
    return map.get(key) != null;
  }

}

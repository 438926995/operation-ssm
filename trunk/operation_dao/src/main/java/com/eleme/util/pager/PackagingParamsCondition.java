package com.eleme.util.pager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.eleme.util.DateUtil;
import com.eleme.util.MapUtil;

/**
 * 通用参数封装类,封装分页参数、页码数据，封装使用sql查询出来以Map<String,Object>为对象的集合.
 * 
 * @author huwenwen
 *
 */
public class PackagingParamsCondition {

  /**
   * 根据数据总数量、每页显示数量、当前页码封装PageInfo对象.
   * 
   * @param totalCount 数据总量.
   * @param pageSize 每页显示数量.
   * @param currentPage 当前选择页码.
   * @return 封装好的页码封装对象.
   */
  public synchronized static PgInfo getPage(Integer totalCount, Integer pageSize,
      Integer currentPage) {
    PgInfo pageInfo = new PgInfo();
    pageInfo.setTotalCount(totalCount); // 总共记录数
    pageInfo.setPageSize(pageSize); // 每页大小
    pageInfo.setFirst((currentPage == 1 ? true : false)); // 是否为第一页
    int sumPage = (int) Math.ceil((double) totalCount / pageSize); // 设置总页数
    if (sumPage == 0) { // 是否为最后一页
      pageInfo.setLast(true);
    } else {
      pageInfo.setLast((currentPage == sumPage ? true : false));
    }
    pageInfo.setSumPage(sumPage); // 总页数
    if (sumPage == 0) {
      pageInfo.setPageNo(0); // 当前页
    } else {
      pageInfo.setPageNo(currentPage); // 当前页
    }
    return pageInfo;
  }

  /**
   * 根据实体封装类，将类中的属性和值封装成字符串.
   * 
   * @param arg 参数封装类对象.
   * @param objName 参数封装类对象名称.
   * @return 封装好的字符串.
   */
  public static String packagingPageCondition(Object arg, String objName) throws SecurityException,
      IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    StringBuffer str = new StringBuffer("");
    if (arg == null) {
      return "";
    }
    // 得到参数对象的所有方法.
    Method[] methods = arg.getClass().getMethods();

    for (Method method : methods) {
      // 判断是否是get方法.
      if (method.getName().startsWith("get") && !"getClass".equals(method.getName())) {

        // 得到get方法并将get方法对应的属性名称得到.
        String propertyName = method.getName().substring(3);
        propertyName = propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1);
        Object obj = method.invoke(arg);
        if (obj == null || "".equals(obj.toString())) {
          continue;
        }

        // 根据属性名称拼接参数字符串.
        str.append("&");
        Class<?> classz = method.getReturnType();
        String propertyValue = "";
        if (classz.equals(Date.class)) {
          Date date = (Date) method.invoke(arg);
          propertyValue = DateFormatUtils.ISO_DATETIME_FORMAT.format(date).replace('T', ' ');
        } else {
          propertyValue = method.invoke(arg).toString();
        }
        String param = "";
        if (!StringUtils.isNotEmpty(objName)) {
          param = propertyName + "=" + propertyValue;
        } else {
          param = objName + "." + propertyName + "=" + propertyValue;
        }
        str.append(param);
      }
    }
    return str.toString();
  }


  /**
   * 根据传入类型和数据map集合封装对象.
   * 
   * @param map 数据存数集合.
   * @param objClass 泛型对象类型.
   * @return 封装好的泛型对象.
   */
  public static <T> T packagingMapToObject(Map<String, Object> map, Class<T> objClass)
      throws Exception {
    if (map == null) {
      return null;
    }
    // 得到传入对象的所有方法对象数组.
    T arg = objClass.newInstance();
    Method[] methods = objClass.getMethods();

    // 遍历方法对象数组.
    for (Method method : methods) {

      // 判断方法是否是setter方法并且不是getClass方法.
      if (method.getName().startsWith("set")) {
        String propertyName = method.getName().substring(3);
        Class<?> classz = getMethodReturnType(methods, propertyName);
        Object obj = fillValue(classz, propertyName.toUpperCase(), map);
        method.invoke(arg, obj);
      }
    }
    return (T) arg;
  }

  /**
   * 封装map集合.
   * 
   * @param mapList 数据集合.
   * @param objClass 泛型对象类型.
   * @return 封装好的泛型对象集合.
   */
  public static <T> List<T> packagingMapListToObjectList(List<Map<String, Object>> mapList,
      Class<T> objClass) throws Exception {
    List<T> objList = new LinkedList<T>();
    for (Map<String, Object> map : mapList) {
      T obj = packagingMapToObject(map, objClass);
      objList.add(obj);
    }
    return objList;
  }

  /**
   * 根据类中的方法数组和属性名称查询get方法(boolean类型，is开头)返回值类型.
   * 
   * @param methods 方法数组.
   * @param propertyName 属性名称.
   * @return 封装好的方法getter方法返回的数据类型集合.
   */
  public static Class<?> getMethodReturnType(Method[] methods, String propertyName)
      throws Exception {
    for (Method method : methods) {
      if (method.getName().startsWith("get")) {
        String propertyName2 = method.getName().substring(3);
        if (propertyName.equals(propertyName2) && !"getClass".equals(method.getName())) {
          return method.getReturnType();
        }
      } else if (method.getName().startsWith("is")) {
        String propertyName2 = method.getName().substring(2);
        if (propertyName.equals(propertyName2) && !"getClass".equals(method.getName())) {
          return method.getReturnType();
        }
      }
    }
    return null;
  }

  /**
   * 填充Map集合的值.
   * 
   * @param classz 类型.
   * @param key 键值.
   * @param map 存值的map集合.
   * @return 得到map中已key为键的classz类型值.
   */
  public static Object fillValue(Class<?> classz, String key, Map<String, Object> map)
      throws Exception {
    if (classz != null && map != null) {
      if (classz.toString().equals(Integer.class.toString())) {
        return getMapValueForInteger(key, map);
      } else if (classz.toString().equals(String.class.toString())) {
        return getMapValueForString(key, map);
      } else if (classz.toString().equals(Long.class.toString())) {
        return getMapValueForLong(key, map);
      } else if (classz.toString().equals(Float.class.toString())) {
        return getMapValueForFloat(key, map);
      } else if (classz.toString().equals(Double.class.toString())) {
        return getMapValueForDouble(key, map);
      } else if (classz.toString().equals(Boolean.class.toString())) {
        return getMapValueForBoolean(key, map);
      } else if (classz.toString().equals(Date.class.toString())) {
        return getMapValueForDate(key, map, DateUtil.ISO_NO_T_DATETIME_FORMAT);
      }
    }
    return null;
  }

  /**
   * 根据Map中的键得到值中间验证是否为空并返回相应值.
   * 
   * @param key 键值.
   * @param map 值集合.
   * @return 返回字符串值.
   */
  public synchronized static String getMapValueForString(String key, Map<String, Object> map) {
    return MapUtil.verifyMapIsNull(key, map) ? map.get(key).toString() : "";
  }

  /**
   * 根据Map中的键得到值中间验证是否为空并返回相应值.
   * 
   * @param key 键值.
   * @param map 值集合.
   * @return 返回int类型值.
   */
  public synchronized static Integer getMapValueForInteger(String key, Map<String, Object> map) {
    return MapUtil.verifyMapIsNull(key, map) ? Integer.parseInt(map.get(key).toString()) : null;
  }

  /**
   * 根据Map中的键得到值中间验证是否为空并返回相应值.
   * 
   * @param key 键值.
   * @param map 值集合.
   * @return 返回long类型值.
   */
  public synchronized static Long getMapValueForLong(String key, Map<String, Object> map) {
    return MapUtil.verifyMapIsNull(key, map) ? Long.parseLong(map.get(key).toString()) : null;
  }

  /**
   * 根据Map中的键得到值中间验证是否为空并返回相应值.
   * 
   * @param key 键值.
   * @param map 值集合.
   * @return 返回double类型值.
   */
  public synchronized static Double getMapValueForDouble(String key, Map<String, Object> map) {
    return MapUtil.verifyMapIsNull(key, map) ? Double.parseDouble(map.get(key).toString()) : null;
  }

  /**
   * 根据Map中的键得到值中间验证是否为空并返回相应值.
   * 
   * @param key 键值.
   * @param map 值集合.
   * @return 返回date类型值.
   * @throws ParseException
   */
  public synchronized static Date getMapValueForDate(String key, Map<String, Object> map,
      String format) throws ParseException {
    return MapUtil.verifyMapIsNull(key, map)
        ? DateFormatUtils.ISO_DATE_FORMAT.parse(map.get(key).toString()) : null;
  }

  /**
   * 根据Map中的键得到值中间验证是否为空并返回相应值.
   * 
   * @param key 键值.
   * @param map 值集合.
   * @return 返回float类型值.
   */
  public synchronized static Float getMapValueForFloat(String key, Map<String, Object> map) {
    return MapUtil.verifyMapIsNull(key, map) ? Float.parseFloat(map.get(key).toString()) : null;
  }

  /**
   * 根据Map中的键得到值中间验证是否为空并返回相应值.
   * 
   * @param key 键值.
   * @param map 值集合.
   * @return 返回boolean类型值.
   */
  public synchronized static Boolean getMapValueForBoolean(String key, Map<String, Object> map) {
    return MapUtil.verifyMapIsNull(key, map) ? ("1".equals(map.get(key).toString()) ? true : false)
        : false;
  }

}

package com.eleme.util.pager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 公用分页封装类.
 * 
 * @author penglau
 *
 */
public class TbData {
  /**
   * 查询数据集合.
   */
  private List<?> list;
  /**
   * 页码封装类.
   */
  private PgInfo pageInfo;
  /**
   * 页码封装(下拉框).
   */
  private Map<Integer, Integer> map;
  /**
   * 查询请求action名称.
   */
  private String action;
  /**
   * 请求action的参数,包含问号.
   */
  private String params;
  /**
   * 新版分页的页码条.
   */
  private List<PageInfo> pageList;

  /**
   * init method
   */
  public TbData() {}

  /**
   * 初始化分页数据对象.
   * 
   * @param list 数据集合
   * @param pageInfo 页码条封装对象
   * @param action 请求地址
   * @param obj 请求参数封装类对象
   * @param objName 请求参数封装类在控制器中全局变量属性名称
   */
  public TbData(List<?> list, PgInfo pageInfo, String action, Object obj, String objName)
      throws Exception {
    this.list = list;
    this.pageInfo = pageInfo;
    this.map = getPageTreeMap(pageInfo.getSumPage());
    this.action = action;
    this.params = PackagingParamsCondition.packagingPageCondition(obj, objName);
    pageList = PageInfo.packagingPageParams(pageInfo.getPageNo(), pageInfo.getSumPage());
  }

  /**
   * 封装分页数据到对象中,其中包含页码下拉框.
   * 
   * @param action 请求地址
   * @param obj 请求参数对象.
   * @param objName 请求参数封装类在控制器中全局变量属性名称.
   * @return 封装好的TbData对象.
   */
  public TbData fillTbData(String action, Object obj, String objName) throws Exception {
    this.map = getPageTreeMap(pageInfo.getSumPage());
    this.action = action;
    this.params = PackagingParamsCondition.packagingPageCondition(obj, objName);
    pageList = PageInfo.packagingPageParams(pageInfo.getPageNo(), pageInfo.getSumPage());
    return this;
  }

  /**
   * 按照指定的页，构建TreeMap.
   * 
   * @param sumPage 页码.
   * @return 构建后的TreeMap.
   */
  public static Map<Integer, Integer> getPageTreeMap(Integer sumPage) {
    Map<Integer, Integer> map = new LinkedHashMap<Integer, Integer>(); // TreeMap
    // 构建TreeMap
    for (int i = 1; i <= sumPage; i++) {
      map.put(i, i);
    }
    return map;
  }

  /**
   * 封装分页数据到对象中,其中不包含页码下拉框.
   * 
   * @param action 请求地址.
   * @param obj 请求参数对象.
   * @param objName 请求参数封装类在控制器中全局变量属性名称.
   * @return 封装好的TbData对象.
   */
  public TbData fillTbData2(String action, Object obj, String objName) throws Exception {
    this.action = action;
    this.params = PackagingParamsCondition.packagingPageCondition(obj, objName);
    pageList = PageInfo.packagingPageParams(pageInfo.getPageNo(), pageInfo.getSumPage());
    return this;
  }

  /**
   * 封装分页数据到对象中,传入拼接好的参数字符串.
   * 
   * @param action 请求地址.
   * @param params 拼接好的参数字符串.
   * @return 封装好的TbData对象.
   */
  public TbData fillTbData3(String action, String params) throws Exception {
    this.action = action;
    this.params = params;
    pageList = PageInfo.packagingPageParams(pageInfo.getPageNo(), pageInfo.getSumPage());
    return this;
  }

  public List<?> getList() {
    return list;
  }

  public void setList(List<?> list) {
    this.list = list;
  }

  public PgInfo getPageInfo() {
    return pageInfo;
  }

  public void setPageInfo(PgInfo pageInfo) {
    this.pageInfo = pageInfo;
  }

  public Map<Integer, Integer> getMap() {
    return map;
  }

  public void setMap(Map<Integer, Integer> map) {
    this.map = map;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getParams() {
    return params;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public List<PageInfo> getPageList() {
    return pageList;
  }

  public void setPageList(List<PageInfo> pageList) {
    this.pageList = pageList;
  }

}

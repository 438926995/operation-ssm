package com.eleme.service.sys;

/**
 * 参数接口类型
 * 
 * @author sunwei
 * @since 2015年12月28日
 */
public interface IRelatedParamService {

  /**
   * 
   * 根据参数标记获取系统配置值
   * 
   * @author sunwei
   * @since 2015年12月28日
   * @param flag
   * @return 空值时返回空
   */
  public String getValueByFlag(String flag);

  /**
   * 
   * 修改Value通过查询标记
   * @author sunwei
   * @since 2015年12月28日
   * @param flag 查询标记
   * @param value 更新值
   */
  public void alertValueByFlag(String flag, String value);

}

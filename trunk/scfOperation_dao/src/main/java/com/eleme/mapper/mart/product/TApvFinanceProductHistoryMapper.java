package com.eleme.mapper.mart.product;

import com.eleme.domain.mart.product.TApvFinanceProductHistory;

/**
 * 产品审批历史记录接口
 * 
 * @author yonglin.zhu
 *
 */
public interface TApvFinanceProductHistoryMapper {

  /**
   * 新增审批历史记录
   * 
   * @param record
   * @return
   */
  int insert(TApvFinanceProductHistory apvFinanceProductHistory);

  /**
   * 根据ID查询审批历史记录
   * 
   * @param ahId
   * @return
   */
  TApvFinanceProductHistory selectById(Integer ahId);

}

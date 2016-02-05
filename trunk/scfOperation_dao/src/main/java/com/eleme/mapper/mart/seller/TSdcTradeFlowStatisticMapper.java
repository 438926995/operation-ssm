package com.eleme.mapper.mart.seller;

import java.sql.JDBCType;
import java.util.List;

import com.eleme.domain.mart.seller.TSdcTradeFlowStatistic;

/**
 * 统计商户流水 interface
 * 
 * @author sunwei
 * @since 2015年12月16日
 *
 */
public interface TSdcTradeFlowStatisticMapper {

  /**
   * 根据商户Id删除原先商户流水统计信息
   * 
   * @param sellerId
   */
  public void deleteBySellerId(Integer sellerId);

  /**
   * 批量插入商户流水统计信息
   * 
   * @param list
   */
  public void insertBatch(List<TSdcTradeFlowStatistic> list);

  /**
   * 根据商户id获取流水统计List
   * 
   * @author sunwei
   * @since 2016年1月6日
   * @param sellerId
   * @return
   */
  public List<TSdcTradeFlowStatistic> selectBySellerId(Integer sellerId);

}

package com.eleme.service.seller;

import java.util.List;

import com.eleme.domain.mart.seller.TSdcTradeFlowStatistic;

/**
 * 商户交易流水接口.
 * 
 * @author yonglin.zhu
 *
 */
public interface ITradeFlowService {

  /**
   * 批量插入商户交易流水
   */
  boolean saveTradeFlowBatch(Integer sellerId,Integer month) throws Exception;



  /**
   * 根据商户id 获取流水统计数据
   * 
   * @author sunwei
   * @since 2016年1月6日
   * @param sellerId
   * @return 返回流水统计List
   * @throws Exception
   */
  List<TSdcTradeFlowStatistic> getTradeFlowStatistic(Integer sellerId) throws Exception;
}

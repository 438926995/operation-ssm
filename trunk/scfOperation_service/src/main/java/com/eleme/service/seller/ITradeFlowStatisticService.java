package com.eleme.service.seller;

/**
 * 流水统计service
 * Created by sunwei on 16/1/19.
 */
public interface ITradeFlowStatisticService {


  /**
   * 异步交易流水统计，统计指定sellerId
   *
   * @throws Exception
   */
  void asyncTradeFlowStatistic(Integer sellerId) throws Exception;

}

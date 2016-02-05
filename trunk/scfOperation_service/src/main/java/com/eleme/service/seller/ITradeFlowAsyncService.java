package com.eleme.service.seller;

import org.springframework.util.concurrent.ListenableFuture;

/**
 * 异步获取商户交易流水接口.
 * 
 * @author yonglin.zhu
 *
 */
public interface ITradeFlowAsyncService {

  /**
   * 批量插入商户交易流水
   */
  ListenableFuture<Boolean> saveTradeFlowBatch(Integer naposResId) throws Exception;
}

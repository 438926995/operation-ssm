package com.eleme.service.seller.impl;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.eleme.service.BaseService;
import com.eleme.service.seller.ITradeFlowAsyncService;
import com.eleme.service.seller.ITradeFlowService;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 交易流水实现类。
 * 
 * @author yonglin.zhu
 *
 */
@Service
public class TradeFlowAsyncServiceImpl extends BaseService implements ITradeFlowAsyncService {

  @Inject
  private ITradeFlowService tradeFlowService;
  /**
   * log
   */
  private static Log log = LogFactory.getLog(TradeFlowAsyncServiceImpl.class);

  @Override
  @Async
  public ListenableFuture<Boolean> saveTradeFlowBatch(Integer naposResId) throws Exception {
    log.info("异步调用开始");
    boolean result = tradeFlowService.saveTradeFlowBatch(naposResId,null);
    log.info("异步调用拉去流水结果:", result);
    return new AsyncResult<Boolean>(result);
  }
}

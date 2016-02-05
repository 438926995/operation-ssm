package com.eleme.service.seller.impl;

import com.eleme.domain.mart.seller.TSdcTradeFlow;
import com.eleme.domain.mart.seller.TSdcTradeFlowStatistic;
import com.eleme.mapper.mart.seller.TSdcTradeFlowMapper;
import com.eleme.mapper.mart.seller.TSdcTradeFlowStatisticMapper;
import com.eleme.service.seller.ITradeFlowStatisticService;
import com.eleme.util.CommonUtil;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by sunwei on 16/1/19.
 */
@Service
public class TradeFlowStatisticServiceImpl implements ITradeFlowStatisticService {


  private Log log = LogFactory.getLog(TradeFlowStatisticServiceImpl.class);

  @Inject
  private TSdcTradeFlowMapper tradeFlowMapper;

  @Inject
  private TSdcTradeFlowStatisticMapper tradeFlowStatisticMapper;

  @Async
  public void asyncTradeFlowStatistic(final Integer sellerId) throws Exception {
    try {
      log.info("开始统计{}流水数据", sellerId);
      // 统计结果通过Map来暂存 key存放年份和月份例如2015.8
      Map<String, TSdcTradeFlowStatistic> statisticResultMap =
          new HashMap<String, TSdcTradeFlowStatistic>();

      // 将数据库中的数据一次全部取出，取入内存中进行计算
      List<TSdcTradeFlow> sellerTradeFlowList = tradeFlowMapper.selectBySellerId(sellerId);
      if (sellerTradeFlowList != null && sellerTradeFlowList.size() > 0) {
        for (TSdcTradeFlow tradeFlow : sellerTradeFlowList) {
          Calendar createTime = Calendar.getInstance();
          // 获得订单时间
          createTime.setTime(tradeFlow.getCreatedAt());
          // 获得订单的年份
          Integer year = createTime.get(Calendar.YEAR);
          // 获得订单的月份
          Integer month = createTime.get(Calendar.MONTH);
          String key = year.toString() + "." + month.toString();
          TSdcTradeFlowStatistic statistic = statisticResultMap.get(key);
          if (statistic == null) {
            statistic = new TSdcTradeFlowStatistic(); // 初始化
            statistic.setSellerId(sellerId); // 设置商户id
            statistic.setMonth(month + 1); // 设置月份 java
            // month 从0开始
            statistic.setYear(year); // 设置年份
            statistic.setOrderCount(0); // 设置订单数量
            statistic.setSumTotal(0f); // 设置订单总额初始0
          }
          // 增加一次流水
          statistic.addOrderCount(1);
          // 增加流水金额
          statistic
              .addSumTotal(tradeFlow.getTotal() != null ? (float) tradeFlow.getTotal() / 100 : 0f);
          statisticResultMap.put(key, statistic);
        }
      }

      List<TSdcTradeFlowStatistic> tradeFlowStatisticInsertList =
          new ArrayList<TSdcTradeFlowStatistic>();

      // 定义所有统计
      TSdcTradeFlowStatistic totalStaticResult = new TSdcTradeFlowStatistic();
      totalStaticResult.setType(1);
      totalStaticResult.setSellerId(sellerId);
      // 定义总共统计的天数
      int totalDayCount = 0;

      // 遍历商户流水统计数据 计算日均流水
      Iterator<Map.Entry<String, TSdcTradeFlowStatistic>> iterator =
          statisticResultMap.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry<String, TSdcTradeFlowStatistic> entry = iterator.next();
        TSdcTradeFlowStatistic statistic = entry.getValue();
        totalStaticResult.addSumTotal(statistic.getSumTotal());
        totalStaticResult.addOrderCount(statistic.getOrderCount());
        // 定义本月统计天数
        int thisMonthDayCount = yearMonthGetDayCount(statistic.getYear(), statistic.getMonth());
        totalDayCount += thisMonthDayCount;
        // 计算日均流水额
        statistic.setPerDayTotal(statistic.getSumTotal() / thisMonthDayCount);
        tradeFlowStatisticInsertList.add(statistic);
      }

      if (totalDayCount > 0) {
        totalStaticResult.setPerDayTotal(totalStaticResult.getSumTotal() / totalDayCount);
        tradeFlowStatisticInsertList.add(totalStaticResult);
      }

      // 删除原先商户Id的统计数据
      tradeFlowStatisticMapper.deleteBySellerId(Integer.valueOf(sellerId));
      if (tradeFlowStatisticInsertList != null && tradeFlowStatisticInsertList.size() > 0) {

        // 批量插入商户统计数据
        tradeFlowStatisticMapper.insertBatch(tradeFlowStatisticInsertList);
      }
    } catch (Exception ex) {
      log.error(CommonUtil.getErrorMessage(ex));
    }
  }


  /**
   * 根据年和月获得该月应有天数，为计算日均流水值
   *
   * @param year
   * @param month 月份为实际月份不用减1
   * @return
   */
  private int yearMonthGetDayCount(int year, int month) {
    Calendar calendar = Calendar.getInstance();
    if (year == calendar.get(Calendar.YEAR) && (month - 1 == calendar.get(Calendar.MONTH))) {
      // 如果发现是本月数据 直接返回当前日
      return calendar.get(Calendar.DAY_OF_MONTH);
    } else {
      calendar.set(year, month - 1, 1); // 设置日期
      calendar.roll(Calendar.DAY_OF_MONTH, -1); // 回滚一天
      return calendar.get(Calendar.DAY_OF_MONTH);
    }
  }
}

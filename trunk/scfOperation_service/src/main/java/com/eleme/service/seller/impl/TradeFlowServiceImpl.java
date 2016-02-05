package com.eleme.service.seller.impl;

import com.alibaba.fastjson.JSONObject;
import com.eleme.bean.seller.TradeFlowBean;
import com.eleme.domain.mart.seller.MNaposSeller;
import com.eleme.domain.mart.seller.TSdcTradeFlow;
import com.eleme.domain.mart.seller.TSdcTradeFlowStatistic;
import com.eleme.mapper.mart.seller.ISellerMapper;
import com.eleme.mapper.mart.seller.TSdcTradeFlowMapper;
import com.eleme.mapper.mart.seller.TSdcTradeFlowStatisticMapper;
import com.eleme.service.BaseService;
import com.eleme.service.seller.ITradeFlowService;
import com.eleme.service.seller.ITradeFlowStatisticService;
import com.eleme.util.CommonUtil;
import com.eleme.util.StringUtil;
import me.ele.dt.order.detail.DTOrderDetailService;
import me.ele.dt.order.detail.DTOrderQuery;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

/**
 * 交易流水实现类。
 *
 * @author yonglin.zhu
 */
@Service
public class TradeFlowServiceImpl extends BaseService implements ITradeFlowService {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(TradeFlowServiceImpl.class);

  @Inject
  private DTOrderDetailService orderDetailService;

  @Inject
  private ITradeFlowStatisticService tradeFlowStatisticService;

  @Inject
  private TSdcTradeFlowMapper tradeFlowMapper;

  @Inject
  private TSdcTradeFlowStatisticMapper tradeFlowStatisticMapper;

  @Inject
  private ISellerMapper sellerMapper;

  @Override
  public boolean saveTradeFlowBatch(Integer id, Integer month) throws Exception {
    String lastReadKey = "";
    if (month == null) {
      month = 6;
    }
    // 交易流水开始时间（前6个月1号）
    String dateFrom =
        DateFormatUtils.format(DateUtils.addMonths(new Date(), -month.intValue()), "yyMMdd")
            .substring(0, 4) + "01";
    // 交易流水结束时间
    String dateEnd = DateFormatUtils.format(new Date(), "yyMMdd");

    MNaposSeller naposSeller = sellerMapper.findMNaposSellerBySellerId(id);
    tradeFlowMapper.deleteBySellerId(naposSeller.getSeller_id());
    while (true) {
      // 获取大数据交易流水
      DTOrderQuery paramDTOrderQuery = new DTOrderQuery(id + "", dateFrom, dateEnd, 100);
      paramDTOrderQuery.setLastReadKey(lastReadKey);
      log.info("调用商家订单流水SOA接口。餐厅ID：" + id + ",查询开始时间:" + dateFrom + ",查询结束时间" + dateEnd
          + ",每次最大返回记录数" + 100);
      Map<String, String> map = orderDetailService.query(paramDTOrderQuery);
      if (map != null && map.size() > 0) {
        List<TSdcTradeFlow> list = new ArrayList<TSdcTradeFlow>();
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
          // 将json字符串转换为json对象
          JSONObject obj = JSONObject.parseObject(entry.getValue());
          // 将json对象转换为java对象
          TradeFlowBean bean = (TradeFlowBean) JSONObject.toJavaObject(obj, TradeFlowBean.class);// 将建json对象转换为TradeFlowBean对象
          TSdcTradeFlow tradeFlow = converTSdcTradeFlow(bean, naposSeller.getSeller_id());
          list.add(tradeFlow);
          if (i == map.size() - 1) {
            lastReadKey = entry.getKey();
          }
          i++;
        }
        if (list.size() > 0) {
          tradeFlowMapper.insertBatch(list);
        }
      } else {
        break;
      }
    }
    tradeFlowStatisticService.asyncTradeFlowStatistic(naposSeller.getSeller_id());
    return true;

  }

  /**
   * 把大数据接口数据转化为后台数据
   *
   * @param bean
   * @param sellerId
   * @return
   */
  private TSdcTradeFlow converTSdcTradeFlow(TradeFlowBean bean, Integer sellerId) {
    TSdcTradeFlow tradeFlow = new TSdcTradeFlow();
    // 需要修改
    tradeFlow.setSellerId(sellerId);
    // NAPOS餐厅ID
    if (!StringUtil.isEmpty(bean.getRestaurant_id())) {
      tradeFlow.setOid(Integer.parseInt(bean.getRestaurant_id()));
    }
    // 订单日期
    tradeFlow.setOrderDate(bean.getOrder_date());
    // 订单号
    tradeFlow.setOrderId(bean.getId());
    // 餐厅名
    tradeFlow.setRestaurantName(bean.getRestaurant_name());
    // 餐厅所属ID
    if (!StringUtil.isEmpty(bean.getRst_owner_id())) {
      tradeFlow.setRstOwnerId(Integer.parseInt(bean.getRst_owner_id()));
    }
    // 交易总额
    if (!StringUtil.isEmpty(bean.getTotal())) {
      tradeFlow
          .setTotal((new BigDecimal(bean.getTotal())).multiply(new BigDecimal(100)).intValue());
    }
    // 交易总额
    if (!StringUtil.isEmpty(bean.getEleme_total())) {
      tradeFlow.setElemeTotal(
          (new BigDecimal(bean.getEleme_total())).multiply(new BigDecimal(100)).intValue());
    }
    // 交易完成时间
    tradeFlow.setSettledAt(bean.getSettled_at());
    // 下单时间
    tradeFlow.setCreatedAt(bean.getCreated_at());
    // 来源
    tradeFlow.setSource(bean.getSource());
    // 送货状态
    if (!StringUtil.isEmpty(bean.getDelivery_status())) {
      tradeFlow.setDeliveryStatus(Integer.parseInt(bean.getDelivery_status()));
    }
    // 修改时间
    tradeFlow.setUpdatedAt(bean.getUpdate_time());
    // MD5加密手机号码
    tradeFlow.setMd5Phone1(bean.getMd5_phone_1());
    return tradeFlow;
  }



  @Override
  public List<TSdcTradeFlowStatistic> getTradeFlowStatistic(Integer sellerId) throws Exception {
    List<TSdcTradeFlowStatistic> tradeFlowStatistic =
        tradeFlowStatisticMapper.selectBySellerId(sellerId);
    return tradeFlowStatistic;
  }

}

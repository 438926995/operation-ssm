package com.eleme.mapper.mart.seller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.eleme.domain.mart.seller.TSdcTradeFlow;

/**
 * 商户交易流水接口
 * 
 * @author yonglin.zhu
 *
 */
/**
 * @author sunwei
 * @since 2015年12月10日
 *
 */
public interface TSdcTradeFlowMapper {

  /**
   * 新增交易流水
   * 
   * @param record
   * @return
   */
  int insert(TSdcTradeFlow record);

  /**
   * 批量插入
   * 
   * @param list
   */
  void insertBatch(List<TSdcTradeFlow> list);

  /**
   * 根据主键ID查询商家交易流水
   * 
   * @param tfId
   * @return
   */
  TSdcTradeFlow selectById(Integer tfId);

  /**
   * 根据主键ID修改交易流水
   * 
   * @param record
   * @return
   */
  int updateById(TSdcTradeFlow record);

  /**
   * 根据商户ID删除交易流水
   * 
   * @param sellerId
   */
  void deleteBySellerId(Integer sellerId);

  /**
   * 根据餐厅Id查询交易流水数据
   * 
   * @param oid 实际给人看的Id
   * @return
   */
  List<TSdcTradeFlow> selectByOid(Long oid);

  /**
   * 根据商户Id来查询 商户流水数据
   * 
   * @param sellerId
   * @return
   */
  List<TSdcTradeFlow> selectBySellerId(Integer sellerId);
}

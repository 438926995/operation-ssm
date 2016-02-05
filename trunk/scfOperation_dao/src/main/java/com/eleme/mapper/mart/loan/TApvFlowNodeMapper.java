package com.eleme.mapper.mart.loan;

import java.util.List;

import com.eleme.domain.mart.loan.TApvFlowNode;

/**
 * 金融机构审批节点接口
 * 
 * @author xudong.gu
 *
 */
public interface TApvFlowNodeMapper {

  /**
   * 添加金融机构审批节点
   * 
   * @param record
   * @return
   */
  int insert(TApvFlowNode record);

  /**
   * 根据ID查询 金融机构审批节点
   * 
   * @param nodeID
   * @return
   */
  TApvFlowNode selectById(Integer nodeID);

  /**
   * 根据ID修改金融机构审批节点
   * 
   * @param record
   * @return
   */
  int updateById(TApvFlowNode record);

  /**
   * 
   * @param procsID
   * @return
   */
  List<TApvFlowNode> selectApvNodesByProsID(Integer procsID);

}

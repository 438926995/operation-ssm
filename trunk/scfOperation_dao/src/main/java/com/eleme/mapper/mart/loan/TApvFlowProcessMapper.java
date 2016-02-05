package com.eleme.mapper.mart.loan;

import java.util.List;

import com.eleme.bean.apv.ApvFlowProcessQueryBean;
import com.eleme.domain.mart.loan.TApvFlowProcess;

/**
 * 金融机构审批流接口
 * 
 * @author xudong.gu
 *
 */
public interface TApvFlowProcessMapper {

  /**
   * 添加金融机构审批流
   * 
   * @param record
   * @return
   */
  int insert(TApvFlowProcess record);

  /**
   * 根据ID查询 金融机构审批流
   * 
   * @param procsId
   * @return
   */
  TApvFlowProcess selectById(Integer procsId);

  /**
   * 根据name查询 金融机构审批流
   * 
   * @param procsId
   * @return
   */
  TApvFlowProcess selectByName(String procsName);

  /**
   * 根据ID修改金融机构审批流
   * 
   * @param record
   * @return
   */
  int updateById(TApvFlowProcess record);

  /**
   * 根据条件 查询审批流列表
   * 
   * @param afpqb 查询条件封装
   * @return
   */
  List<TApvFlowProcess> selectApvFlowProcsList(ApvFlowProcessQueryBean afpqb);

  /**
   * 根据条件 查询审批流总数
   * 
   * @param afpqb 查询条件封装
   * @return
   */
  int selectApvFlowprocsTotal(ApvFlowProcessQueryBean afpqb);
  
  /**
   * 查询最新流程信息
   * 
   * @return
   * @throws Exception
   * @author yonglin.zhu
   */
  TApvFlowProcess selectLastestCommonProcess() throws Exception;

}

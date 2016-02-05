package com.eleme.mapper.mart.loan;

import java.util.List;

import com.eleme.bean.apv.ApvFlowTeamQueryBean;
import com.eleme.domain.mart.loan.TApvFlowTeam;

/**
 * 金融机构审批组接口
 * 
 * @author yonglin.zhu
 *
 */
public interface TApvFlowTeamMapper {

  /**
   * 添加金融机构审批组
   * 
   * @param record
   * @return
   */
  int insert(TApvFlowTeam record);

  /**
   * 根据ID查询 金融机构审批组
   * 
   * @param ftId
   * @return
   */
  TApvFlowTeam selectById(Integer ftId);

  /**
   * 根据ID修改金融机构审批组
   * 
   * @param record
   * @return
   */

  /**
   * 
   * @param teamName
   * @return
   */
  TApvFlowTeam selectTeamByName(String teamName);

  /**
   * 更新审批组
   * 
   * @param record
   * @return
   */
  int updateById(TApvFlowTeam record);

  /**
   * 根据审批组名称查询总件数
   * 
   * @param teamName
   * @return
   */
  Integer selectByName(String teamName);

  /**
   * 查找所有审批组信息
   * 
   * @param aftqc team名和分页信息封装
   * @return 不存在返回null
   */
  List<TApvFlowTeam> selectApvFlowTeams(ApvFlowTeamQueryBean aftqb);

  /**
   * 查询team名相同的审批组 总数
   * 
   * @param aftqc team名和分页信息封装
   * @return
   */
  int selectApvFlowTeamTotal(ApvFlowTeamQueryBean aftqb);

}

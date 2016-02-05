package com.eleme.service.flow;

import java.util.List;

import com.eleme.bean.apv.ApvFlowProcessQueryBean;
import com.eleme.bean.apv.ApvFlowTeamQueryBean;
import com.eleme.domain.mart.loan.TApvFlowNode;
import com.eleme.domain.mart.loan.TApvFlowProcess;
import com.eleme.domain.mart.loan.TApvFlowTeam;
import com.eleme.domain.mart.martusers.MMartUsers;
import com.eleme.util.pager.TbData;

/**
 * 流程管理相关接口。
 * 
 * @author penglau
 *
 */
public interface IFlowManageService {

  /**
   * 获得所有审批组信息
   * 
   * @param currentPage 当前页
   * @param aftqb 页面请求信息封装
   * @return
   */
  public List<TApvFlowTeam> getApvFlowTeams(Integer currentPage, ApvFlowTeamQueryBean aftqb)
      throws Exception;

  /**
   * 获得所有审批组信息
   * 
   * @param currentPage 当前页
   * @param aftqb 页面请求信息封装
   * @return
   * @throws Exception
   */
  public TbData getApvFlowTeamList(Integer currentPage, ApvFlowTeamQueryBean aftqb)
      throws Exception;

  /**
   * 根据审批组id 获得审批组信息
   * 
   * @param ftId
   * @return
   */
  public TApvFlowTeam getApvFlowTeamById(Integer ftId) throws Exception;

  /**
   * 插入审批组信息
   * 
   * @param aftb
   * @return
   */
  public int addApvTeam(TApvFlowTeam aftb);

  /**
   * 根据审批组id 更新审批组信息
   * 
   * @param aftb 审批组信息封装
   * @return
   * @throws Exception
   */
  public int updateApvTeam(TApvFlowTeam aftb) throws Exception;

  /**
   * 根据金融机构Id 获得用户信息
   * 
   * @param foId
   * @return
   * @throws Exception
   */
  public List<MMartUsers> getMartUsersByFoId(Integer foId) throws Exception;

  /**
   * 根据审批组名 查询审批组信息
   * 
   * @param teamName
   * @return
   */
  public TApvFlowTeam getTeamByTeamname(String teamName) throws Exception;

  /**
   * 获得审批流信息
   * 
   * @param currentPage 当前页
   * @param afpqb 页面请求信息封装
   * @return
   */
  public List<TApvFlowProcess> getApvFlowProcs(Integer currentPage, ApvFlowProcessQueryBean afpqb)
      throws Exception;

  /**
   * 获得审批流信息
   * 
   * @param currentPage 当前页
   * @param afpqb 页面请求信息封装
   * @return
   */
  public TbData getApvFlowProcsList(Integer currentPage, ApvFlowProcessQueryBean afpqb)
      throws Exception;

  /**
   * 根据id 获得审批流信息
   * 
   * @param procsId
   * @return
   * @throws Exception
   */
  public TApvFlowProcess getApvFlowProcsById(Integer procsId) throws Exception;

  /**
   * 插入审批流
   * 
   * @param afp
   * @return
   */
  public int addApvProcs(TApvFlowProcess afp) throws Exception;

  /**
   * 更新审批流
   * 
   * @param afp
   * @return
   * @throws Exception
   */
  public int updateApvProcs(TApvFlowProcess afp) throws Exception;

  /**
   * 根据 审批流ID 获得审批节点信息
   * 
   * @param procsID
   * @return
   */
  public List<TApvFlowNode> getApvFlowNodes(Integer procsID) throws Exception;

  /**
   * 添加审批流节点
   * 
   * @param afn
   * @return
   */
  public int addApvNode(TApvFlowNode afn) throws Exception;

  /**
   * 根据 节点id 获得审批节点信息
   * 
   * @param nodeID
   * @return
   */
  public TApvFlowNode getApvFlowNodeById(Integer nodeID) throws Exception;

  /**
   * 更新审批节点
   * 
   * @param afn
   * @return
   */
  public int updateApvNode(TApvFlowNode afn) throws Exception;


}

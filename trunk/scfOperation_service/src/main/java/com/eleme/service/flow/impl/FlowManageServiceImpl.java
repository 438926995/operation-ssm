package com.eleme.service.flow.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.eleme.bean.apv.ApvFlowProcessQueryBean;
import com.eleme.bean.apv.ApvFlowTeamQueryBean;
import com.eleme.constants.PagerConstants;
import com.eleme.dao.flow.IFlowManageDao;
import com.eleme.domain.mart.loan.TApvFlowNode;
import com.eleme.domain.mart.loan.TApvFlowProcess;
import com.eleme.domain.mart.loan.TApvFlowTeam;
import com.eleme.domain.mart.martusers.MMartUsers;
import com.eleme.mapper.mart.loan.TApvFlowNodeMapper;
import com.eleme.mapper.mart.loan.TApvFlowProcessMapper;
import com.eleme.mapper.mart.loan.TApvFlowTeamMapper;
import com.eleme.mapper.mart.martusers.MMartUsersMapper;
import com.eleme.mapper.ops.user.IUserMapper;
import com.eleme.service.BaseService;
import com.eleme.service.flow.IFlowManageService;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 流程管理接口实现。
 * 
 * @author penglau
 *
 */
@Service
public class FlowManageServiceImpl extends BaseService implements IFlowManageService {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(FlowManageServiceImpl.class);

  @Inject
  private IFlowManageDao flowManageDaoImpl;

  @Inject
  private TApvFlowTeamMapper apvFlowTeamMapper;

  @Inject
  private TApvFlowProcessMapper apvFlowProcessMapper;

  @Inject
  private TApvFlowNodeMapper apvFlowNodeMapper;

  @Inject
  private MMartUsersMapper mMartUsersMapper;

  @Inject
  private IUserMapper userMapper;

  @Override
  public List<TApvFlowTeam> getApvFlowTeams(Integer currentPage, ApvFlowTeamQueryBean aftqb) {
    // 初始化currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }

    // 封装sql查询条件
    aftqb.setStartPage((currentPage - 1) * PagerConstants.PAGE_SIZE);
    aftqb.setPageSize(PagerConstants.PAGE_SIZE);
    return apvFlowTeamMapper.selectApvFlowTeams(aftqb);
  }

  @Override
  public TbData getApvFlowTeamList(Integer currentPage, ApvFlowTeamQueryBean aftqb)
      throws Exception {
    // 初始化currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }

    // 封装sql查询条件
    aftqb.setStartPage((currentPage - 1) * PagerConstants.PAGE_SIZE);
    aftqb.setPageSize(PagerConstants.PAGE_SIZE);

    int totalCount = apvFlowTeamMapper.selectApvFlowTeamTotal(aftqb);

    List<TApvFlowTeam> apvFlowTeams = apvFlowTeamMapper.selectApvFlowTeams(aftqb);

    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, apvFlowTeams);
  }

  @Override
  public TApvFlowTeam getApvFlowTeamById(Integer ftId) {
    return apvFlowTeamMapper.selectById(ftId);
  }

  @Override
  public List<MMartUsers> getMartUsersByFoId(Integer foId) throws Exception {
    return mMartUsersMapper.selectMartUsersByFoId(foId);
  }

  @Override
  public TApvFlowTeam getTeamByTeamname(String teamName) {
    return apvFlowTeamMapper.selectTeamByName(teamName);

  }

  @Override
  public int addApvTeam(TApvFlowTeam aftb) {
    return apvFlowTeamMapper.insert(aftb);
  }

  @Override
  public int updateApvTeam(TApvFlowTeam aftb) throws Exception {
    return apvFlowTeamMapper.updateById(aftb);
  }

  @Override
  public List<TApvFlowProcess> getApvFlowProcs(Integer currentPage, ApvFlowProcessQueryBean afpqb)
      throws Exception {
    // 初始化currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }

    // 封装sql查询条件
    afpqb.setStartPage((currentPage - 1) * PagerConstants.PAGE_SIZE);
    afpqb.setPageSize(PagerConstants.PAGE_SIZE);
    return apvFlowProcessMapper.selectApvFlowProcsList(afpqb);
  }

  @Override
  public TbData getApvFlowProcsList(Integer currentPage, ApvFlowProcessQueryBean afpqb)
      throws Exception {
    // 初始化currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }

    // 封装sql查询条件
    afpqb.setStartPage((currentPage - 1) * PagerConstants.PAGE_SIZE);
    afpqb.setPageSize(PagerConstants.PAGE_SIZE);

    int totalCount = apvFlowProcessMapper.selectApvFlowprocsTotal(afpqb);

    List<TApvFlowProcess> apvFlowProcsList = apvFlowProcessMapper.selectApvFlowProcsList(afpqb);

    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, apvFlowProcsList);
  }

  @Override
  public TApvFlowProcess getApvFlowProcsById(Integer procsId) throws Exception {

    return apvFlowProcessMapper.selectById(procsId);
  }

  @Override
  public int addApvProcs(TApvFlowProcess afp) throws Exception {
    return apvFlowProcessMapper.insert(afp);
  }

  @Override
  public int updateApvProcs(TApvFlowProcess afp) throws Exception {
    return apvFlowProcessMapper.updateById(afp);
  }

  @Override
  public List<TApvFlowNode> getApvFlowNodes(Integer procsID) {
    return apvFlowNodeMapper.selectApvNodesByProsID(procsID);
  }

  @Override
  public int addApvNode(TApvFlowNode afn) throws Exception {
    return apvFlowNodeMapper.insert(afn);
  }

  @Override
  public TApvFlowNode getApvFlowNodeById(Integer nodeID) throws Exception {
    return apvFlowNodeMapper.selectById(nodeID);
  }

  @Override
  public int updateApvNode(TApvFlowNode afn) throws Exception {
    return apvFlowNodeMapper.updateById(afn);
  }

}

package com.eleme.dao.flow.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.eleme.dao.ICommonDao;
import com.eleme.dao.flow.IFlowManageDao;

/**
 * 流程管理的dao层实现类。
 * 
 * @author penglau
 *
 */
@Repository
public class FlowManageDaoImpl implements IFlowManageDao {

  @Inject
  private ICommonDao commonDaoImpl;


}

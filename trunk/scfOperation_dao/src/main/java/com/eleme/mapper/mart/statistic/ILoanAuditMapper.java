package com.eleme.mapper.mart.statistic;

import java.util.List;
import java.util.Map;

import com.eleme.bean.statistic.ProductName;
import com.eleme.domain.mart.statistic.LoanAudit;

/**
 * 贷款审核统计接口
 * 
 * @author huwenwen
 *
 */
public interface ILoanAuditMapper {
  /**
   * 查询所有的贷款申请
   * 
   * @return 贷款审核统计集合
   */
  public List<LoanAudit> loanAuditList(Map<String, Object> map);

  /**
   * 查询产品名称List
   */
  public List<ProductName> productList(Map<String, Object> map);

  /**
   * 查询总共的申请记录
   * 
   * @return
   */
  public List<LoanAudit> selectTotalApplyInfo();

  /**
   * 查询总数
   */
  public int LoanTotalNum(Map<String, Object> map);

}

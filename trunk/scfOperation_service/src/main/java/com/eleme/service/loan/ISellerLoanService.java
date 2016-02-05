package com.eleme.service.loan;

import com.eleme.domain.mart.loan.LoanQueryCnd;
import com.eleme.domain.mart.loan.TAppSellerLoan;
import com.eleme.domain.mart.loan.TAppSellerLoanVo;
import com.eleme.util.pager.TbData;

/**
 * 商户借贷接口
 * 
 * @author yonglin.zhu
 *
 */
public interface ISellerLoanService {

  /**
   * 根据条件分页查询商户借贷信息。查询条件：产品名称,所属机构;排序:默认;
   * 
   * @param pqc 条件封装类
   * @return 融资产品集合
   */
  TbData selectSellerLoanList(LoanQueryCnd cnd, Integer currentPage) throws Exception;

  /**
   * 根据ID审核贷款
   * 
   * @param approveFlag 0:不通过,1:通过
   * @param slId
   * @param userId
   * @param remark
   * @return
   * @throws Exception
   */
  boolean approveSellerLoan(String approveFlag, Integer slId, Integer userId, String remark)
      throws Exception;

  /**
   * 根据贷款ID查询贷款审核信息
   * 
   * @param slId
   * @return
   */
  TAppSellerLoanVo selectSellerLoanById(Integer slId, Integer userId);
  
  /**
   * 修改贷款申请信息
   * 
   * @param record
   * @return
   * @throws Exception
   */
  int updateById(TAppSellerLoan record) throws Exception;

}

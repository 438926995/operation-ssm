package com.eleme.mapper.mart.loan;

import java.util.List;

import com.eleme.domain.mart.loan.LoanQueryCnd;
import com.eleme.domain.mart.loan.TAppSellerLoan;
import com.eleme.domain.mart.loan.TAppSellerLoanVo;

/**
 * 商户借贷申请接口
 * 
 * @author a
 *
 */
public interface TAppSellerLoanMapper {

	/**
	 * 新增商户借贷申请
	 * 
	 * @param record
	 * @return
	 */
	int insert(TAppSellerLoan record);

	/**
	 * 根据ID查询商户借贷申请
	 * 
	 * @param slId
	 * @return
	 */
	TAppSellerLoan selectById(Integer slId);

	/**
	 * 修改商户借贷申请
	 * 
	 * @param record
	 * @return
	 */
	int updateById(TAppSellerLoan record);

	/**
	 * 根据条件查询商户借贷申请信息
	 * 
	 * @param cnd
	 * @return
	 */
	List<TAppSellerLoanVo> selectSellerLoanList(LoanQueryCnd cnd);

	/**
	 * 根据条件查询商户借贷申请信息件数
	 * 
	 * @param cnd
	 * @return
	 */
	Integer selectSellerLoanListCount(LoanQueryCnd cnd);
	
	List<TAppSellerLoanVo> selectSellerLoanById(Integer sid);
}
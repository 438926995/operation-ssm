package com.eleme.mapper.mart.loan;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eleme.domain.mart.loan.PurposeLoanQueryCnd;
import com.eleme.domain.mart.loan.PurposeLoanStatisticsVo;
import com.eleme.domain.mart.loan.TAppPurposeLoan;
import com.eleme.domain.mart.loan.TAppPurposeLoanVo;

/**
 * 商户借贷意向申请接口
 * 
 * @author yonglin.zhu
 *
 */
public interface TAppPurposeLoanMapper {

  /**
   * 添加商户借贷意向申请
   * 
   * @param record
   * @return
   */
  int insert(TAppPurposeLoan record);

  /**
   * 根据主键ID查询商户借贷意向申请
   * 
   * @param plId
   * @return
   */
  TAppPurposeLoan selectById(Integer plId);

  /**
   * 根据主键ID修改商户借贷意向申请
   * 
   * @param record
   * @return
   */
  int updateById(TAppPurposeLoan record);

  /**
   * 根据条件查询商户借贷意向申请
   * 
   * @param cnd
   * @return
   * @throws Exception
   */
  List<TAppPurposeLoanVo> selectPurposeLoanListByCnd(PurposeLoanQueryCnd cnd) throws Exception;

  /**
   * 根据条件查询商户借贷意向申请件数
   * 
   * @param cnd
   * @return
   * @throws Exception
   */
  Integer selectSellerLoanListCount(PurposeLoanQueryCnd cnd) throws Exception;

  /**
   * 查询商户借贷意向申请信息统计信息
   * 
   * @return
   * @throws Exception
   */
  List<PurposeLoanStatisticsVo> selectSellerLoanStatistics() throws Exception;

  /**
   * 根据查询条件 查询商户借贷意向申请信息统计信息
   * 
   * @param cnd 查询条件
   * @return
   * @throws Exception
   */
  List<PurposeLoanStatisticsVo> selectSellerLoanStatisticsByCnd(PurposeLoanQueryCnd cnd)
      throws Exception;

  /**
   * 管理平台-未开通城市贷款分配
   * 
   * @param fpId
   * @param plIds
   * @return
   * @throws Exception
   */
  int assignOrg(@Param("fpId") Integer fpId, @Param("plIds") Integer[] plIds) throws Exception;

  /**
   * 管理平台-未开通城市贷款分配
   * 
   * @param plIds
   * @return
   * @throws Exception
   */
  int updateForAssignOrg(@Param("plIds") Integer[] plIds) throws Exception;

  /**
   * 根据主键ID查询无城市贷款申请详情
   * 
   * @param cnd
   * @return
   * @throws Exception
   */
  TAppPurposeLoanVo selectPurposeLoanDetail(Integer plId) throws Exception;

  /**
   * 根据主键集合查询商户ID集合
   * 
   * @param plIds
   * @return
   * @throws Exception
   */
  List<TAppPurposeLoanVo> selectNaposResIdsByPlids(@Param("plIds") Integer[] plIds)
      throws Exception;

  /**
   * 根据条件查询商户借贷意向申请金额总和
   * 
   * @param cnd
   * @return
   */
  BigDecimal selectLoanAmountSum(PurposeLoanQueryCnd cnd);

  /**
   * 查询所有未开通城市贷款申请重复数据
   * 
   * @return
   */
  List<TAppPurposeLoanVo> selectAllRepeatPurposeLoan(PurposeLoanQueryCnd cnd);
  
  /**
   * 查询所有未开通城市贷款申请重复数据总件数
   * @return
   */
  Integer selectAllRepeatPurposeLoanCount(PurposeLoanQueryCnd cnd);
}

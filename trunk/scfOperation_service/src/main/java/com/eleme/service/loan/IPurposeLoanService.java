package com.eleme.service.loan;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eleme.domain.mart.loan.ExportPurposeLoanQueryCnd;
import com.eleme.domain.mart.loan.PurposeLoanQueryCnd;
import com.eleme.domain.mart.loan.PurposeLoanStatisticsDetail;
import com.eleme.domain.mart.loan.PurposeLoanStatisticsVo;
import com.eleme.domain.mart.loan.TAppPurposeLoan;
import com.eleme.domain.mart.loan.TAppPurposeLoanVo;
import com.eleme.util.pager.TbData;

/**
 * 商户借贷意向申请接口
 *
 * @author yonglin.zhu
 */
public interface IPurposeLoanService {


  /**
   * 根据条件查询
   *
   * @param cnd
   * @param currentPage
   * @return
   * @throws Exception
   */
  TbData selectPurposeLoanList(PurposeLoanQueryCnd cnd, Integer currentPage) throws Exception;

  /**
   * 根据查询条件导出Excel
   *
   * @param cnd
   * @throws Exception
   * @author sunwei
   * @since 2016年1月12日
   */
  XSSFWorkbook exportPurposeLoanList(ExportPurposeLoanQueryCnd cnd) throws Exception;

  /**
   * 查询商户借贷意向申请信息统计信息
   *
   * @return
   * @throws Exception
   */
  List<PurposeLoanStatisticsVo> selectSellerLoanStatistics() throws Exception;

  /**
   * 查询商户借贷意向申请信息统计信息总件数
   *
   * @return
   * @throws Exception
   */
  Integer selectSellerLoanListCount(PurposeLoanQueryCnd cnd) throws Exception;

  /**
   * 管理平台-未开通城市贷款分配
   *
   * @param fpId
   * @param plIds
   * @return
   * @throws Exception
   */
  int assignOrg(Integer fpId, Integer[] plIds) throws Exception;

  /**
   * 根据意向商户信息 获得 具体未开通省份,城市申请数量的信息
   *
   * @return 没有则返回空列表
   */
  List<PurposeLoanStatisticsDetail> getPurposeStatisticsDetail(PurposeLoanQueryCnd cnd)
      throws Exception;

  /**
   * 根据主键ID查询无城市贷款申请详情
   *
   * @param plId
   * @return
   * @throws Exception
   */
  TAppPurposeLoanVo selectPurposeLoanDetail(Integer plId) throws Exception;

  /**
   * 导出未开通城市餐厅统计Excel
   * 
   * @param outputStream
   * @throws Exception
   */
  void exportPurposeLoanStatistics(OutputStream outputStream) throws Exception;

  /**
   * 根据未开通城市贷款申请批量拉去流水
   * 
   * @param plIds
   */
  List<TAppPurposeLoanVo> getTradeFlows(Integer[] plIds) throws Exception;

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
  TbData selectAllRepeatPurposeLoan(PurposeLoanQueryCnd cnd, Integer currentPage) throws Exception;

  /**
   * 修改未开通城市贷款信息
   * 
   * @param appPurposeLoan
   * @return
   */
  int updatePurposeLoan(TAppPurposeLoan appPurposeLoan);

}

package com.eleme.service.loan.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eleme.constants.LoanStatus;
import com.eleme.constants.PagerConstants;
import com.eleme.domain.mart.loan.LoanQueryCnd;
import com.eleme.domain.mart.loan.TAppSellerLoan;
import com.eleme.domain.mart.loan.TAppSellerLoanVo;
import com.eleme.domain.mart.loan.TApvHistory;
import com.eleme.domain.mart.loan.TApvHistoryVo;
import com.eleme.domain.mart.product.ProductRuleQueryCnd;
import com.eleme.mapper.mart.loan.TAppSellerLoanMapper;
import com.eleme.mapper.mart.loan.TApvHistoryMapper;
import com.eleme.mapper.mart.product.TFcoRequestRuleMapper;
import com.eleme.service.BaseService;
import com.eleme.service.loan.ISellerLoanService;
import com.eleme.util.pager.TbData;

/**
 * 商户借贷现类。
 * 
 * @author yonglin.zhu
 *
 */
@Service
@Transactional(rollbackFor = java.lang.Throwable.class)
public class SellerLoanServiceImpl extends BaseService implements ISellerLoanService {

  @Inject
  private TAppSellerLoanMapper appSellerLoanMapper;

  @Inject
  private TApvHistoryMapper apvHistoryMapper;

  @Inject
  private TFcoRequestRuleMapper fcoRequestRuleMapper;

  @Transactional(readOnly = true)
  public TbData selectSellerLoanList(LoanQueryCnd cnd, Integer currentPage) throws Exception {
    // 第一次进入时，currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }
    // 封装sql查询条件
    if (cnd.getSubmitTimeEnd() != null) {
      cnd.setSubmitTimeEnd(DateUtils.parseDate(
          DateFormatUtils.format(cnd.getSubmitTimeEnd(), "yyyy/MM/dd") + " 23:59:59",
          "yyyy/MM/dd HH:mm:ss"));
    }
    cnd.setStartRecord((currentPage - 1) * PagerConstants.PAGE_SIZE);
    cnd.setPageSize(PagerConstants.PAGE_SIZE);
    List<TAppSellerLoanVo> loanList = appSellerLoanMapper.selectSellerLoanList(cnd);
    int totalCount = appSellerLoanMapper.selectSellerLoanListCount(cnd);
    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, loanList);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Throwable.class)
  public boolean approveSellerLoan(String approveFlag, Integer slId, Integer userId, String remark)
      throws Exception {
    // 根据ID查询商户借贷申请
    TAppSellerLoan sellerLoan = appSellerLoanMapper.selectById(slId);
    if (sellerLoan != null) {
      List<TApvHistoryVo> historyList = apvHistoryMapper.selectBySlId(slId);
      if (historyList != null && historyList.size() > 2) {
        // 后台审核记录
        TApvHistory historyOpt = historyList.get(0);
        // 个人审核记录
        TApvHistory historyPerson = historyList.get(1);
        // 审核通过
        if ("1".equals(approveFlag)) {
          // 后台审核记录的状态为P，并且个人审核记录的审核状态为B
          if (LoanStatus.Status_P.getIndex().equals(historyOpt.getAppStatus())
              && LoanStatus.Status_B.getIndex().equals(historyPerson.getAppStatus())) {
            // 修改后台审核记录
            TApvHistory historyOptUpd = new TApvHistory();
            historyOptUpd.setAhId(historyOpt.getAhId());
            historyOptUpd.setAppStatus(LoanStatus.Status_C.getIndex());
            historyOptUpd.setAppDate(new Date());
            historyOptUpd.setReallyAppUserId(userId);
            historyOptUpd.setOpinions(remark);
            apvHistoryMapper.updateById(historyOptUpd);
            // 修改个人审核记录
            TApvHistory historyPersonUpd = new TApvHistory();
            historyPersonUpd.setAhId(historyPerson.getAhId());
            historyPersonUpd.setAppStatus(LoanStatus.Status_P.getIndex());
            apvHistoryMapper.updateById(historyPersonUpd);
            return true;
          }
        } else {
          // 后台审核记录的状态为P，并且个人审核记录的审核状态为B
          if (LoanStatus.Status_P.getIndex().equals(historyOpt.getAppStatus())
              && LoanStatus.Status_B.getIndex().equals(historyPerson.getAppStatus())) {
            // 修改后台审核记录
            TApvHistory historyOptUpd = new TApvHistory();
            historyOptUpd.setAhId(historyOpt.getAhId());
            historyOptUpd.setAppStatus(LoanStatus.Status_D.getIndex());
            historyOptUpd.setAppDate(new Date());
            historyOptUpd.setReallyAppUserId(userId);
            historyOptUpd.setOpinions(remark);
            apvHistoryMapper.updateById(historyOptUpd);
            // 修改个人审核记录
            TApvHistory historyPersonUpd = new TApvHistory();
            historyPersonUpd.setAhId(historyPerson.getAhId());
            historyPersonUpd.setAppStatus(LoanStatus.Status_P.getIndex());
            apvHistoryMapper.updateById(historyPersonUpd);
            // 贷款申请不通过
            TAppSellerLoan sellerLoanUpd = new TAppSellerLoan();
            sellerLoanUpd.setSlId(sellerLoan.getSlId());
            sellerLoanUpd.setAppStatus(LoanStatus.Status_D.getIndex());
            appSellerLoanMapper.updateById(sellerLoanUpd);
            return true;
          }
        }
      }
    }
    return false;
  }

  @Transactional(readOnly = true)
  public TAppSellerLoanVo selectSellerLoanById(Integer slId, Integer userId) {
    // 查询商户贷款申请
    List<TAppSellerLoanVo> sellerLoanList = appSellerLoanMapper.selectSellerLoanById(slId);
    TAppSellerLoanVo sellerLoan = null;
    if (sellerLoanList != null && sellerLoanList.size() > 0) {
      sellerLoan = sellerLoanList.get(0);
    }
    if (sellerLoan != null) {
      // 查询贷款申请记录
      List<TApvHistoryVo> historyList = apvHistoryMapper.selectBySlId(slId);
      sellerLoan.setHistoryList(historyList);
      if (historyList != null && historyList.size() > 2) {
        // 后台审核记录
        TApvHistoryVo historyOpt = historyList.get(0);
        if (userId != null && LoanStatus.Status_P.getIndex().equals(historyOpt.getAppStatus())
            && historyOpt.getIsOps() != null && historyOpt.getIsOps().intValue() == 1
            && historyOpt.getAppUserIds() != null
            && historyOpt.getAppUserIds().contains(userId.toString())) {
          sellerLoan.setIsVisable(1);
        }
      }
      // 查询贷款贷款产品规则信息
      ProductRuleQueryCnd cnd = new ProductRuleQueryCnd();
      cnd.setFpId(sellerLoan.getFpId());
      cnd.setIsMobileUse("-1");
      sellerLoan.setRuleList(fcoRequestRuleMapper.selectRuleListByFpId(cnd));
    }
    return sellerLoan;
  }

  @Override
  public int updateById(TAppSellerLoan record) throws Exception {
    return appSellerLoanMapper.updateById(record);
  }

}

package com.eleme.service.loan.impl;

import com.eleme.bean.loan.ApplyLoan;
import com.eleme.bean.loan.LoanApvBean;
import com.eleme.bean.loan.LoanQueryBean;
import com.eleme.mapper.ops.loan.LoanAuditMapper;
import com.eleme.service.BaseService;
import com.eleme.service.loan.ILoanService;
import com.eleme.util.pager.TbData;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by huwenwen on 16/5/14.
 */
@Service
public class LoanServiceImpl extends BaseService implements ILoanService{

    @Inject
    private LoanAuditMapper loanAuditMapper;

    @Override
    public TbData getApplyLoan(LoanQueryBean lqb) {
        List<ApplyLoan> applyLoan = loanAuditMapper.selectApplyLoan(lqb);
        int total = loanAuditMapper.selectApplyLoanTotal(lqb);
        return initTbData(total, lqb.getCurrentPage(), lqb.getPageSize(), applyLoan);
    }

    @Override
    public ApplyLoan getApplyLoanBySlId(Integer slId) {
        return loanAuditMapper.selectApplyLoanBySlId(slId);
    }

    @Override
    public int approveLoan(LoanApvBean lab) {
        return loanAuditMapper.updateApplyLoanToApv(lab);
    }

    @Override
    public int deleteLoanBySlId(Integer slId) {
        // 删除
        int line = loanAuditMapper.updateApplyLoanToDel(slId);
        // 记录删除历史
        return line;
    }
}

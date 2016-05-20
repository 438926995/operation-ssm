package com.eleme.mapper.ops.loan;

import com.eleme.bean.loan.*;

import java.util.List;

/**
 * Created by huwenwen on 16/5/14.
 */
public interface LoanAuditMapper {

    public List<LoanAudit> selectLoanAudit(LoanQueryBean lqb);

    public List<ApplyLoan> selectApplyLoan(LoanQueryBean lqb);

    public int selectApplyLoanTotal(LoanQueryBean lqb);

    public ApplyLoanVo selectApplyLoanBySlId(Integer slId);

    public int updateApplyLoanToApv(LoanApvBean lab);

    public int updateApplyLoanToDel(Integer slId);
}

package com.eleme.service.loan;

import com.eleme.bean.loan.ApplyLoan;
import com.eleme.bean.loan.LoanApvBean;
import com.eleme.bean.loan.LoanQueryBean;
import com.eleme.util.pager.TbData;

/**
 * Created by huwenwen on 16/5/14.
 */
public interface ILoanService {

    public TbData getApplyLoan(LoanQueryBean lqb);

    public ApplyLoan getApplyLoanBySlId(Integer slId);

    public int approveLoan(LoanApvBean lab);

    public int deleteLoanBySlId(Integer slId);
}

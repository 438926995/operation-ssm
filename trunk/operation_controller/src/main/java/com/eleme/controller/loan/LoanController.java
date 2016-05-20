package com.eleme.controller.loan;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.bean.JSONMessage;
import com.eleme.bean.loan.ApplyLoan;
import com.eleme.bean.loan.LoanApvBean;
import com.eleme.bean.loan.LoanQueryBean;
import com.eleme.bean.product.MFinanceProduct;
import com.eleme.controller.BaseController;
import com.eleme.service.loan.ILoanService;
import com.eleme.service.product.IProductService;
import com.eleme.util.pager.TbData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huwenwen on 16/5/14.
 */
@Controller
@RequestMapping(value = "/loan")
public class LoanController extends BaseController {

    @Inject
    private ILoanService loanService;

    @Inject
    private IProductService productService;

    /**
     * 贷款列表
     * @param request
     * @param response
     * @param lqb
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @UserMenu
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response, LoanQueryBean lqb) throws Exception {
        ModelAndView mav = new ModelAndView("loan/list");
        TbData tbData = loanService.getApplyLoan(lqb);
        tbData = tbData.fillTbData("loan/list", lqb, null);
        mav.addObject("tbData", tbData);
        mav.addObject("productList", productService.getAllProductList());
        mav.addObject("lqb", lqb);
        return mav;
    }

    /**
     * 申请详情
     * @param request
     * @param response
     * @param slId
     * @return
     */
    @RequestMapping(value = "/detail/{slId}")
    @UserMenu
    public ModelAndView loanDetail(HttpServletRequest request, HttpServletResponse response,
                                   @PathVariable("slId") Integer slId){
        ModelAndView mav = new ModelAndView("loan/detail");
        ApplyLoan applyLoan = loanService.getApplyLoanBySlId(slId);
        MFinanceProduct product = productService.getProductInfoByFpId(applyLoan.getFpId());
        mav.addObject("loan", applyLoan);
        mav.addObject("product", product);
        return mav;
    }

    /**
     * 审批贷款审批
     * @param request
     * @param response
     * @param lab
     * @return
     */
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    @ResponseBody
    public JSONMessage approve(HttpServletRequest request, HttpServletResponse response,
                               LoanApvBean lab){
        int userId = getSessionBean(request).getUserId().intValue();
        lab.setApvUserId(userId);
        int line = loanService.approveLoan(lab);
        if(line > 0){
            return new JSONMessage(true, "审批成功");
        }
        return new JSONMessage(false, "审批失败");
    }

    /**
     * 删除贷款记录
     * @param request
     * @param response
     * @param slId
     * @return
     */
    @RequestMapping(value="deleteLoan/{slId}", method = RequestMethod.POST)
    @ResponseBody
    public JSONMessage deleteLoan(HttpServletRequest request, HttpServletResponse response,
                                  @PathVariable("slId") Integer slId){
        int line = loanService.deleteLoanBySlId(slId);
        if(line > 0){
            return new JSONMessage(true, "删除成功");
        }
        return new JSONMessage(false, "删除失败");
    }
}

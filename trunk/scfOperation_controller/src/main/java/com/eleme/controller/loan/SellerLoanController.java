package com.eleme.controller.loan;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.annotation.form.AvoidDuplicateSubmission;
import com.eleme.bean.JSONMessage;
import com.eleme.bean.SessionBean;
import com.eleme.bean.loan.LoanQueryBean;
import com.eleme.constants.ProductStatus;
import com.eleme.constants.StringConstants;
import com.eleme.controller.BaseController;
import com.eleme.domain.mart.loan.LoanQueryCnd;
import com.eleme.domain.mart.loan.TAppSellerLoan;
import com.eleme.domain.mart.loan.TAppSellerLoanVo;
import com.eleme.domain.mart.product.MFinanceProductVo;
import com.eleme.domain.mart.product.ProductQueryCnd;
import com.eleme.service.basic.ICityService;
import com.eleme.service.loan.ISellerLoanService;
import com.eleme.service.product.IProductService;
import com.eleme.service.seller.ITradeFlowAsyncService;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 商户借贷控制器。
 * 
 * @author yonglin.zhu
 *
 */
@Controller
@RequestMapping(value = "/loan")
public class SellerLoanController extends BaseController {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(SellerLoanController.class);

  @Inject
  private IProductService productService;

  @Inject
  private ISellerLoanService sellerLoanService;

  @Inject
  private ITradeFlowAsyncService tradeFlowAsyncService;

  @Inject
  private ICityService cityService;

  /**
   * 跳转到商户贷款管理的页面.
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
      LoanQueryBean lqb, Integer currentPage) throws Exception {
    ModelAndView mav = new ModelAndView("loan/list");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到商户贷款管理界面", session.getUserName());
    // 查询产品信息
    ProductQueryCnd cnd = new ProductQueryCnd();
    // 已发布
    cnd.setProductStatus(ProductStatus.Status_1.getIndex());
    List<MFinanceProductVo> productList = productService.selectProductList(cnd);
    LoanQueryCnd loanCnd = new LoanQueryCnd();
    BeanUtils.copyProperties(loanCnd, lqb);
    TbData tbData = sellerLoanService.selectSellerLoanList(loanCnd, currentPage);
    tbData = tbData.fillTbData("loan/list", lqb, null);
    mav.addObject("provinceList", cityService.getProvinceList());
    mav.addObject("tbData", tbData);
    mav.addObject("productList", productList);
    mav.addObject("lqb", lqb);
    if (lqb.getProvinceId() != null) {
      mav.addObject("cityList", cityService.getCityListByProvinceId(lqb.getProvinceId()));
    }
    // 返回
    return mav;
  }

  /**
   * 跳转到贷款审核详情画面
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView detailInfo(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer id) throws Exception {
    ModelAndView mav = new ModelAndView("loan/detail");

    // 记录日志
    SessionBean session = getSessionBean(request);
    TAppSellerLoan sellerLoan =
        sellerLoanService.selectSellerLoanById(id, session.getUserId().intValue());
    if (sellerLoan == null) {
      log.info("未找到对应的商户贷款申请");
      mav.setViewName("account/notfound");
    } else {
      log.info("{}跳转到贷款详情界面", session.getUserName());
      mav.addObject("sellerLoan", sellerLoan);
    }
    // 返回
    return mav;
  }

  /**
   * 贷款审核审核
   * 
   * @param request
   * @param response
   * @param approveFlag
   * @param slId
   * @param userId
   * @param remark
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/approve", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage approvePass(HttpServletRequest request, HttpServletResponse response,
      String approveFlag, Integer slId, Integer userId, String remark) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}审核贷款信息,账户ID:{}", session.getUserName(), userId);
    try {
      boolean result = sellerLoanService.approveSellerLoan(approveFlag, slId,
          session.getUserId().intValue(), remark);
      if (!result) {
        return new JSONMessage(false, "审核失败");
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      return new JSONMessage(false, "审核失败");
    }

    return new JSONMessage(true, "审核成功");
  }

  /**
   * 手机端贷款审核调用API
   * 
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/approveMobileApi", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage approveMobileApi(HttpServletRequest request, HttpServletResponse response,
      Integer slId) {
    // 记录日志
    log.info("进入发起自动审核信息请求,申请单ID为:" + slId);
    // 根据贷款ID查询贷款审核信息
    TAppSellerLoanVo sellerLoanVo =
        sellerLoanService.selectSellerLoanById(slId, StringConstants.System_User);
    if (sellerLoanVo != null) {
      // 无产品规则，则自动拉去流水，并且审核通过
      if (sellerLoanVo.getRuleList() == null || sellerLoanVo.getRuleList().size() == 0) {
        // 获取流水
        try {
          ListenableFuture<Boolean> listenableFuture =
              tradeFlowAsyncService.saveTradeFlowBatch(sellerLoanVo.getNaposResId());
          SuccessCallback<Boolean> successCallback = new SuccessCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean ret) {
              if (ret) {
                // 审核操作
                try {
                  Boolean result = sellerLoanService.approveSellerLoan("1", slId,
                      StringConstants.System_User, "系统自动审核");
                  log.info("系统自动审核结果为:" + result);
                } catch (Exception e) {
                  log.error("异步回调失败了, 错误信息:" + e.getMessage());
                }
              }
            }
          };
          FailureCallback failureCallback = new FailureCallback() {
            @Override
            public void onFailure(Throwable throwable) {
              log.error("异步回调失败了, 错误信息: " + throwable.getMessage());
            }
          };
          listenableFuture.addCallback(successCallback, failureCallback);

        } catch (Exception e) {
          logger.error(e.getMessage());
          return new JSONMessage(false, "审核失败,原因为数据异常。");
        }
      }
    } else {
      return new JSONMessage(false, "审核失败,原因为没有贷款审核信息");
    }
    return new JSONMessage(true, "审核成功");
  }

  /**
   * 删除融资产品信息
   * 
   * @param request
   * @param response
   * @param slId 贷款申请id
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/del", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage deleteLoan(HttpServletRequest request, HttpServletResponse response,
      Integer slId) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}删除贷款申请信息,账户ID:{}", session.getUserName(), slId);
    // 修改贷款申请信息
    TAppSellerLoan record = new TAppSellerLoan();
    record.setSlId(slId);
    record.setDocStatus(1);
    // 调用service
    int lines = sellerLoanService.updateById(record);
    // 记录日志
    log.info("{}删除贷款申请信息，影响行数{}", session.getUserName(), lines);
    return new JSONMessage(true, "删除成功");
  }

}

package com.eleme.controller.loan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.annotation.form.AvoidDuplicateSubmission;
import com.eleme.bean.JSONMessage;
import com.eleme.bean.SessionBean;
import com.eleme.bean.loan.PurposeLoanAssignBean;
import com.eleme.bean.loan.PurposeLoanQueryBean;
import com.eleme.controller.BaseController;
import com.eleme.domain.mart.loan.ExportPurposeLoanQueryCnd;
import com.eleme.domain.mart.loan.PurposeLoanQueryCnd;
import com.eleme.domain.mart.loan.PurposeLoanStatisticsDetail;
import com.eleme.domain.mart.loan.TAppPurposeLoan;
import com.eleme.domain.mart.loan.TAppPurposeLoanVo;
import com.eleme.service.basic.ICityService;
import com.eleme.service.finance.IFinanceService;
import com.eleme.service.loan.IPurposeLoanService;
import com.eleme.service.seller.ITradeFlowAsyncService;
import com.eleme.util.DateUtil;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;


/**
 * 商户借贷意向申请控制器。
 *
 * @author yonglin.zhu
 */
@Controller
@RequestMapping(value = "/purpose/loan")
public class PurposeLoanController extends BaseController {

  /**
   * log
   */
  private static Log log = LogFactory.getLog(PurposeLoanController.class);

  @Inject
  private IPurposeLoanService purposeLoanService;
  @Inject
  private ICityService cityService;
  @Inject
  private IFinanceService financeService;

  @Inject
  private ITradeFlowAsyncService tradeFlowAsyncService;


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
      PurposeLoanQueryBean plq, Integer currentPage) throws Exception {
    ModelAndView mav = new ModelAndView("loan/purposelist");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到未开通城市商户贷款管理界面", session.getUserName());
    // 查询商户借贷意向申请信息
    PurposeLoanQueryCnd cnd = new PurposeLoanQueryCnd();
    BeanUtils.copyProperties(cnd,plq);
    TbData tbData = purposeLoanService.selectPurposeLoanList(cnd, currentPage);
    tbData = tbData.fillTbData("purpose/loan/list", plq, null);
    mav.addObject("tbData", tbData);
    mav.addObject("loanAmountSum",
        purposeLoanService.selectLoanAmountSum(cnd).divide(new BigDecimal("1000000")));
    mav.addObject("plq", plq);
    mav.addObject("orgProductList", financeService.selectOrgProductList());
    mav.addObject("provinceList", cityService.getProvinceList());
    if (plq.getProvinceId() != null) {
      mav.addObject("cityList", cityService.getCityListByProvinceId(plq.getProvinceId()));
    }
    // 返回
    return mav;
  }

  /**
   * 导出商户贷款信息Excel
   *
   * @param request
   * @param response
   * @param plq
   * @throws Exception
   * @author sunwei
   * @since 2016年1月12日
   */
  @RequestMapping(value = "/exportList", method = RequestMethod.GET)
  public void exportList(HttpServletRequest request, HttpServletResponse response,
      ExportPurposeLoanQueryCnd plq) throws Exception {
    // 日志
    SessionBean session = getSessionBean(request);
    log.info("{}导出商户贷款信息Excel", session.getUserName());

    String codedFileName = new String("商户信息列表".getBytes("gb2312"), "ISO8859-1");
    // 导出
    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");

    // 查询商户借贷意向申请信息
    ExportPurposeLoanQueryCnd cnd = new ExportPurposeLoanQueryCnd();
    BeanUtils.copyProperties(cnd, plq);

    // 查询数据并返回excel数据流
    XSSFWorkbook workbook = purposeLoanService.exportPurposeLoanList(cnd);
    workbook.write(response.getOutputStream());
  }

  /**
   * 查询商户借贷意向申请信息统计信息
   *
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/getStatisticsInfo", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> getStatisticsInfo(HttpServletRequest request,
      HttpServletResponse response, Date startTime, Date endTime) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查询商户借贷意向申请信息统计信息", session.getUserName());
    // 查询商户借贷意向申请信息统计信息
    // 查询条件
    Map<String, Object> map = new HashMap<String, Object>();
    PurposeLoanQueryCnd cnd = new PurposeLoanQueryCnd();
    cnd.setSubmitTimeFrom(startTime);
    cnd.setSubmitTimeTo(endTime);
    // 统计信息

    List<PurposeLoanStatisticsDetail> details = purposeLoanService.getPurposeStatisticsDetail(cnd);
    if (startTime != null && endTime != null) {
      map.put("startTime", DateFormatUtils.format(startTime, DateUtil.ISO_DATE_FORMAT));
      map.put("endTime", DateFormatUtils.format(endTime, DateUtil.ISO_DATE_FORMAT));
    }
    map.put("details", details);
    // 合计
    map.put("statisticsCount", purposeLoanService.selectSellerLoanListCount(cnd));
    return map;
  }

  /**
   * 未开通城市商户贷款管理-分配机构
   *
   * @param request
   * @param plab
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/assignOrg", method = RequestMethod.POST)
  @AvoidDuplicateSubmission(needRemoveToken = true)
  public ModelAndView assignOrg(HttpServletRequest request,
      @ModelAttribute PurposeLoanAssignBean plab) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}:未开通城市商户贷款管理-分配机构", session.getUserName());
    try {
      // 拉去商户交易流水
      // 选中拉去流水的情况
      if ("1".equals(plab.getIsGetFlow())) {
        List<TAppPurposeLoanVo> appPurposeLoanVos =
            purposeLoanService.getTradeFlows(plab.getPlIds());
        for (TAppPurposeLoanVo tAppPurposeLoanVo : appPurposeLoanVos) {
          // 异步拉去流水
          ListenableFuture<Boolean> listenableFuture =
              tradeFlowAsyncService.saveTradeFlowBatch(tAppPurposeLoanVo.getNaposResId());
          SuccessCallback<Boolean> successCallback = new SuccessCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean ret) {
              if (ret) {
                // 审核操作
                try {
                  log.info("流水拉去成功。餐厅ID:" + tAppPurposeLoanVo.getNaposResId());
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
        }
      }
    } catch (Exception e) {
      log.error("流水拉去失败。");
    }
    // 调用service，得到影响行数
    int lines = purposeLoanService.assignOrg(plab.getFpId(), plab.getPlIds());
    // 记录日志
    log.info("{}:未开通城市商户贷款管理-分配机构(fpid:{}-plids:{})，影响行数{}", session.getUserName(), plab.getFpId(),
        plab.getPlIds(), lines);
    // 返回
    mav.setViewName("redirect:/purpose/loan/list");
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
  @UserMenu
  public ModelAndView detailInfo(HttpServletRequest request, HttpServletResponse response,
      @PathVariable Integer id) throws Exception {
    ModelAndView mav = new ModelAndView("loan/purposedetail");

    // 记录日志
    SessionBean session = getSessionBean(request);
    TAppPurposeLoanVo purposeLoan = purposeLoanService.selectPurposeLoanDetail(id);
    if (purposeLoan == null) {
      log.info("未找到对应的商户贷款申请");
      mav.setViewName("account/notfound");
    } else {
      log.info("{}跳转到贷款详情界面", session.getUserName());
      mav.addObject("purposeLoan", purposeLoan);
    }
    // 返回
    return mav;
  }

  @RequestMapping(value = "/exportStatistics", method = RequestMethod.GET)
  public void exportStatistics(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}导出未开通城市统计数据", session.getUserName());
    String codedFileName = new String("未开通城市商户贷款统计表".getBytes("gb2312"), "ISO8859-1");
    // 导出
    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");

    purposeLoanService.exportPurposeLoanStatistics(response.getOutputStream());
  }

  /**
   * 跳转到商户贷款管理的页面.
   *
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/repeatList", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView repeatList(HttpServletRequest request, HttpServletResponse response,
      PurposeLoanQueryBean plq, Integer currentPage) throws Exception {
    ModelAndView mav = new ModelAndView("loan/purposerepeatlist");
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到未开通城市商户贷款重复数据界面", session.getUserName());
    // 查询商户借贷意向申请信息
    PurposeLoanQueryCnd cnd = new PurposeLoanQueryCnd();
    BeanUtils.copyProperties(cnd, plq);
    TbData tbData = purposeLoanService.selectAllRepeatPurposeLoan(cnd, currentPage);
    tbData = tbData.fillTbData("purpose/loan/repeatList", plq, null);
    mav.addObject("tbData", tbData);
    mav.addObject("plq", plq);
    mav.addObject("provinceList", cityService.getProvinceList());
    if (plq.getProvinceId() != null) {
      mav.addObject("cityList", cityService.getCityListByProvinceId(plq.getProvinceId()));
    }
    // 返回
    return mav;
  }

  /**
   * 删除融资产品信息
   * 
   * @param request
   * @param response
   * @param plId 贷款申请id
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/deletePurposeLoan", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONMessage deletePurposeLoan(HttpServletRequest request, HttpServletResponse response,
      Integer plId) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}删除未开通城市贷款申请信息,账户ID:{}", session.getUserName(), plId);
    // 修改贷款申请信息
    TAppPurposeLoan appPurposeLoan = new TAppPurposeLoan();
    appPurposeLoan.setPlId(plId);
    appPurposeLoan.setDocStatus(1);
    // 调用service
    int lines = purposeLoanService.updatePurposeLoan(appPurposeLoan);
    // 记录日志
    log.info("{}删除未开通城市贷款申请信息，影响行数{}", session.getUserName(), lines);
    return new JSONMessage(true, "删除成功");
  }

}

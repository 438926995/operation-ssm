package com.eleme.controller.statistic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.annotation.form.AvoidDuplicateSubmission;
import com.eleme.bean.SessionBean;
import com.eleme.bean.statistic.LoanQueryBean;
import com.eleme.bean.statistic.OptionBean;
import com.eleme.bean.statistic.ProductName;
import com.eleme.bean.statistic.UserFeedbackQuerybean;
import com.eleme.controller.BaseController;
import com.eleme.domain.mart.statistic.LoanNumberBean;
import com.eleme.domain.mart.statistic.UserFeedback;
import com.eleme.service.statistic.ILoanAuditService;
import com.eleme.service.statistic.IUserFeedbackService;
import com.eleme.util.pager.TbData;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 贷款审核控制器
 * 
 * @author huwenwen
 * 
 */
@Controller
@RequestMapping(value = "/statistic")
public class LoanAuditController extends BaseController {
  /**
   * log
   */
  private static Log log = LogFactory.getLog(LoanAuditController.class);

  @Inject
  private ILoanAuditService loanAuditService;

  @Inject
  private IUserFeedbackService userFeedbackService;

  /**
   * 跳转到贷款审核统计的页面.
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @AvoidDuplicateSubmission(needSaveToken = true)
  @UserMenu
  public ModelAndView list(HttpServletRequest request, Integer currentPage, LoanQueryBean lqb)
      throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到贷款审核统计界面", session.getUserName());
    // 查询数据并返回
    ModelAndView mav = new ModelAndView("statistic/list");
    // 初期查一个月数据，全部产品
    if (lqb.getSubmitTimeFrom() == null && lqb.getSubmitTimeEnd() == null) {
      // 获取现在的时间
      Date now = new Date();
      // 获取上个月时间
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DATE, 1);
      calendar.add(Calendar.MONTH, -1);
      int year = calendar.get(Calendar.YEAR);
      int month = calendar.get(Calendar.MONTH) + 1;
      int day = calendar.get(Calendar.DATE);
      String lastMonth = year + "-" + month + "-" + day;
      Date lastDate = sdf.parse(lastMonth);
      lqb.setSubmitTimeFrom(lastDate);
      lqb.setSubmitTimeEnd(now);
    }
    TbData tbData = loanAuditService.getStatisticList(currentPage, lqb, true);
    // 趋势图数据获得，不分页
    OptionBean opt = loanAuditService.getOption(lqb, false);

    tbData.fillTbData("statistic/list", lqb, null);
    // 得到统计所以记录的关键指标
    LoanNumberBean loanNumber = loanAuditService.getNumberTotal();
    // 获得贷款产品List
    List<ProductName> productList = loanAuditService.selectProductList();
    mav.addObject("loanNumber", loanNumber);
    mav.addObject("tbData", tbData);
    mav.addObject("lqb", lqb);
    mav.addObject("productList", productList);
    mav.addObject("opt", opt);
    // 返回
    return mav;
  }

  /**
   * 导出Excel
   * 
   * @throws Exception
   */
  @RequestMapping(value = "exportExcel")
  public void exportExcel(HttpServletRequest request, HttpServletResponse response,
      LoanQueryBean lqb) throws Exception {
    // 日志
    SessionBean session = getSessionBean(request);
    log.info("{}导出提交人数的详细数据", session.getUserName());
    // 查询数据并返回数据表对象
    XSSFWorkbook workbook = loanAuditService.exportMessage(lqb, false);
    String codedFileName = new String("申请人数详细信息".getBytes("gb2312"), "ISO8859-1");
    // 导出
    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
    workbook.write(response.getOutputStream());
  }

  /**
   * 跳转到 商家平台用户反馈页面
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/mechantFeedbacks", method = RequestMethod.GET)
  @AvoidDuplicateSubmission()
  @UserMenu
  public ModelAndView mechantFeedbacks(HttpServletRequest request, HttpServletResponse response,
      Integer currentPage, UserFeedbackQuerybean ufqb) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到商家平台用户反馈界面", session.getUserName());

    ModelAndView mav = new ModelAndView("statistic/mechantFeedbacks");
    List<UserFeedback> ufbs = userFeedbackService.getUserFeedbackByCate(1);
    // 初始页面没选择时间为null ，选择时间搜索根据时间进行查询
    if (ufqb.getSubmitTimeFrom() == null) {
      Date now = new Date();
      if (ufbs.size() == 0) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        ufqb.setSubmitTimeFrom(calendar.getTime());
      } else {
        ufqb.setSubmitTimeFrom(ufbs.get(0).getCreatedAt());
      }
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      ufqb.setSubmitTimeEnd(sdf.parse(sdf.format(now)));
    }
    ufqb.setUserCate(1);

    TbData tbData = userFeedbackService.getUserFeedbackList(ufqb, currentPage);
    tbData.fillTbData("statistic/mechantFeedbacks", ufqb, null);

    mav.addObject("ufqb", ufqb);
    mav.addObject("tbData", tbData);
    // 返回
    return mav;
  }

  /**
   * 跳转到 融资平台用户反馈页面
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/orgFeedbacks", method = RequestMethod.GET)
  @AvoidDuplicateSubmission()
  @UserMenu
  public ModelAndView orgFeedbacks(HttpServletRequest request, HttpServletResponse response,
      Integer currentPage, UserFeedbackQuerybean ufqb) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}跳转到融资平台用户反馈界面", session.getUserName());

    ModelAndView mav = new ModelAndView("statistic/orgFeedbacks");
    List<UserFeedback> ufbs = userFeedbackService.getUserFeedbackByCate(2);
    // 初始没选择时间为null ，选择时间搜索根据时间进行查询
    if (ufqb.getSubmitTimeFrom() == null) {
      Date now = new Date();
      if (ufbs.size() == 0) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        ufqb.setSubmitTimeFrom(calendar.getTime());
      } else {
        ufqb.setSubmitTimeFrom(ufbs.get(0).getCreatedAt());
      }
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      ufqb.setSubmitTimeEnd(sdf.parse(sdf.format(now)));
    }
    ufqb.setUserCate(2);

    TbData tbData = userFeedbackService.getUserFeedbackList(ufqb, currentPage);
    tbData.fillTbData("statistic/orgFeedbacks", ufqb, null);

    mav.addObject("ufqb", ufqb);
    mav.addObject("tbData", tbData);
    // 返回
    return mav;
  }

  /**
   * 根据用户类型获得 用户反馈信息
   * 
   * @param request
   * @param response
   * @param cate 用户类型
   * @return
   * @throws Exception
   */
  public List<UserFeedback> getUserFeedbackByCate(HttpServletRequest request,
      HttpServletResponse response, int cate) throws Exception {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}根据类型获得用户反馈", session.getUserName());
    return userFeedbackService.getUserFeedbackByCate(cate);
  }
}

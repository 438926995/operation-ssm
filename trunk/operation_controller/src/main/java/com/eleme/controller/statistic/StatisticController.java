package com.eleme.controller.statistic;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.bean.JSONMessage;
import com.eleme.bean.SessionBean;
import com.eleme.bean.echart.OptionBean;
import com.eleme.bean.feedback.FeedbackQueryBean;
import com.eleme.bean.feedback.ReplyFeedback;
import com.eleme.bean.loan.LoanAudit;
import com.eleme.bean.loan.LoanQueryBean;
import com.eleme.controller.BaseController;
import com.eleme.service.product.IProductService;
import com.eleme.service.statistic.IStatisticService;
import com.eleme.util.DateUtil;
import com.eleme.util.pager.TbData;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by huwenwen on 16/5/14.
 */
@Controller
@RequestMapping(value = "/statistic")
public class StatisticController extends BaseController {

  @Inject
  private IStatisticService statisticService;

  @Inject
  private IProductService productService;

  /**
   * 贷款申请人数列表
   *
   * @param request
   * @param response
   * @param lqb
   * @return
   */
  @RequestMapping(value = "list", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
      LoanQueryBean lqb) throws Exception {
    ModelAndView mav = new ModelAndView();
    // 查一个月数据
    if (!lqb.isQueryAll() && lqb.getSubmitTimeEnd() == null) {
      // 获取现在的时间
      lqb.setSubmitTimeEnd(DateUtil.dateFormatDate(new Date()));
      // 获取上个月时间
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.MONTH, -1);
      lqb.setSubmitTimeFrom(DateUtil.dateFormatDate(calendar.getTime()));
    }
    Map<String, Object> map = statisticService.getLoanStatistic(lqb);
    TbData tbData = (TbData) map.get("tbData");
    tbData = tbData.fillTbData("statistic/list", lqb, null);
    OptionBean opt = (OptionBean) map.get("opt");
    LoanAudit la = (LoanAudit) map.get("loanTotal");
    mav.addObject("productList", productService.getAllProductList());
    mav.addObject("tbData", tbData);
    mav.addObject("opt", opt);
    mav.addObject("loanTotal", la);
    mav.addObject("lqb", lqb);
    return mav;
  }

  /**
   * 导出申请人数Excel
   *
   * @param request
   * @param response
   * @param lqb
   * @throws Exception
   */
  @RequestMapping(value = "/exportExcel")
  public void exportExcel(HttpServletRequest request, HttpServletResponse response,
      LoanQueryBean lqb) throws Exception {
    // 查询数据并返回数据表对象
    XSSFWorkbook workbook = statisticService.exportAppNumberInfo(lqb);
    String codedFileName = new String("申请人数详细信息".getBytes("gb2312"), "ISO8859-1");
    // 导出
    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
    workbook.write(response.getOutputStream());
  }

  /**
   * 用户反馈列表
   * @param request
   * @param fqb
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "getUserFeedbackData", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView getUserFeedbackData(HttpServletRequest request, FeedbackQueryBean fqb)
      throws Exception {
    ModelAndView mav = new ModelAndView("feedback");
    TbData tbData = statisticService.getFeedbackInfo(fqb);
    tbData = tbData.fillTbData("statistic/feedback", fqb, null);
    mav.addObject("tbData", tbData);
    mav.addObject("fqb", fqb);
    return mav;
  }

  /**
   * 回复用户反馈
   * @param request
   * @param rf
   * @return
   */
  @RequestMapping(value = "replyFeedback", method = RequestMethod.POST)
  @ResponseBody
  public JSONMessage replyFeedback(HttpServletRequest request, ReplyFeedback rf){
    SessionBean sessionBean = getSessionBean(request);
    rf.setReplyUserId(sessionBean.getUserId().intValue());
    int line = statisticService.replyFeedback(rf);
    if(line > 0){
      return new JSONMessage(true, "回复成功");
    }
    return new JSONMessage(false, "回复失败");
  }

}

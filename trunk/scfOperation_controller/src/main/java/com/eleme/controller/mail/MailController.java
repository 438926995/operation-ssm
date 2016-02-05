package com.eleme.controller.mail;

import com.eleme.annotation.controller.UserMenu;
import com.eleme.bean.JSONMessage;
import com.eleme.bean.SessionBean;
import com.eleme.constants.RelatedKeyContants;
import com.eleme.controller.BaseController;
import com.eleme.domain.mart.PageResData;
import com.eleme.domain.mart.finance.MFinanceOrg;
import com.eleme.domain.mart.mail.MailSellerInfoListQueryBean;
import com.eleme.domain.mart.mail.MailSellerInfoVO;
import com.eleme.domain.mart.mail.MailSendQueueEntity;
import com.eleme.service.finance.IFinanceService;
import com.eleme.service.mail.IMailSellerInfoService;
import com.eleme.service.mail.IMailSendQueueService;
import com.eleme.service.sys.IRelatedParamService;
import com.eleme.util.CommonUtil;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送邮件控制器
 *
 * @author sunwei
 * @since 2015年12月25日
 */
@Controller
@RequestMapping(value = "/mail")
public class MailController extends BaseController {

  /**
   * e log
   */
  private static final Log elog = LogFactory.getLog(MailController.class);

  @Inject
  private IMailSellerInfoService mailSellerInfoService;

  @Inject
  private IMailSendQueueService mailSendQueueService;

  @Inject
  private IFinanceService financeService;

  @Inject
  private IRelatedParamService relatedParamService;


  /**
   * 供MobileApi调用 请求发送申请邮件
   *
   * @param response 请求返回
   * @param mailId   邮件id
   * @author sunwei
   * @since 2015年12月25日
   */
  @RequestMapping(value = "/mobile_api/sendMailSellerInfo/{mailId:[\\d]+}")
  @ResponseBody
  public JSONMessage applySendMailSellerInfo(HttpServletResponse response,
                                             @PathVariable("mailId") Integer mailId) {
    boolean isSuccess = true;
    String message = "请求成功";
    // TODO 申请发送邮件的业务逻辑
    try {
      MailSellerInfoVO mailSellerInfo = mailSellerInfoService.getMailSellerInfoById(mailId);
      if (mailSellerInfo != null) {
        if (mailSellerInfo.getMailSendStatus() == null) { // 未添加到发送队列中
          String sendMode =
              relatedParamService.getValueByFlag(RelatedKeyContants.MAIL_SELLER_INFO_AUTO_SEND);
          elog.info("当前发送模式为{}", sendMode);
          if (sendMode.equals(RelatedKeyContants.MAIL_SELLER_INFO_AUTO_SEND)) {
            // 自动发送模式下添加到发送队列中去
            // 加到发送队列中
            List<MailSellerInfoVO> mailSellerInfos = new ArrayList<MailSellerInfoVO>();
            mailSellerInfos.add(mailSellerInfo);
            mailSellerInfoService.sendSelllerInfoMail(mailSellerInfos);
          } else {
            // do nothing
          }
        } else {
          // 已经添加到发送队列中去
          isSuccess = false;
          message = "重复提交";
        }
      } else {
        isSuccess = false;
        message = "未匹配到要发送的数据";
      }
    } catch (Exception ex) {
      elog.error(CommonUtil.getErrorMessage(ex));
      isSuccess = false;
      message = "服务器异常，请重试";
    }
    JSONMessage jsonMessage = new JSONMessage();
    jsonMessage.setMessage(message);
    jsonMessage.setIsSuccess(isSuccess);
    return jsonMessage;
  }

  /**
   * 供MobileApi调用 统一发送邮件接口
   *
   * @param mailId
   * @author sunwei
   * @since 2015年12月29日
   */
  @RequestMapping(value = "/mobile_api/sendMail/{mailId:[\\d]+}")
  @ResponseBody
  public JSONMessage applySendMail(HttpServletResponse response, @PathVariable("mailId") Integer mailId) {
    boolean isSuccess = true;
    String message = "请求成功";
    try {
      MailSendQueueEntity mailSendQueue = mailSendQueueService.getMailSendQueueById(mailId);
      if (mailSendQueue != null) {
        mailSendQueueService.sendMail(mailSendQueue);
      } else {
        isSuccess = false;
        message = "未查询到可以发送的邮件";
      }
    } catch (Exception ex) {
      elog.error(CommonUtil.getErrorMessage(ex));
      isSuccess = false;
      message = "服务器异常，请重试";
    }
    JSONMessage jsonMessage = new JSONMessage();
    jsonMessage.setMessage(message);
    jsonMessage.setIsSuccess(isSuccess);
    return jsonMessage;
  }


  /**
   * 获得页面
   *
   * @param request
   * @return
   * @author sunwei
   * @since 2016年1月4日
   */
  @RequestMapping(value = "/seller_info/list", method = RequestMethod.GET)
  @UserMenu
  public ModelAndView getMailSellerInfoListPage(HttpServletRequest request) {
    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}获取邮箱商户信息列表页面", session.getUserName());

    ModelAndView mav = new ModelAndView("mail/seller_info/list");
    try {

      List<MFinanceOrg> financeOrgs = financeService.queryAllFinanceOrgsForSelect();
      String sendMode =
          relatedParamService.getValueByFlag(RelatedKeyContants.MAIL_SELLER_INFO_AUTO_SEND);
      mav.addObject("receivers", financeOrgs);
      mav.addObject("sendMode", sendMode);
    } catch (Exception ex) {
      elog.error(CommonUtil.getErrorMessage(ex));
    }
    return mav;
  }


  /**
   * 获得页面数据
   *
   * @param request
   * @return
   * @author sunwei
   * @since 2016年1月4日
   */
  @RequestMapping(value = "/seller_info/list", method = RequestMethod.POST)
  @ResponseBody
  public JSONMessage getMailSellerInfoList(HttpServletRequest request,
                                           MailSellerInfoListQueryBean queryBean) {

    boolean isSuccess = true;
    String message = "";
    PageResData<MailSellerInfoVO> result = null;

    // 初始化请求参数
    if (queryBean == null) {
      queryBean = new MailSellerInfoListQueryBean();
    }

    queryBean.setStartItem(queryBean.getCurrentPage() * queryBean.getPageSize());

    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}查看商户列表", session.getUserName());
    // 查询数据并返回
    try {
      result = mailSellerInfoService.getMailSellerInfoPage(queryBean);
    } catch (Exception ex) {
      elog.error(CommonUtil.getErrorMessage(ex));
      isSuccess = false;
      message = "系统异常";
    }

    JSONMessage json = new JSONMessage();
    json.setIsSuccess(isSuccess);
    json.setDatas(result);
    json.setMessage(message);
    return json;
  }

  /**
   * 设置邮件发送模式
   *
   * @param request
   * @param sendMode（0：手动发送；1：自动发送）
   * @return
   * @author sunwei
   * @since 2015年12月28日
   */
  @RequestMapping(value = "/seller_info/setting", method = RequestMethod.POST)
  @ResponseBody
  public JSONMessage setMailSellerInfoSendMode(HttpServletRequest request, String sendMode) {

    boolean isSuccess = true;
    String message = "";

    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}修改商户邮件发送设置", session.getUserName());
    // 查询数据并返回
    try {
      if (StringUtils.isNotBlank(sendMode)) {
        relatedParamService.alertValueByFlag(RelatedKeyContants.MAIL_SELLER_INFO_SEND_MODE,
            sendMode);
      }

    } catch (Exception ex) {
      elog.error(CommonUtil.getErrorMessage(ex));
      isSuccess = false;
      message = "系统异常";
    }

    JSONMessage json = new JSONMessage();
    json.setIsSuccess(isSuccess);
    json.setMessage(message);
    return json;
  }

  /**
   * @param request
   * @param mailSellerInfoIds
   * @return
   * @author sunwei
   * @since 2015年12月28日
   */
  @RequestMapping(value = "/seller_info/sendEmail", method = RequestMethod.POST)
  @ResponseBody
  public JSONMessage sendMailSellerInfoList(HttpServletRequest request,
                                            @RequestParam("mailSellerInfoIds[]") String[] mailSellerInfoIds) {
    boolean isSuccess = true;
    String message = "操作成功，请等待片刻，再刷新页面查看发送状态";

    // 记录日志
    SessionBean session = getSessionBean(request);
    log.info("{}商户邮件发送", session.getUserName());

    // 查询数据并返回
    try {
      // TODO 批量发送邮件
      mailSellerInfoService.batchSendSelllerInfoMail(mailSellerInfoIds);
    } catch (Exception ex) {
      ex.printStackTrace();
      elog.error(CommonUtil.getErrorMessage(ex));
      isSuccess = false;
      message = "系统异常";
    }

    JSONMessage json = new JSONMessage();
    json.setIsSuccess(isSuccess);
    json.setMessage(message);
    return json;

  }

}

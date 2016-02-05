package com.eleme.service.mail.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.eleme.constants.MailSendStatus;
import com.eleme.constants.MailType;
import com.eleme.domain.mart.PageResData;
import com.eleme.domain.mart.loan.TAppSellerLoanVo;
import com.eleme.domain.mart.mail.MailSellerInfoEntity;
import com.eleme.domain.mart.mail.MailSellerInfoListQueryBean;
import com.eleme.domain.mart.mail.MailSellerInfoVO;
import com.eleme.domain.mart.mail.MailSendQueueEntity;
import com.eleme.domain.mart.seller.TSdcTradeFlow;
import com.eleme.mapper.mart.mail.MailSellerInfoMapper;
import com.eleme.service.mail.IMailSellerInfoService;
import com.eleme.service.mail.IMailSendQueueService;
import com.eleme.util.CommonUtil;
import com.eleme.util.FileUtil;
import com.eleme.util.ftl.FreemarkerUtils;
import com.eleme.util.mail.GlobalConstants;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/**
 * 商户邮件Mail service 实现类
 * 
 * @author sunwei
 * @since 2015年12月25日
 */
@Service
public class MailSellerInfoServiceImpl implements IMailSellerInfoService {

  /**
   * elog
   */
  private static final Log elog = LogFactory.getLog(MailSellerInfoServiceImpl.class);

  @Inject
  private MailSellerInfoMapper mailSellerInfoMapper;

  @Inject
  private IMailSendQueueService mailSendQueueService;

  @Override
  public PageResData<MailSellerInfoVO> getMailSellerInfoPage(
      MailSellerInfoListQueryBean queryBean) {
    PageResData<MailSellerInfoVO> mailSellerInfoPageData = new PageResData<MailSellerInfoVO>();
    // 获得数据总量
    mailSellerInfoPageData.setTotal(mailSellerInfoMapper.selectMailSellerInfosCount(queryBean));
    // 获得分页数据
    mailSellerInfoPageData.setRows(mailSellerInfoMapper.selectMailSellerInfos(queryBean));
    return mailSellerInfoPageData;
  }

  @Override
  public void batchSendSelllerInfoMail(String[] mailSellerInfoIds) {

    if (mailSellerInfoIds.length > 0) {
      // 定义商户邮件信息列表
      List<MailSellerInfoVO> mailSellerInfos =
          mailSellerInfoMapper.selectMailSellerInfosByIds(mailSellerInfoIds);
      if (mailSellerInfos != null && mailSellerInfos.size() > 0) {
        elog.info("查询到得请求批量发送商户信息邮件数量为{0}", mailSellerInfos.size());
        System.out.println("查询到得请求批量发送商户信息邮件数量为：" + mailSellerInfos.size());
        sendSelllerInfoMail(mailSellerInfos);
      }
    }
  }

  @Override
  public MailSellerInfoVO getMailSellerInfoById(Integer mailId) {
    return mailSellerInfoMapper.selectMailSellerInfoVOById(mailId);
  }

  @Override
  @Async
  public void sendSelllerInfoMail(List<MailSellerInfoVO> mailSellerInfos) {
    for (MailSellerInfoVO mailSellerInfo : mailSellerInfos) {
      if (StringUtils.isBlank(mailSellerInfo.getMailSellerInfoExcelPath())) {
        // 生成Excel 并返回文件路径
        String filePath = createSellerLoanInfoExcel(mailSellerInfo);

        // 更新MailSellerInfo path
        MailSellerInfoEntity mailSellerInfoEntity = new MailSellerInfoEntity();
        mailSellerInfoEntity.setSimId(mailSellerInfo.getSimId());
        mailSellerInfoEntity.setMailSellerInfoExcelPath(filePath);
        mailSellerInfoMapper.updateById(mailSellerInfoEntity);

        // 赋值
        mailSellerInfo.setMailSellerInfoExcelPath(filePath);
      }

      MailSendQueueEntity mailSendQueue = null;

      if (mailSellerInfo.getMailId() == null) {
        mailSendQueue = new MailSendQueueEntity();
        // 设置主题
        String mailTopic = "【e金融提供】饿了么" + mailSellerInfo.getSellerName() + "餐厅数据";
        mailSendQueue.setMailTopic(mailTopic);
        // 设置内容
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String mailContent =
            "<p>您好，" + mailSellerInfo.getUserName() + ":</p><p style=\"text-indent:2em\">这是"
                + mailSellerInfo.getSellerName() + "餐厅的基本信息和近180天订单流水，请下载附件查看详细内容。</p>"
                + "<p style=\"text-indent:2em\">如有疑问，请回复邮件，e金融工作人员会在三个工作日之内答复你的疑问。</p>"
                + "<p style=\"text-indent:4em\">谢谢！</p>" + "<p style=\"text-align:right\">e金融</p>"
                + "<p style=\"text-align:right\">" + dateFormat.format(new Date()) + "</p>";
        try {
          Map<String, Object> mapParam = new HashMap<String, Object>();
          mapParam.put("userName", mailSellerInfo.getUserName());
          mapParam.put("sellerName", mailSellerInfo.getSellerName());
          mapParam.put("sendDate", dateFormat.format(new Date()));
          // 填充数据模板
          mailContent = FreemarkerUtils.getEmailTPL("SellerInfoMailContent.tpl", mapParam);
        } catch (Exception e) {
          e.printStackTrace();
          elog.error(CommonUtil.getErrorMessage(e));
        }
        mailSendQueue.setMailContent(mailContent);
        // 设置发送时间
        mailSendQueue.setMailSendedAt(new Date());
        // 设置收件人
        mailSendQueue.setMailReceiver(mailSellerInfo.getUserEmail());
        // 设置发送状态
        mailSendQueue.setMailSendStatus(MailSendStatus.UNSEND.getCode());
        // 设置邮件类型 商户信息邮件
        mailSendQueue.setMailContainType(MailType.SELLER_INFO.getId());
        // 设置管理id
        mailSendQueue.setMailContainId(mailSellerInfo.getSimId());
        // 设置附件路径
        mailSendQueue.setMailAttachments(mailSellerInfo.getMailSellerInfoExcelPath());
        // 插入数据库 并且返回 主键id
        mailSendQueueService.addOneMailSendQueue(mailSendQueue);
      } else {
        mailSendQueue = mailSendQueueService.getMailSendQueueById(mailSellerInfo.getMailId());
        mailSendQueue.setMailReceiver(mailSellerInfo.getUserEmail());
      }

      // 发送邮件
      mailSendQueueService.sendMail(mailSendQueue);
    }
  }

  /**
   * 创建商户贷款 申请邮件 附件中商户信息Excel 当前方法假实现，并不产生excel，在正真发送邮件处实现
   * 
   * TODO 以后将正真发送邮件处的execl生成取出，将生成excel放开
   * 
   * @author sunwei
   * @since 2016年1月3日
   * @param mailSellerInfo
   * @return
   */
  protected String createSellerLoanInfoExcel(MailSellerInfoVO mailSellerInfo) {
    try {
      // 获取导出路径文件夹配置信息
      String exportPath = GlobalConstants.mailSellerLoanExcelExportPath;
      // 创建文件夹
      FileUtil.createDir(exportPath);

      // 生成导出文件名字
      Date now = new Date();
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String fileName = dateFormat.format(now) + mailSellerInfo.getSellerName()
          + mailSellerInfo.getSellerId() + ".xls";
      String uuid = UUID.randomUUID().toString().replaceAll("-", "");
      String filePath = exportPath + File.separator + uuid + ".xls";

      // // 获取贷款信息
      // TAppSellerLoanVo appSellerLoan =
      // sellerLoanService.selectSellerLoanById(mailSellerInfo.getSlId(), null);
      // // 获取流水信息
      // List<TSdcTradeFlow> tradeFlows =
      // tradeFlowMapper.selectBySellerId(mailSellerInfo.getSellerId());
      //
      // // 将贷款信息放入list中
      // List<TAppSellerLoanVo> sellerLoans = new ArrayList<TAppSellerLoanVo>();
      // sellerLoans.add(appSellerLoan);
      // // 读取导出Excel模板
      // InputStream is = getClass().getResourceAsStream("/tpl/excel/mail_seller_info.xls");
      // // 设置导出Excel路径
      // OutputStream os = new FileOutputStream(filePath);
      //
      // // 创建导出时Jxls的上下文
      // Context context = new Context();
      // // 设置 sellerLoans 商户贷款变量
      // context.putVar("sellerLoans", sellerLoans);
      // // 设置 tradeFlows 商户流水变量
      // context.putVar("tradeFlows", tradeFlows);
      // context.putVar("naposResOid", appSellerLoan.getNaposResOid());
      //
      // // 开始导出
      // JxlsHelper.getInstance().processTemplate(is, os, context);

      // 完成导出之后返回生成文件路径
      return filePath + "|" + fileName;
    } catch (Exception ex) {
      ex.printStackTrace();
      return "";
    }
  }

}

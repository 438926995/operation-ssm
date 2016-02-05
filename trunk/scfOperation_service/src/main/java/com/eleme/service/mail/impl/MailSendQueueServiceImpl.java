package com.eleme.service.mail.impl;

import com.eleme.constants.MailSendStatus;
import com.eleme.constants.MailType;
import com.eleme.domain.mart.loan.TAppSellerLoanVo;
import com.eleme.domain.mart.mail.MailSellerInfoVO;
import com.eleme.domain.mart.mail.MailSendQueueEntity;
import com.eleme.domain.mart.seller.TSdcTradeFlow;
import com.eleme.mapper.mart.mail.MailSellerInfoMapper;
import com.eleme.mapper.mart.mail.MailSendQueueMapper;
import com.eleme.mapper.mart.seller.TSdcTradeFlowMapper;
import com.eleme.service.loan.ISellerLoanService;
import com.eleme.service.mail.IMailSendQueueService;
import com.eleme.util.CommonUtil;
import com.eleme.util.FileUtil;
import com.eleme.util.ftl.FreemarkerUtils;
import com.eleme.util.mail.CommonMailSender;
import com.eleme.util.mail.CustomMailSender;
import com.eleme.util.mail.CustomMailSenderListener;
import com.eleme.util.mail.GlobalConstants;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import org.apache.commons.lang3.StringUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 邮件发送队列业务实现类
 *
 * @author sunwei
 * @since 2015年12月29日
 */
@Service
public class MailSendQueueServiceImpl implements IMailSendQueueService, CustomMailSenderListener {

    /**
     * elog
     */
    private Log elog = LogFactory.getLog(MailSendQueueServiceImpl.class);

    @Inject
    private CustomMailSender customMailSender;

    @Inject
    private CommonMailSender commonMailSender;

    @Inject
    private MailSendQueueMapper mailSendQueueMapper;

    @Inject
    private MailSellerInfoMapper mailSellerInfoMapper;

    @Inject
    private ISellerLoanService sellerLoanService;

    @Inject
    private TSdcTradeFlowMapper tradeFlowMapper;

    @Override
    public Integer addOneMailSendQueue(MailSendQueueEntity mailInfo) {
        return mailSendQueueMapper.insertMailSendQueue(mailInfo);
    }

    @Override
    public MailSendQueueEntity getMailSendQueueById(Integer mailId) {
        MailSendQueueEntity mailSendQueue = mailSendQueueMapper.selectMailSendQueueById(mailId);
        return mailSendQueue;
    }


    @Override
    @Async
    public void sendMail(final MailSendQueueEntity mailSendQueue) {
        elog.info("发送邮件id:{}", mailSendQueue.getMailId());
        // 特殊情况判断 当为商户信息邮件时
        if (mailSendQueue.getMailContainType().equals(MailType.SELLER_INFO.getId())) {
            MailSellerInfoVO mailSellerInfo =
                    mailSellerInfoMapper.selectMailSellerInfoVOById(mailSendQueue.getMailContainId());
            if (mailSellerInfo != null) {
                // 重新创建文件 如果有统一的文件服务器之后，将不必在这里创建
                mailSendQueue.setMailAttachments(createSellerLoanInfoExcel(mailSellerInfo));
            } else {
                // 异常情况
                elog.error("sendMail时无法获取到对应的mailSellerInfo数据");
                return;
            }
        }

        // 套上公共模板，写入签名
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("mail", mailSendQueue);
        String text = mailSendQueue.getMailContent();
        try {
            // 填充数据模板
            text = FreemarkerUtils.getEmailTPL("BaseMail.tpl", mapParam);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            elog.error(CommonUtil.getErrorMessage(e));
        }
        // 发送 html 邮件
//        customMailSender.sendAttachmentHtmlMail(mailSendQueue.getMailReceiver(),
//                mailSendQueue.getMailTopic(), text, mailSendQueue.getMailAttachments(), this,
//                mailSendQueue.getMailId());
        commonMailSender.sendHtmlAttachmentMail(mailSendQueue.getMailReceiver(),
                mailSendQueue.getMailTopic(), text, mailSendQueue.getMailAttachments(), this,
                mailSendQueue.getMailId());
    }

    /**
     * 创建商户贷款 申请邮件 附件中商户信息Excel
     *
     * @param mailSellerInfo
     * @return
     * @author sunwei
     * @since 2016年1月3日
     */
    protected String createSellerLoanInfoExcel(MailSellerInfoVO mailSellerInfo) {
        try {
            // 获取导出路径文件夹配置信息
            String exportPath = GlobalConstants.mailSellerLoanExcelExportPath;
            // 创建文件夹
            FileUtil.createDir(exportPath);

            // 生成导出文件名字
            Date now = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String fileName = mailSellerInfo.getSellerName() + dateFormat.format(now) + ".xls";
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String filePath = exportPath + File.separator + uuid + ".xls";

            // 获取贷款信息
            TAppSellerLoanVo appSellerLoan =
                    sellerLoanService.selectSellerLoanById(mailSellerInfo.getSlId(), null);
            // 获取流水信息
            List<TSdcTradeFlow> tradeFlows =
                    tradeFlowMapper.selectBySellerId(mailSellerInfo.getSellerId());

            // 将贷款信息放入list中
            List<TAppSellerLoanVo> sellerLoans = new ArrayList<TAppSellerLoanVo>();
            sellerLoans.add(appSellerLoan);
            // 读取导出Excel模板
            InputStream is = getClass().getResourceAsStream("/tpl/excel/mail_seller_info.xls");
            // 设置导出Excel路径
            OutputStream os = new FileOutputStream(filePath);

            // 创建导出时Jxls的上下文
            Context context = new Context();
            // 设置 sellerLoans 商户贷款变量
            context.putVar("sellerLoans", sellerLoans);
            // 设置 tradeFlows 商户流水变量
            context.putVar("tradeFlows", tradeFlows);
            context.putVar("naposResOid", appSellerLoan.getNaposResOid());

            long startTime = new Date().getTime();
            // 开始导出
            JxlsHelper.getInstance().processTemplate(is, os, context);

            elog.info("导出流水{}条，所需时间：{}ms", tradeFlows.size(), new Date().getTime() - startTime);

            // 完成导出之后返回生成文件路径
            return filePath + "|" + fileName;
        } catch (Exception ex) {
            elog.error(CommonUtil.getErrorMessage(ex));
            return "";
        }
    }

    @Override
    public void success(Integer mailId, String sendTo) {
        this.success(mailId, sendTo, null);
    }

    @Override
    public void success(Integer mailId, String sendTo, String attachments) {
        try {
            // 成功回调
            MailSendQueueEntity mailSendQueue = new MailSendQueueEntity();
            // 设置id
            mailSendQueue.setMailId(mailId);
            // 收件人
            mailSendQueue.setMailReceiver(sendTo);
            // 设置发送时间
            mailSendQueue.setMailSendedAt(new Date());
            // 设置发送状态
            mailSendQueue.setMailSendStatus(MailSendStatus.SEND_SUCCESS.getCode());
            // 更新
            mailSendQueueMapper.updateMailSendQueue(mailSendQueue);
            // 删除附件
            if (StringUtils.isNotBlank(attachments)) {
                String[] attachmentArr = attachments.split(",");
                if (attachmentArr != null && attachmentArr.length > 0) {
                    for (String attachment : attachmentArr) {
                        String[] attachInfoArr = attachment.split("\\|");
                        if (attachInfoArr.length != 2) {
                            continue;
                        }
                        elog.info("发送完毕,删除附件:{}",attachInfoArr[0]);
                        FileUtil.deleteFile(attachInfoArr[0]);
                    }
                }
            }
        } catch (Exception e) {
            elog.error(CommonUtil.getErrorMessage(e));
        }
    }

    @Override
    public void fail(Integer mailId, String sendTo, String attachments, Exception ex) {
        try {
            // 失败回调
            MailSendQueueEntity mailSendQueue = new MailSendQueueEntity();
            // 设置id
            mailSendQueue.setMailId(mailId);
            mailSendQueue.setMailReceiver(sendTo);
            // 设置发送时间
            mailSendQueue.setMailSendedAt(new Date());
            // 设置发送状态
            mailSendQueue.setMailSendStatus(MailSendStatus.SEND_FAILD.getCode());
            String exmsg = ex.getMessage();
            if (exmsg.length() > 200) {
                exmsg.subSequence(0, 199);
            }
            mailSendQueue.setMailSendDesc(exmsg);
            // 更新
            mailSendQueueMapper.updateMailSendQueue(mailSendQueue);

            // 删除附件
            if (StringUtils.isNotBlank(attachments)) {
                String[] attachmentArr = attachments.split(",");
                if (attachmentArr != null && attachmentArr.length > 0) {
                    for (String attachment : attachmentArr) {
                        String[] attachInfoArr = attachment.split("\\|");
                        if (attachInfoArr.length != 2) {
                            continue;
                        }
                        elog.info("发送失败,附件:{}",attachInfoArr[0]);
                        FileUtil.deleteFile(attachInfoArr[0]);
                    }
                }
            }
        } catch (Exception e) {
            elog.error(CommonUtil.getErrorMessage(e));
        }
    }

    @Override
    public void fail(Integer mailId, String sendTo, Exception ex) {
        this.fail(mailId, sendTo, null, ex);
    }


}

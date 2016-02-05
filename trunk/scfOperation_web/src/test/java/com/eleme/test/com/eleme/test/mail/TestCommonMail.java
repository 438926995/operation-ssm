package com.eleme.test.com.eleme.test.mail;

import com.eleme.domain.mart.mail.MailSendQueueEntity;
import com.eleme.mapper.mart.mail.MailSendQueueMapper;
import com.eleme.test.BaseTest;
import com.eleme.util.CommonUtil;
import com.eleme.util.ftl.FreemarkerUtils;
import com.eleme.util.mail.CommonMailSender;
import com.google.inject.Inject;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunwei on 16/1/15.
 */
public class TestCommonMail extends BaseTest {

    @Inject
    CommonMailSender commonMailSender;

    @Inject
    MailSendQueueMapper mailSendQueueMapper;

    private Log elog = LogFactory.getLog(TestCommonMail.class);

//    @Test
//    public void testSendAttachmentMail() {
//        try {
//            HtmlEmail email = new HtmlEmail();
//            email.setSmtpPort(25);
//            email.addTo("xiaochun.hu@ele.me");
//            email.setAuthentication("e-financetest@ele.me", "73zkhU-8632nN");
//            email.setFrom("e-financetest@ele.me");
//            email.setHostName("email.ele.me");
//            email.setSubject("Test email with inline image");
//            email.setMsg("<!doctype html><html lang='en'><head><meta charset='UTF-8'><title>Document</title></head><body>你好我爱你</body></html>");
//
//            EmailAttachment attachment = new EmailAttachment();
//            attachment.setPath("/Users/sunwei/temp/trade_flow_detail.xls");
//            attachment.setDisposition(EmailAttachment.ATTACHMENT);
//            attachment.setDescription("商户流水信息");
//            attachment.setName("商户流水123.xls");
//            email.attach(attachment);
//            email.send();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//
//    }

    @Test
    public void testCommonMailSender() {
        if(mailSendQueueMapper ==null){
            elog.info("111111111111");
        }
        MailSendQueueEntity mailSendQueue = mailSendQueueMapper.selectMailSendQueueById(124);

        // 套上公共模板，写入签名
        Map<String, Object> mapParam = new HashMap<>();
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
        commonMailSender.sendHtmlAttachmentMail(mailSendQueue.getMailReceiver(),
                mailSendQueue.getMailTopic(), text, mailSendQueue.getMailAttachments(), null,
                mailSendQueue.getMailId());

    }
}

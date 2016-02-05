package com.eleme.util.mail;

import com.eleme.util.CommonUtil;
import com.eleme.util.DateUtil;
import com.eleme.util.xml.TagSoupUtils;
import com.eleme.util.xml.XmlGen;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 基于CommonMail发送邮件工具组件
 * <p>
 * Created by sunwei on 16/1/15.
 */
public class CommonMailSender {

    private static final Log logger = LogFactory.getLog(CustomMailSender.class);

    public void sendHtmlAttachmentMail(String to, String subject, String content, String attachments,
                                       CustomMailSenderListener listener, Integer mailId) {
        // 设置默认附件路径，resource目录下
        String attachmentBasePath = this.getClass().getResource("/").getPath();

        if (StringUtils.isNotBlank(GlobalConstants.ATTACHMENT_PATH)) {
            attachmentBasePath = GlobalConstants.ATTACHMENT_PATH; // 设置附件基本路径
        }

        // 不是正式环境，全部发送给测试人员
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_PRODUCTION)) {
            to = GlobalConstants.TEST_SENDTO;
        }

        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_DEVELOP)) {
            try {
                // 初始化html邮件对象
                HtmlEmail email = new HtmlEmail();
                // 设置邮件服务器
                email.setHostName(GlobalConstants.emailHost);
                // 设置邮件服务器端口
                email.setSmtpPort(Integer.valueOf(GlobalConstants.emailSmtpPort));
                // 设置邮件标题
                email.setSubject(subject);
                // 设置邮件收件人
                email.addTo(to);
                logger.info("发送邮件账号:{},密码:{}",GlobalConstants.emailUserName,GlobalConstants.emailPassword);
                // 设置邮件发送验证账号密码
                email.setAuthentication(GlobalConstants.emailUserName, GlobalConstants.emailPassword);
                // 设置邮件发件人
                email.setFrom(GlobalConstants.emailFrom);


                // 设置邮件发送时间
                email.setSentDate(new Date());

                // 以XML格式读取HTML格式邮件内容
                XmlGen x = new XmlGen(TagSoupUtils.parse(content, "UTF8").getBytes("UTF-8"));

                // 使用xpath得到邮件内容中所有img节点集合
                List<XmlGen> sites = x.selectAllXml("//img");
                Integer count = 1;
                // 遍历img节点集合
                for (XmlGen xg : sites) {
                    // 得到img节点src属性的值
                    String srcValue = xg.selectFirst("@src");
                    // 截取src属性值的图片名称
                    String fileName = srcValue.substring(srcValue.lastIndexOf("/") + 1);
                    // 得到文件后缀名
                    String postfix = fileName.substring(fileName.indexOf("."));
                    // 创建新的文件名称，在附件中显示
                    String newFileName = count.toString() + postfix;
                    // 创建修改html格式邮件内容img节点下src的值，改为读取附件地址为新文件名的图片
                    content = content.replace(srcValue, "cid:" + newFileName);
                    count++;
                }

                email.setHtmlMsg(content);
                email.setCharset("UTF-8");


                count = 1;
                // 再次遍历img节点集合
                for (XmlGen xg : sites) {
                    // 得到img节点src属性的值
                    String srcValue = xg.selectFirst("@src");
                    // 截取src属性值的图片名称
                    String fileName = srcValue.substring(srcValue.lastIndexOf("/") + 1);

                    // 设置目标路径
                    String destPath = attachmentBasePath + File.separator + srcValue;

                    // 添加邮件图片附件
                    EmailAttachment attachment = new EmailAttachment();
                    attachment.setPath(destPath);
                    attachment.setDisposition(EmailAttachment.ATTACHMENT);
                    // 加上这句将作为附件发送,否则将作为信件的文本内容
                    String postfix = fileName.substring(fileName.indexOf("."));
                    String newFileName = count.toString() + postfix;

                    attachment.setName(newFileName);
                    email.attach(attachment);

                    count++;
                }

                logger.info("附件内容:{}", attachments);
                // 遍历attachments 添加附件
                if (StringUtils.isNotBlank(attachments)) {
                    String[] attachmentArr = attachments.split(",");
                    for (String attachmentStr : attachmentArr) {
                        String[] attachmentInfoArr = attachmentStr.split("\\|");
                        if (attachmentInfoArr.length != 2) {
                            continue;
                        }
                        String destPath = attachmentInfoArr[0]; // 目标位置
                        String fileName = attachmentInfoArr[1]; // 文件名称

                        // 添加附件
                        EmailAttachment emailAttachment = new EmailAttachment();
                        // 设置附件位置
                        emailAttachment.setPath(destPath);
                        // 设置显示位置
                        emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);

                        // 设置文件名称
                        emailAttachment.setName(fileName);

                        // 添加附件
                        email.attach(emailAttachment);
                    }
                }
                email.send();
                if (listener != null) {
                    listener.success(mailId, to, attachments);
                }
                logger.info(new SimpleDateFormat(DateUtil.ISO_DATE_FORMAT).format(new Date())
                        + "==>发带附件和图片的邮件给" + to + "，主题：" + subject);
            } catch (Exception ex) {
                logger.error(CommonUtil.getErrorMessage(ex));
                if (listener != null) {
                    listener.fail(mailId, to, attachments, ex);
                }
            }
        }
    }
}

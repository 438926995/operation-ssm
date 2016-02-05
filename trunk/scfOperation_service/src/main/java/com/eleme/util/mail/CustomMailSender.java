package com.eleme.util.mail;

import com.eleme.util.CommonUtil;
import com.eleme.util.DateUtil;
import com.eleme.util.xml.TagSoupUtils;
import com.eleme.util.xml.XmlGen;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 邮件发送方法
 *
 * @author penglau
 */
public class CustomMailSender {

    private static final Log logger = LogFactory.getLog(CustomMailSender.class);

    /**
     * 发送普通文本邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @Async
    public void sendTextMail(String to, String subject, String content) {
        // 不是正式环境，全部发送给测试人员
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_PRODUCTION)
                && !GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_BETA)) {
            to = GlobalConstants.TEST_SENDTO;
        }
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_DEVELOP)) {
            Properties props = new Properties();
            props.put("mail.smtp.host", GlobalConstants.emailHost); //
            props.put("mail.from", GlobalConstants.emailFrom);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", GlobalConstants.emailSmtpPort);
            Session session = Session.getInstance(props, new Authenticator() { // 验账账户
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(GlobalConstants.emailUserName,
                            GlobalConstants.emailPassword);
                }
            });

            try {
                MimeMessage msg = new MimeMessage(session);
                msg.setFrom();
                msg.setRecipients(Message.RecipientType.TO, to);
                msg.setSubject(subject);
                msg.setSentDate(new Date());
                msg.setText(content);
                Transport.send(msg);
            } catch (MessagingException mex) {
                logger.error("send failed, exception: " + mex);
            }
        }
    }

    /**
     * 发送HTML格式邮件
     *
     * @param to      收件人
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    @Async
    public void sendHtmlMail(String to, String subject, String content) {
        // 不是正式环境，全部发送给测试人员
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_PRODUCTION)
                && !GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_BETA)) {
            subject = subject + " 发给：" + to;
            to = GlobalConstants.TEST_SENDTO;
        }
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_DEVELOP)) {
            Properties props = new Properties();
            BodyPart messageBodyPart = new MimeBodyPart();
            Session session = Session.getDefaultInstance(props, new Authenticator() { // 验账账户
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(GlobalConstants.emailUserName,
                            GlobalConstants.emailPassword);
                }
            });
            MimeMessage message = new MimeMessage(session);
            Multipart multipart = new MimeMultipart();
            try {
                message.setContent("Email Content. ", "text/html");
                message.setSubject(subject);
                props.put("mail.smtp.host", GlobalConstants.emailHost);
                props.put("mail.from", GlobalConstants.emailFrom);
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", GlobalConstants.emailSmtpPort);
                message.setFrom();
                message.setRecipients(Message.RecipientType.TO, to);
                message.setSentDate(new Date());
                messageBodyPart.setContent(content, "text/html;charset=utf-8");
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
                Transport.send(message);
                logger.info(new SimpleDateFormat(DateUtil.ISO_DATE_FORMAT).format(new Date()) + "==>发邮件给"
                        + to + "，主题：" + subject);
            } catch (MessagingException mex) {
                logger.error(CommonUtil.getErrorMessage(mex));
            } catch (Exception ex) {
                logger.error(CommonUtil.getErrorMessage(ex));
            }
        }
    }

    /**
     * 发送HTML格式邮件
     *
     * @param toAddressArr 收件人（多）
     * @param subject      标题
     * @param content      邮件正文
     */
    @Async
    public void sendHtmlMailMultiTo(InternetAddress[] toAddressArr, String subject, String content) {
        // 不是正式环境，全部发送给测试人员
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_PRODUCTION)) {
            for (int i = 0; i < toAddressArr.length; i++) {
                try {
                    toAddressArr[i] = new InternetAddress(GlobalConstants.TEST_SENDTO);
                } catch (AddressException e) {
                    logger.error(CommonUtil.getErrorMessage(e));
                }
            }
        }
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_DEVELOP)) {
            Properties props = new Properties();
            BodyPart messageBodyPart = new MimeBodyPart();
            Session session = Session.getDefaultInstance(props, new Authenticator() { // 验账账户
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(GlobalConstants.emailUserName,
                            GlobalConstants.emailPassword);
                }
            });
            MimeMessage message = new MimeMessage(session);
            Multipart multipart = new MimeMultipart();
            try {
                message.setContent("Email Content. ", "text/html");
                message.setSubject(subject);
                props.put("mail.smtp.host", GlobalConstants.emailHost);
                props.put("mail.from", GlobalConstants.emailFrom);
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", GlobalConstants.emailSmtpPort);
                message.setFrom();
                message.setRecipients(Message.RecipientType.TO, toAddressArr);
                message.setSentDate(new Date());
                messageBodyPart.setContent(content, "text/html;charset=utf-8");
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
                Transport.send(message);
                logger.info(new SimpleDateFormat(DateUtil.ISO_DATE_FORMAT).format(new Date()) + "==>成功发送邮件给"
                        + ArrayUtils.toString(toAddressArr) + "，主题：" + subject);
            } catch (MessagingException mex) {
                logger.error(CommonUtil.getErrorMessage(mex));
            } catch (Exception ex) {
                logger.error(CommonUtil.getErrorMessage(ex));
            }
        }
    }

    /**
     * 发送HTML格式邮件,带抄送的
     *
     * @param to      收件地址
     * @param cc      单个抄送地址
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    @Async
    public void sendHtmlMailCC(String to, String cc, String subject, String content) {
        // 不是正式环境，全部发送给测试人员
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_PRODUCTION)) {
            to = GlobalConstants.TEST_SENDTO;
            cc = GlobalConstants.TEST_SENDTO;
        }
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_DEVELOP)) {
            Properties props = new Properties();
            BodyPart messageBodyPart = new MimeBodyPart();
            Session session = Session.getDefaultInstance(props, new Authenticator() { // 验账账户
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(GlobalConstants.emailUserName,
                            GlobalConstants.emailPassword);
                }
            });
            MimeMessage message = new MimeMessage(session);
            Multipart multipart = new MimeMultipart();
            try {
                message.setContent("Email Content. ", "text/html");
                message.setSubject(subject);
                props.put("mail.smtp.host", GlobalConstants.emailHost);
                props.put("mail.from", GlobalConstants.emailFrom);
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", GlobalConstants.emailSmtpPort);
                message.setFrom();
                message.setRecipients(Message.RecipientType.TO, to);
                message.setRecipients(Message.RecipientType.CC, cc);
                message.setSentDate(new Date());
                messageBodyPart.setContent(content, "text/html;charset=utf-8");
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
                Transport.send(message);
                logger.info(new SimpleDateFormat(DateUtil.ISO_DATE_FORMAT).format(new Date()) + "==>发邮件给"
                        + to + "===>抄送" + cc + "，主题：" + subject);
            } catch (MessagingException mex) {
                logger.error(CommonUtil.getErrorMessage(mex));
            } catch (Exception ex) {
                logger.error(CommonUtil.getErrorMessage(ex));
            }
        }
    }

    /**
     * 发送HTML格式邮件,带抄送的
     *
     * @param to             收件地址
     * @param ccAddressArray 多个抄送地址
     * @param subject        邮件标题
     * @param content        邮件内容
     */
    @Async
    public void sendHtmlMailMultiCC(String to, InternetAddress[] ccAddressArray, String subject,
                                    String content) {
        // 不是正式环境，全部发送给测试人员
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_PRODUCTION)) {
            to = GlobalConstants.TEST_SENDTO;
            for (int i = 0; i < ccAddressArray.length; i++) {
                try {
                    ccAddressArray[i] = new InternetAddress(GlobalConstants.TEST_SENDTO);
                } catch (AddressException e) {
                    logger.error(CommonUtil.getErrorMessage(e));
                }
            }
        }
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_DEVELOP)) {
            Properties props = new Properties();
            BodyPart messageBodyPart = new MimeBodyPart();
            Session session = Session.getDefaultInstance(props, new Authenticator() { // 验账账户
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(GlobalConstants.emailUserName,
                            GlobalConstants.emailPassword);
                }
            });
            MimeMessage message = new MimeMessage(session);
            Multipart multipart = new MimeMultipart();
            try {
                logger.info(new SimpleDateFormat(DateUtil.ISO_DATE_FORMAT).format(new Date()) + "==>准备发邮件给"
                        + to + "===>抄送" + ArrayUtils.toString(ccAddressArray) + "，主题：" + subject);
                message.setContent("Email Content. ", "text/html");
                message.setSubject(subject);
                props.put("mail.smtp.host", GlobalConstants.emailHost);
                props.put("mail.from", GlobalConstants.emailFrom);
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", GlobalConstants.emailSmtpPort);
                message.setFrom();
                message.setRecipients(Message.RecipientType.TO, to);
                message.setRecipients(Message.RecipientType.CC, ccAddressArray);
                message.setSentDate(new Date());
                messageBodyPart.setContent(content, "text/html;charset=utf-8");
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
                Transport.send(message);
                logger.info(new SimpleDateFormat(DateUtil.ISO_DATE_FORMAT).format(new Date()) + "==>成功发邮件给"
                        + to + "===>抄送" + ArrayUtils.toString(ccAddressArray) + "，主题：" + subject);
            } catch (MessagingException mex) {
                logger.error(CommonUtil.getErrorMessage(mex));
            } catch (Exception ex) {
                logger.error(CommonUtil.getErrorMessage(ex));
            }
        }
    }

    /**
     * 发送带有附件的HTML格式邮件，HTML内容中的图片等，从附件中读取
     *
     * @param to
     * @param subject
     * @param content
     * @param attachments 附件表的主键字符串，逗号分隔 | 附件路径
     */
    @Async
    public void sendAttachmentHtmlMail(String to, String subject, String content, String attachments,
                                       Map<String, Object> fileInfo) {

        boolean isSuccess = true;
        String sendDesc = "";
        // 不是正式环境，全部发送给测试人员
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_PRODUCTION)) {
            to = GlobalConstants.TEST_SENDTO;
        }

        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_DEVELOP)) {
            Properties props = new Properties();
            BodyPart messageBodyPart = new MimeBodyPart();
            Session session = Session.getDefaultInstance(props, new Authenticator() { // 验账账户
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(GlobalConstants.emailUserName,
                            GlobalConstants.emailPassword);
                }
            });
            MimeMessage message = new MimeMessage(session);
            MimeMultipart multipart = new MimeMultipart();
            try {
                // 设置邮件HTML格式
                message.setContent("Email Content. ", "text/html");
                // 设置邮件标题
                message.setSubject(subject);
                // 设置邮件服务器
                props.put("mail.smtp.host", GlobalConstants.emailHost);// email.ele.me
                // 设置邮件发送地址
                props.put("mail.from", GlobalConstants.emailFrom);
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", GlobalConstants.emailSmtpPort);// 25
                message.setFrom();
                // 设置收件人
                message.setRecipients(Message.RecipientType.TO, to);
                // 设置发送时间
                message.setSentDate(new Date());
                // 设置邮件内容类型
                multipart.setSubType("related");

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

                // 将修改后的邮件内容放入邮件内容中
                messageBodyPart.setContent(content, "text/html;charset=gbk");
                multipart.addBodyPart(messageBodyPart);

                count = 1;
                // 再次遍历img节点集合
                for (XmlGen xg : sites) {
                    // 得到img节点src属性的值
                    String srcValue = xg.selectFirst("@src");
                    // 截取src属性值的图片名称
                    String fileName = srcValue.substring(srcValue.lastIndexOf("/") + 1);

                    // // 从属性文件中得到服务器上传文件所在主目录
                    // String path = GlobalConstants.fileUploadBase;
                    // // 得到服务器文件访问路径封装到对象中
                    // String destPath = path
                    // + File.separator
                    // + GlobalConstants.noticeImageDir
                    // + File.separator + fileName;
                    //
                    String destPath = attachments + fileName;


                    MimeBodyPart mbp_file = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(destPath);
                    mbp_file.setDataHandler(new DataHandler(fds));

                    // 加上这句将作为附件发送,否则将作为信件的文本内容
                    String postfix = fileName.substring(fileName.indexOf("."));
                    String newFileName = count.toString() + postfix;
                    // 设置附件文件名称
                    // mbp_file.setFileName(MimeUtility.encodeText(newFileName,
                    // "gbk", "B"));
                    // 将此文件作为内容标识,设置此属性，此附件将不显示在附件栏中,并且与邮件内容对应
                    mbp_file.setHeader("Content-ID", newFileName);
                    multipart.addBodyPart(mbp_file);

                    count++;
                }
                // 再次遍历img节点集合
                for (XmlGen xg : sites) {
                    // 得到img节点src属性的值
                    String srcValue = xg.selectFirst("@src");
                    // 截取src属性值的图片名称
                    String fileName = srcValue.substring(srcValue.lastIndexOf("/") + 1);
                    if (fileInfo != null && fileInfo.containsKey(fileName)) {
                        String destPath = attachments + fileName;


                        MimeBodyPart mbp_file = new MimeBodyPart();
                        FileDataSource fds = new FileDataSource(destPath);
                        mbp_file.setDataHandler(new DataHandler(fds));

                        // 加上这句将作为附件发送,否则将作为信件的文本内容
                        String postfix = fileName.substring(fileName.indexOf("."));
                        String newFileName = fileInfo.get(fileName) + postfix;
                        // 设置附件文件名称
                        mbp_file.setFileName(MimeUtility.encodeText(newFileName, "gbk", "B"));
                        // 将此文件作为内容标识,设置此属性，此附件将不显示在附件栏中,并且与邮件内容对应
                        mbp_file.setHeader("Content-ID", newFileName);
                        multipart.addBodyPart(mbp_file);
                    }
                }

                message.setContent(multipart);
                message.saveChanges();
                Transport transport = session.getTransport("smtp");
                transport.connect(GlobalConstants.emailHost, GlobalConstants.emailUserName,
                        GlobalConstants.emailPassword);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                logger.info(new SimpleDateFormat(DateUtil.ISO_DATE_FORMAT).format(new Date())
                        + "==>发带附件和图片的邮件给" + to + "，主题：" + subject);
            } catch (MessagingException mex) {
                logger.error(CommonUtil.getErrorMessage(mex));
            } catch (Exception ex) {
                logger.error(CommonUtil.getErrorMessage(ex));
            }

        }
    }

    /**
     * @param to
     * @param subject
     * @param content
     * @param attachments 附件字符串
     * @param listener    监听器 可以为空
     * @param mailId      邮件id 如果listener为空
     * @author sunwei
     * @since 2015年12月30日
     */
    @Async
    public void sendAttachmentHtmlMail(String to, String subject, String content, String attachments,
                                       CustomMailSenderListener listener, Integer mailId) {
        // 设置默认附件路径，resource目录下
        String attachmentBasePath = this.getClass().getResource("/").getPath();

        if (StringUtils.isNotBlank(GlobalConstants.ATTACHMENT_PATH)) {
            attachmentBasePath = GlobalConstants.ATTACHMENT_PATH; // 设置附件基本路径
        }

        // 不是正式环境，全部发送给测试人员
//    if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_PRODUCTION)) {
//      to = GlobalConstants.TEST_SENDTO;
//    }

        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_DEVELOP)) {
            Properties props = new Properties();
            BodyPart messageBodyPart = new MimeBodyPart();
            Session session = Session.getDefaultInstance(props, new Authenticator() { // 验账账户
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(GlobalConstants.emailUserName,
                            GlobalConstants.emailPassword);
                }
            });
            MimeMessage message = new MimeMessage(session);
            MimeMultipart multipart = new MimeMultipart();
            try {
                // 设置邮件HTML格式
                message.setContent("Email Content. ", "text/html");
                // 设置邮件标题
                message.setSubject(subject);
                // 设置邮件服务器
                props.put("mail.smtp.host", GlobalConstants.emailHost);// email.ele.me
                // 设置邮件发送地址
                props.put("mail.from", GlobalConstants.emailFrom);
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", GlobalConstants.emailSmtpPort);// 25
                message.setFrom();
                // 设置收件人
                message.setRecipients(Message.RecipientType.TO, to);
                // 设置发送时间
                message.setSentDate(new Date());
                // 设置邮件内容类型
                multipart.setSubType("related");

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

                // 将修改后的邮件内容放入邮件内容中
                messageBodyPart.setContent(content, "text/html;charset=gbk");
                multipart.addBodyPart(messageBodyPart);

                count = 1;
                // 再次遍历img节点集合
                for (XmlGen xg : sites) {
                    // 得到img节点src属性的值
                    String srcValue = xg.selectFirst("@src");
                    // 截取src属性值的图片名称
                    String fileName = srcValue.substring(srcValue.lastIndexOf("/") + 1);

                    String destPath = attachmentBasePath + File.separator + srcValue;

                    MimeBodyPart mbp_file = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(destPath);
                    mbp_file.setDataHandler(new DataHandler(fds));

                    // 加上这句将作为附件发送,否则将作为信件的文本内容
                    String postfix = fileName.substring(fileName.indexOf("."));
                    String newFileName = count.toString() + postfix;
                    // 设置附件文件名称
                    // mbp_file.setFileName(MimeUtility.encodeText(newFileName,
                    // "gbk", "B"));
                    // 将此文件作为内容标识,设置此属性，此附件将不显示在附件栏中,并且与邮件内容对应
                    mbp_file.setHeader("Content-ID", newFileName);
                    multipart.addBodyPart(mbp_file);
                    count++;
                }

                logger.info("附件内容:{}", attachments);
                // 遍历attachments 添加附件
                if (StringUtils.isNotBlank(attachments)) {
                    String[] attachmentArr = attachments.split(",");
                    for (String attachment : attachmentArr) {
                        String[] attachmentInfoArr = attachment.split("\\|");
                        if (attachmentInfoArr.length != 2) {
                            continue;
                        }
                        String destPath = attachmentInfoArr[0]; // 目标位置
                        String fileName = attachmentInfoArr[1]; // 文件名称

                        MimeBodyPart mbp_file = new MimeBodyPart();
                        logger.info("文件位置:{}", destPath);
                        FileDataSource fds = new FileDataSource(destPath);
                        mbp_file.setDataHandler(new DataHandler(fds));

                        // 设置附件文件名称
                        mbp_file.setFileName(MimeUtility.encodeText(fileName, "gbk", "B"));
                        // 将此文件作为内容标识,设置此属性，此附件将不显示在附件栏中,并且与邮件内容对应
                        mbp_file.setHeader("Content-ID", fileName);
                        multipart.addBodyPart(mbp_file);
                    }
                }

                message.setContent(multipart);
                message.saveChanges();
                Transport transport = session.getTransport("smtp");
                transport.connect(GlobalConstants.emailHost, GlobalConstants.emailUserName,
                        GlobalConstants.emailPassword);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                if (listener != null) {
                    listener.success(mailId, to, attachments);
                }
                logger.info(new SimpleDateFormat(DateUtil.ISO_DATE_FORMAT).format(new Date())
                        + "==>发带附件和图片的邮件给" + to + "，主题：" + subject);
            } catch (MessagingException mex) {
                logger.error(CommonUtil.getErrorMessage(mex));
                if (listener != null) {
                    listener.fail(mailId, to, attachments, mex);
                }
            } catch (Exception ex) {
                logger.error(CommonUtil.getErrorMessage(ex));
                if (listener != null) {
                    listener.fail(mailId, to, attachments, ex);
                }
            }
        }
    }

    /**
     * 发送带的图片的html邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param url
     * @param element
     * @param attr
     */
    @Async
    public void sendPicHtmlMail(String to, String subject, String content, String url, String element,
                                String attr) {
        // 不是正式环境，全部发送给测试人员
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_PRODUCTION)) {
            to = GlobalConstants.TEST_SENDTO;
        }
        if (!GlobalConstants.DEVMODEL.equals(GlobalConstants.DEVMODEL_DEVELOP)) {
            Properties props = new Properties();
            BodyPart messageBodyPart = new MimeBodyPart();
            Session session = Session.getDefaultInstance(props, new Authenticator() { // 验账账户
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(GlobalConstants.emailUserName,
                            GlobalConstants.emailPassword);
                }
            });
            MimeMessage message = new MimeMessage(session);
            MimeMultipart multipart = new MimeMultipart();
            try {
                // 设置邮件HTML格式
                message.setContent("Email Content. ", "text/html");
                // 设置邮件标题
                message.setSubject(subject);
                // 设置邮件服务器
                props.put("mail.smtp.host", GlobalConstants.emailHost);
                // 设置邮件发送地址
                props.put("mail.from", GlobalConstants.emailFrom);
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", GlobalConstants.emailSmtpPort);
                message.setFrom();
                // 设置收件人
                message.setRecipients(Message.RecipientType.TO, to);
                // 设置发送时间
                message.setSentDate(new Date());
                // 设置邮件内容类型
                multipart.setSubType("related");

                // 以XML格式读取HTML格式邮件内容
                XmlGen x = new XmlGen(TagSoupUtils.parse(content, "UTF8").getBytes("UTF-8"));

                // 使用xpath得到邮件内容中所有img节点集合
                List<XmlGen> sites = x.selectAllXml("//" + element);
                Integer count = 1;
                // 遍历img节点集合
                for (XmlGen xg : sites) {
                    // 得到img节点src属性的值
                    String srcValue = xg.selectFirst("@" + attr);
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

                // 将修改后的邮件内容放入邮件内容中
                messageBodyPart.setContent(content, "text/html;charset=gbk");
                multipart.addBodyPart(messageBodyPart);

                count = 1;
                // 再次遍历img节点集合
                for (XmlGen xg : sites) {
                    // 得到img节点src属性的值
                    String srcValue = xg.selectFirst("@" + attr);
                    // 截取src属性值的图片名称
                    String fileName = srcValue.substring(srcValue.lastIndexOf("/") + 1);

                    // // 从属性文件中得到服务器上传文件所在主目录
                    // String path = GlobalConstants.fileUploadBase;
                    // // 得到服务器文件访问路径封装到对象中
                    // String destPath = path
                    // + File.separator
                    // + GlobalConstants.noticeImageDir
                    // + File.separator + fileName;
                    //
                    String destPath = url + fileName;


                    MimeBodyPart mbp_file = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(destPath);
                    mbp_file.setDataHandler(new DataHandler(fds));

                    // 加上这句将作为附件发送,否则将作为信件的文本内容
                    String postfix = fileName.substring(fileName.indexOf("."));
                    String newFileName = count.toString() + postfix;
                    // 设置附件文件名称
                    // mbp_file.setFileName(MimeUtility.encodeText(newFileName,
                    // "gbk", "B"));
                    // 将此文件作为内容标识,设置此属性，此附件将不显示在附件栏中,并且与邮件内容对应
                    mbp_file.setHeader("Content-ID", newFileName);
                    multipart.addBodyPart(mbp_file);

                    count++;
                }
                // // 再次遍历img节点集合
                // for (XmlGen xg : sites) {
                // // 得到img节点src属性的值
                // String srcValue = xg.selectFirst("@src");
                // // 截取src属性值的图片名称
                // String fileName = srcValue.substring(srcValue
                // .lastIndexOf("/") + 1);
                // if(fileInfo!=null&&fileInfo.containsKey(fileName)){
                // String destPath = url + fileName;
                //
                //
                // MimeBodyPart mbp_file = new MimeBodyPart();
                // FileDataSource fds = new FileDataSource(destPath);
                // mbp_file.setDataHandler(new DataHandler(fds));
                //
                // // 加上这句将作为附件发送,否则将作为信件的文本内容
                // String postfix = fileName.substring(fileName.indexOf("."));
                // String newFileName = fileInfo.get(fileName) + postfix;
                // // 设置附件文件名称
                // mbp_file.setFileName(MimeUtility.encodeText(newFileName,
                // "gbk", "B"));
                // // 将此文件作为内容标识,设置此属性，此附件将不显示在附件栏中,并且与邮件内容对应
                // mbp_file.setHeader("Content-ID", newFileName);
                // multipart.addBodyPart(mbp_file);
                // }
                // }

                message.setContent(multipart);
                message.saveChanges();
                Transport transport = session.getTransport("smtp");
                transport.connect(GlobalConstants.emailHost, GlobalConstants.emailUserName,
                        GlobalConstants.emailPassword);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                logger.info(new SimpleDateFormat(DateUtil.ISO_DATE_FORMAT).format(new Date())
                        + "==>发带图片的邮件给" + to + "，主题：" + subject);
            } catch (MessagingException mex) {
                logger.error(CommonUtil.getErrorMessage(mex));
            } catch (Exception ex) {
                logger.error(CommonUtil.getErrorMessage(ex));
            }
        }
    }

}

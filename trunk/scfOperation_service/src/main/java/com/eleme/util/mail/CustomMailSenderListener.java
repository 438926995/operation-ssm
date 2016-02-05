package com.eleme.util.mail;

/**
 * 邮件发送监听器
 *
 * @author sunwei
 * @since 2015年12月29日
 */
public interface CustomMailSenderListener {

    /**
     * 邮件发送成功
     *
     * @author sunwei
     * @since 2015年12月29日
     * @param mailId 邮件id
     * @param sendTo 收件人
     */
    default void success(Integer mailId, String sendTo) {
    }

    /**
     * 邮件发送成功
     *
     * @param mailId
     * @param sendTo 收件人
     * @param attachments 附件
     * @author sunwei
     * @since 2016年1月3日
     */
    default void success(Integer mailId, String sendTo, String attachments) {
    }


    /**
     * 邮件发送失败
     *
     * @param ex
     * @author sunwei
     * @since 2015年12月29日
     */
    default void fail(Integer mailId, String sendTo, Exception ex) {
    }


    /**
     * 邮件发送失败
     *
     * @param mailId
     * @param sendTo 收件人
     * @param attachments
     * @param ex
     * @author sunwei
     * @since 2016年1月3日
     */
    default void fail(Integer mailId, String sendTo, String attachments, Exception ex) {
    }

}

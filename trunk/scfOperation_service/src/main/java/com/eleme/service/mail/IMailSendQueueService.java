package com.eleme.service.mail;

import com.eleme.domain.mart.mail.MailSendQueueEntity;

/**
 * 邮件发送队列业务 Service
 * 
 * @author sunwei
 * @since 2015年12月29日
 */
public interface IMailSendQueueService {

  /**
   * 往发送队列中添加一条邮件数据
   * 
   * @author sunwei
   * @since 2015年12月29日
   * @param mailInfo
   * @return 影响行数
   */
  public Integer addOneMailSendQueue(MailSendQueueEntity mailInfo);

  /**
   * 查询需要发送Mail
   * 
   * @author sunwei
   * @since 2015年12月29日
   * @param mailId
   */
  public MailSendQueueEntity getMailSendQueueById(Integer mailId);

  /**
   * 发送邮件
   * 
   * @author sunwei
   * @since 2015年12月29日
   * @param mailSendQueue
   */
  public void sendMail(MailSendQueueEntity mailSendQueue);


}

package com.eleme.mapper.mart.mail;

import com.eleme.domain.mart.mail.MailSendQueueEntity;

/**
 * 邮件发送队列 Mapper
 * 
 * @author sunwei
 * @since 2015年12月29日
 */
public interface MailSendQueueMapper {


  /**
   * 向邮件队列中添加一条数据
   * 
   * @author sunwei
   * @since 2015年12月29日
   * @param mailSendQueue
   * @return 插入id
   */
  public int insertMailSendQueue(MailSendQueueEntity mailSendQueue);

  /**
   * 
   * 根据主键查询出MailSendQueue对象
   * 
   * @author sunwei
   * @since 2015年12月29日
   * @param id
   * @return
   */
  public MailSendQueueEntity selectMailSendQueueById(Integer mailId);


  /**
   * 更新发送队列数据
   * 
   * @author sunwei
   * @since 2015年12月29日
   * @param mailSendQueue
   * @return
   */
  public void updateMailSendQueue(MailSendQueueEntity mailSendQueue);

}

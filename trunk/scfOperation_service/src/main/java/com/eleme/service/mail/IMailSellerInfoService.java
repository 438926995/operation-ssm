package com.eleme.service.mail;

import java.util.List;

import com.eleme.domain.mart.PageResData;
import com.eleme.domain.mart.mail.MailSellerInfoEntity;
import com.eleme.domain.mart.mail.MailSellerInfoListQueryBean;
import com.eleme.domain.mart.mail.MailSellerInfoVO;
import com.eleme.domain.mart.mail.MailSendQueueEntity;

/**
 * 邮件service 接口
 * 
 * @author sunwei
 * @since 2015年12月25日
 */
public interface IMailSellerInfoService {

  /**
   * 获得申请发送商户信息邮件数据列表
   * 
   * @author sunwei
   * @since 2015年12月25日
   * @return
   */
  public PageResData<MailSellerInfoVO> getMailSellerInfoPage(MailSellerInfoListQueryBean queryBean);

  /**
   * 批量发送商户信息邮件
   * 
   * @author sunwei
   * @since 2015年12月29日
   * @param mailSellerInfoIds
   */
  public void batchSendSelllerInfoMail(String[] mailSellerInfoIds);

  /**
   * 获取一个商户邮件信息
   * 
   * @author sunwei
   * @since 2015年12月29日
   * @param mailId
   */
  public MailSellerInfoVO getMailSellerInfoById(Integer mailId);

  /**
   * 发送商户信息邮件
   * 
   * @author sunwei
   * @since 2015年12月29日
   * @param mailSellerInfos 商户信息邮件
   */
  public void sendSelllerInfoMail(List<MailSellerInfoVO> mailSellerInfos);

}

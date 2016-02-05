package com.eleme.mapper.mart.mail;

import java.util.List;

import com.eleme.domain.mart.mail.MailSellerInfoEntity;
import com.eleme.domain.mart.mail.MailSellerInfoListQueryBean;
import com.eleme.domain.mart.mail.MailSellerInfoVO;

/**
 * 商户信息邮件Mapper
 * 
 * @author sunwei
 * @since 2015年12月25日
 */
public interface MailSellerInfoMapper {

  /**
   * 查询 商户信息邮件 申请发送的数据记录表
   * 
   * @author sunwei
   * @since 2015年12月25日
   * @param queryBean
   * @return 返回商户信息列表数据
   */
  public List<MailSellerInfoVO> selectMailSellerInfos(MailSellerInfoListQueryBean queryBean);

  /**
   * 查询 商户信息邮件 申请发送的数据数量
   * 
   * @author sunwei
   * @since 2015年12月27日
   * @param queryBean
   * @return 返回商户信息列表总数
   */
  public Integer selectMailSellerInfosCount(MailSellerInfoListQueryBean queryBean);

  /**
   * 查询 商户信息邮件 通过ids
   * 
   * @author sunwei
   * @since 2015年12月29日
   * @param simIds
   * @return
   */
  public List<MailSellerInfoVO> selectMailSellerInfosByIds(String[] simIds);

  /**
   * 查询Mail seller info 通过id
   * 
   * @author sunwei
   * @since 2015年12月29日
   * @param mailSellerInfoId
   * @return
   */
  public MailSellerInfoVO selectMailSellerInfoVOById(Integer mailSellerInfoId);

  /**
   * 根据主键更新
   * 
   * @author sunwei
   * @since 2016年1月3日
   * @param mailSellerInfoEntity
   */
  public void updateById(MailSellerInfoEntity mailSellerInfoEntity);


}

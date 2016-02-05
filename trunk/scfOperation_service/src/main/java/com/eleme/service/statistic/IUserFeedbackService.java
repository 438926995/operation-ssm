package com.eleme.service.statistic;

import java.util.List;
import java.util.Map;

import com.eleme.bean.statistic.UserFeedbackQuerybean;
import com.eleme.domain.mart.statistic.UserFeedback;
import com.eleme.util.pager.TbData;

/**
 * 
 * @author xudong.gu
 *
 */
public interface IUserFeedbackService {

  /**
   * 根据用户类别 查询用户反馈
   * 
   * @param cate 用户类别
   * @return
   * @throws Exception
   */
  List<UserFeedback> getUserFeedbackByCate(int cate) throws Exception;

  /**
   * 根据用户类别和创建时间 查询用户反馈信息
   * 
   * @param map 用户类别和创建时间 封装
   * @return
   * @throws Exception
   */
  List<UserFeedback> getUserFeedbackByCateAndTime(Map<String, Object> map) throws Exception;

  /**
   * 获取用户反馈列表
   * 
   * @param ufqb 查询条件
   * @param currentPage 当前页
   * @return
   * @throws Exception
   */
  public TbData getUserFeedbackList(UserFeedbackQuerybean ufqb, Integer currentPage)
      throws Exception;

}

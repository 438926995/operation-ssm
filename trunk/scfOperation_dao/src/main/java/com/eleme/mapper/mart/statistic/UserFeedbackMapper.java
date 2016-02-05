package com.eleme.mapper.mart.statistic;

import java.util.List;
import java.util.Map;

import com.eleme.domain.mart.statistic.UserFeedback;

/**
 * 统计用户反馈信息接口
 * 
 * @author xudong.gu
 *
 */
public interface UserFeedbackMapper {

  /**
   * 根据用户类型 获得用户反馈信息
   * 
   * @param userCate 用户类型
   * @return 对应用户类型的所有用户反馈信息封装
   * @throws Exception
   */
  List<UserFeedback> selectUserFeedbacksByCate(int userCate) throws Exception;

  /**
   * 根据用户类型和 时间节点 获得用户反馈信息总数
   * 
   * @param map
   * @return totalCount
   */
  int selectUserFeedbacksByCateAndTimeTotal(Map<String, Object> map);

  /**
   * 根据用户类型和 时间节点 获得用户反馈信息
   * 
   * @param map 用户类型, 开始时间, 结束时间的封装
   * @return 对应用户类型的所有用户反馈信息封装
   * @throws Exception
   */
  List<UserFeedback> selectUserFeedbacksByCateAndTime(Map<String, Object> map) throws Exception;

}

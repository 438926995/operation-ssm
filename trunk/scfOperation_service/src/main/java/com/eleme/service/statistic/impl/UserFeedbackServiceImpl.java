package com.eleme.service.statistic.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.eleme.bean.statistic.UserFeedbackQuerybean;
import com.eleme.constants.PagerConstants;
import com.eleme.domain.mart.statistic.UserFeedback;
import com.eleme.mapper.mart.statistic.UserFeedbackMapper;
import com.eleme.service.BaseService;
import com.eleme.service.statistic.IUserFeedbackService;
import com.eleme.util.pager.TbData;

@Service
public class UserFeedbackServiceImpl extends BaseService implements IUserFeedbackService {

  @Inject
  private UserFeedbackMapper userFeedbackMapper;

  @Override
  public List<UserFeedback> getUserFeedbackByCate(int cate) throws Exception {
    return userFeedbackMapper.selectUserFeedbacksByCate(cate);
  }

  @Override
  public List<UserFeedback> getUserFeedbackByCateAndTime(Map<String, Object> map) throws Exception {
    return userFeedbackMapper.selectUserFeedbacksByCateAndTime(map);
  }

  @Override
  public TbData getUserFeedbackList(UserFeedbackQuerybean ufqb, Integer currentPage)
      throws Exception {
    // 初始化currentPage为空
    if (currentPage == null) {
      currentPage = 1;
    }

    // 封装sql查询条件
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("userCate", ufqb.getUserCate());
    map.put("startTime", ufqb.getSubmitTimeFrom());
    map.put("endTime", ufqb.getSubmitTimeEnd());

    int totalCount = userFeedbackMapper.selectUserFeedbacksByCateAndTimeTotal(map);

    map.put("startRecord", (currentPage - 1) * PagerConstants.PAGE_SIZE);
    map.put("pageSize", PagerConstants.PAGE_SIZE);

    List<UserFeedback> userFeedbacks = userFeedbackMapper.selectUserFeedbacksByCateAndTime(map);

    return initTbData(totalCount, currentPage, PagerConstants.PAGE_SIZE, userFeedbacks);
  }

}

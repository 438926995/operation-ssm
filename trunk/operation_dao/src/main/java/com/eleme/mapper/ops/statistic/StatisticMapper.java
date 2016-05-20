package com.eleme.mapper.ops.statistic;

import com.eleme.bean.feedback.FeedbackQueryBean;
import com.eleme.bean.feedback.ReplyFeedback;
import com.eleme.bean.feedback.UserFeedback;

import java.util.List;


/**
 * Created by huwenwen on 16/5/20.
 */
public interface StatisticMapper {

  public List<UserFeedback> selectUserFeedbakList(FeedbackQueryBean fqb);

  public int selectUserFeedbackListCount(FeedbackQueryBean fqb);

  public int updateUserFeedbackToReply(ReplyFeedback rf);
}

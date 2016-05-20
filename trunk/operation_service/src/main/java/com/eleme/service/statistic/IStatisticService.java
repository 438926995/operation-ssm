package com.eleme.service.statistic;

import com.eleme.bean.feedback.FeedbackQueryBean;
import com.eleme.bean.feedback.ReplyFeedback;
import com.eleme.bean.loan.LoanQueryBean;
import com.eleme.util.pager.TbData;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Map;

/**
 * Created by huwenwen on 16/5/14.
 */
public interface IStatisticService {

    public Map<String, Object> getLoanStatistic(LoanQueryBean lqb);

    public XSSFWorkbook exportAppNumberInfo(LoanQueryBean lqb);

    public TbData getFeedbackInfo(FeedbackQueryBean fqb);

    public int replyFeedback(ReplyFeedback rf);
}

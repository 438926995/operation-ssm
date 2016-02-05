package com.eleme.service.quartz;

import java.util.Date;
import java.util.List;

import com.eleme.bean.quartz.QuartzJobHistoryBean;
import com.eleme.bean.quartz.QuartzJobsBean;
import com.eleme.bean.quartz.QuartzTriggerHistoryBean;

/**
 * 定时任务管理业务方法接口.
 * 
 * @author penglau
 *
 */
public interface IQuartzJobService {

  /**
   * 查找某段时间范围的Job历史.
   * 
   * @param start 开始时间
   * @param end 结束时间
   * @return 符合条件的job的历史集合
   */
  List<QuartzJobHistoryBean> getJobHistoryBetween(Date start, Date end);

  /**
   * 查找所有的job历史.
   * 
   * @return 所有job历史集合
   */
  List<QuartzJobHistoryBean> getAllJobHistory();

  /**
   * 查找所有的trigger记录。
   * 
   * @return trigger历史记录集合
   */
  List<QuartzTriggerHistoryBean> getAllTriggerHistory();

  /**
   * 查找所有的QuartzJob.
   * 
   * @return 所有QuartzJob集合
   */
  List<QuartzJobsBean> getAllJobs();

}

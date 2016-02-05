package com.eleme.bean.quartz;

import java.util.Date;

/**
 * QuartzJob历史封装bean（主要是QRTZ_JOB_HISTORY表的数据）.
 * 
 * @author penglau
 *
 */
public class QuartzJobHistoryBean {

  private long historyId;
  private String fireInstanceId;
  private String jobName;
  private String jobGroup;
  private String triggerName;
  private String triggerGroup;
  private boolean success;
  private Date start;
  private Date finish;

  public long getHistoryId() {
    return historyId;
  }

  public void setHistoryId(long historyId) {
    this.historyId = historyId;
  }

  public String getFireInstanceId() {
    return fireInstanceId;
  }

  public void setFireInstanceId(String fireInstanceId) {
    this.fireInstanceId = fireInstanceId;
  }

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  public String getJobGroup() {
    return jobGroup;
  }

  public void setJobGroup(String jobGroup) {
    this.jobGroup = jobGroup;
  }

  public String getTriggerName() {
    return triggerName;
  }

  public void setTriggerName(String triggerName) {
    this.triggerName = triggerName;
  }

  public String getTriggerGroup() {
    return triggerGroup;
  }

  public void setTriggerGroup(String triggerGroup) {
    this.triggerGroup = triggerGroup;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public Date getStart() {
    return start;
  }

  public void setStart(Date start) {
    this.start = start;
  }

  public Date getFinish() {
    return finish;
  }

  public void setFinish(Date finish) {
    this.finish = finish;
  }

}

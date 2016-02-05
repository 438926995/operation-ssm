package com.eleme.bean.quartz;

import java.util.Date;

/**
 * trigger历史封装类(主要是QRTZ_TRIGGER_HISTORY表).
 * 
 * @author penglau
 *
 */
public class QuartzTriggerHistoryBean {

  private long triggerId;
  private String triggerName;
  private String triggerGroup;
  private Date scheduledStartDate;
  private Date actualStartDate;
  private Date finishDate;
  private boolean misfire;

  public long getTriggerId() {
    return triggerId;
  }

  public void setTriggerId(long triggerId) {
    this.triggerId = triggerId;
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

  public Date getScheduledStartDate() {
    return scheduledStartDate;
  }

  public void setScheduledStartDate(Date scheduledStartDate) {
    this.scheduledStartDate = scheduledStartDate;
  }

  public Date getActualStartDate() {
    return actualStartDate;
  }

  public void setActualStartDate(Date actualStartDate) {
    this.actualStartDate = actualStartDate;
  }

  public Date getFinishDate() {
    return finishDate;
  }

  public void setFinishDate(Date finishDate) {
    this.finishDate = finishDate;
  }

  public boolean isMisfire() {
    return misfire;
  }

  public void setMisfire(boolean misfire) {
    this.misfire = misfire;
  }

}

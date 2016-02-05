package com.eleme.service.quartz.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.eleme.bean.quartz.QuartzJobHistoryBean;
import com.eleme.bean.quartz.QuartzJobsBean;
import com.eleme.bean.quartz.QuartzTriggerHistoryBean;
import com.eleme.service.quartz.IQuartzJobService;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * QuartzJob管理的业务方法.
 * 
 * @author penglau
 *
 */
@Service
public class QuartzJobServiceImpl implements IQuartzJobService {
  @Inject
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<QuartzJobsBean> getAllJobs() {
    String sql =
        "select tr.JOB_NAME, tr.JOB_GROUP, tr.TRIGGER_NAME, tr.TRIGGER_GROUP, tr.IS_VOLATILE, "
            + " tr.DESCRIPTION, ctr.CRON_EXPRESSION, tr.NEXT_FIRE_TIME,"
            + " tr.PREV_FIRE_TIME, tr.TRIGGER_STATE, tr.TRIGGER_TYPE, tr.START_TIME, tr.END_TIME "
            + " from QRTZ_TRIGGERS tr"
            + " left join QRTZ_CRON_TRIGGERS ctr on tr.TRIGGER_NAME = ctr.TRIGGER_NAME";

    return jdbcTemplate.query(sql, new QuartzJobsRowMapper());
  }

  @Override
  public List<QuartzTriggerHistoryBean> getAllTriggerHistory() {
    String sql = "select * from QRTZ_TRIGGER_HISTORY";
    return jdbcTemplate.query(sql, new QuartzTriggerHistoryRowMapper());
  }

  @Override
  public List<QuartzJobHistoryBean> getAllJobHistory() {
    String sql = "select * from QRTZ_JOB_HISTORY";
    return extractQuartzJobHistoryBeans(sql, null);
  }

  @Override
  public List<QuartzJobHistoryBean> getJobHistoryBetween(Date start, Date end) {
    String sql = "select * from QRTZ_JOB_HISTORY where ";
    List<Object> params = Lists.newArrayList();

    if (start != null) {
      sql += " START >= ? ";
      params.add(start);
    }
    if (end != null) {
      sql += " and END <= ?";
      params.add(end);
    }
    return extractQuartzJobHistoryBeans(sql, Iterables.toArray(params, Object.class));
  }

  /**
   * transform db records into QuartzJobHistoryBeans
   *
   * @param sql SQL to retrieve quartz log
   * @param params sql parameters
   * @return list of QuartzJobHistoryBeans
   */
  private List<QuartzJobHistoryBean> extractQuartzJobHistoryBeans(String sql, Object[] params) {
    if (params == null || params.length == 0) {
      return jdbcTemplate.query(sql, new QuartzJobHistoryRowMapper());
    } else {
      return jdbcTemplate.query(sql, params, new QuartzJobHistoryRowMapper());
    }
  }

  /**
   * Transform DB records into a QuartzJobHistoryBean instance
   */
  private static class QuartzJobHistoryRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
      QuartzJobHistoryBean hist = new QuartzJobHistoryBean();
      hist.setHistoryId(rs.getLong("HISTORY_ID"));
      hist.setFireInstanceId(rs.getString("FIRE_INSTANCE_ID"));
      hist.setJobName(rs.getString("JOB_NAME"));
      hist.setJobGroup(rs.getString("JOB_GROUP"));
      hist.setTriggerName(rs.getString("TRIGGER_NAME"));
      hist.setTriggerGroup(rs.getString("TRIGGER_GROUP"));
      hist.setSuccess(rs.getBoolean("SUCCESS"));
      hist.setStart(rs.getTimestamp("START_DATE"));
      hist.setFinish(rs.getTimestamp("FINISH_DATE"));

      return hist;
    }
  }

  /**
   * row mapper to transform db record into QuartzTriggerHistoryBean
   */
  private static class QuartzTriggerHistoryRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
      QuartzTriggerHistoryBean historyBean = new QuartzTriggerHistoryBean();
      historyBean.setTriggerId(rs.getLong("TRIGGER_ID"));
      historyBean.setTriggerName(rs.getString("TRIGGER_NAME"));
      historyBean.setTriggerGroup(rs.getString("TRIGGER_GROUP"));
      historyBean.setScheduledStartDate(rs.getTimestamp("SCHEDULED_START_DATE"));
      historyBean.setActualStartDate(rs.getTimestamp("ACTUAL_START_DATE"));
      historyBean.setFinishDate(rs.getTimestamp("FINISH_DATE"));
      historyBean.setMisfire(rs.getBoolean("MISFIRE"));

      return historyBean;
    }
  }

  private static class QuartzJobsRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
      QuartzJobsBean jobBean = new QuartzJobsBean();
      jobBean.setName(rs.getString("JOB_NAME"));
      jobBean.setJobGroupName(rs.getString("JOB_GROUP"));
      jobBean.setTriggerName(rs.getString("TRIGGER_NAME"));
      jobBean.setTriggerGroupName(rs.getString("TRIGGER_GROUP"));
      jobBean.setVolatileJob(rs.getBoolean("IS_VOLATILE"));
      jobBean.setDescription(rs.getString("DESCRIPTION"));
      Long nextFireTime = rs.getLong("NEXT_FIRE_TIME");
      if (nextFireTime > 0L)
        jobBean.setNextFireTime(new Date(nextFireTime));

      Long prevFireTime = rs.getLong("PREV_FIRE_TIME");
      if (prevFireTime > 0L)
        jobBean.setPrevFireTime(new Date(prevFireTime));

      jobBean.setTriggerState(rs.getString("TRIGGER_STATE"));
      jobBean.setTriggerType(rs.getString("TRIGGER_TYPE"));
      jobBean.setCronExpression(rs.getString("CRON_EXPRESSION"));
      Long startTime = rs.getLong("START_TIME");
      if (startTime > 0L)
        jobBean.setStartTime(new Date(startTime));

      Long endTime = rs.getLong("END_TIME");
      if (endTime > 0L)
        jobBean.setEndTime(new Date(endTime));

      return jobBean;
    }
  }
}

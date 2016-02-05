package com.eleme.bean.statistic;

import java.util.Date;

/**
 * 贷款审核统计查询条件封装类
 * 
 * @author huwenwen
 *
 */
public class LoanQueryBean {
	//开始时间
	private Date submitTimeFrom;
	//截至时间
	private Date submitTimeEnd;
	//贷款产品Id
	private Integer fpId;
	//时间段
	private Integer period;
	
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public Date getSubmitTimeFrom() {
		return submitTimeFrom;
	}
	public void setSubmitTimeFrom(Date submitTimeFrom) {
		this.submitTimeFrom = submitTimeFrom;
	}
	public Date getSubmitTimeEnd() {
		return submitTimeEnd;
	}
	public void setSubmitTimeEnd(Date submitTimeEnd) {
		this.submitTimeEnd = submitTimeEnd;
	}
	public Integer getFpId() {
		return fpId;
	}
	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}
}

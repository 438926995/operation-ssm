package com.eleme.domain.mart.statistic;

import java.io.Serializable;
import java.util.Date;

/**
 * 贷款审核统计页面现实封装类
 * 
 * @author huwenwen
 *
 */
public class LoanAudit implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -893493749515783440L;
	//申请状态
	private String appStatus;
	//申请时间
	private Date submitTime;
	//统计数量
	private int count;
	//通过的人数
	private int passNumber;
	//未通过的人数
	private int disPassNumber;
	//审核中的人数
	private int auditNumber;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPassNumber() {
		return passNumber;
	}
	public void setPassNumber(int passNumber) {
		this.passNumber = passNumber;
	}
	public int getDisPassNumber() {
		return disPassNumber;
	}
	public void setDisPassNumber(int disPassNumber) {
		this.disPassNumber = disPassNumber;
	}
	public int getAuditNumber() {
		return auditNumber;
	}
	public void setAuditNumber(int auditNumber) {
		this.auditNumber = auditNumber;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	

}

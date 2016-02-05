package com.eleme.bean.statistic;
/**
 * 贷款人数Bean
 * 
 * @author huwenwen
 *
 */
public class LNumberBean {

	// 提交贷款申请人数
	private int sumNumber;
	// 通过人数
	private int passNumber;
	// 未通过人数
	private int disPassNumber;
	// 审核中人数
	private int auditNumber;
	public LNumberBean(int sumNumber, int passNumber, int disPassNumber, int auditNumber) {
		super();
		this.sumNumber = sumNumber;
		this.passNumber = passNumber;
		this.disPassNumber = disPassNumber;
		this.auditNumber = auditNumber;
	}
	
	public LNumberBean() {
		super();
	}

	public int getSumNumber() {
		return sumNumber;
	}
	public void setSumNumber(int sumNumber) {
		this.sumNumber = sumNumber;
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

}

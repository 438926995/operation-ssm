package com.eleme.domain.mart.statistic;

/**
 * 贷款人数和涨幅Flg的Bean
 * 
 * @author huwenwen
 *
 */
public class LoanNumberBean {
	
	//提交贷款申请人数
	private int sumNumber;
	//通过人数
	private int passNumber;
	//未通过人数
	private int disPassNumber;
	//审核中人数
	private int auditNumber;
	//提交贷款申请人数比前一天涨幅
	private int sumFlg;
	//通过人数比前一天涨幅
	private int passFlg;
	//未通过人数比前一天涨幅
	private int disPassFlg;
	//审核中人数比前一天涨幅
	private int auditFlg;
	public int getSumFlg() {
		return sumFlg;
	}
	public void setSumFlg(int sumFlg) {
		this.sumFlg = sumFlg;
	}
	public int getPassFlg() {
		return passFlg;
	}
	public void setPassFlg(int passFlg) {
		this.passFlg = passFlg;
	}
	public int getDisPassFlg() {
		return disPassFlg;
	}
	public void setDisPassFlg(int disPassFlg) {
		this.disPassFlg = disPassFlg;
	}
	public int getAuditFlg() {
		return auditFlg;
	}
	public void setAuditFlg(int auditFlg) {
		this.auditFlg = auditFlg;
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

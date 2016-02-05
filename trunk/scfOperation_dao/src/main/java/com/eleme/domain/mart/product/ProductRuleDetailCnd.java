package com.eleme.domain.mart.product;

/**
 * 
 * 产品规则明细查询条件封装类
 * 
 * @author yonglin.zhu
 *
 */
public class ProductRuleDetailCnd {
	// 分页开始
	private Integer startRecord;
	// 页面显示条数
	private Integer pageSize;
	// 规则类型
	private Integer ruleType;
	// 产品ID
	private Integer fpId;
	// 规则名
	private String ruleName;

	// 规则ID
	private Integer prId;

	public Integer getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(Integer startRecord) {
		this.startRecord = startRecord;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public Integer getFpId() {
		return fpId;
	}

	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getPrId() {
		return prId;
	}

	public void setPrId(Integer prId) {
		this.prId = prId;
	}

}

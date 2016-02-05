package com.eleme.domain.mart.product;

import java.util.Date;

/**
 * 产品规则明细封装类
 * 
 * @author yonglin.zhu
 *
 */
public class MProductRuleDetail {
	// 规则ID
	private Integer prId;
	// 产品ID
	private Integer fpId;
	// 规则名
	private String ruleName;
	// 规则内容
	private String ruleContent;
	// 规则类型 1：申请条件 2：所需材料
	private Integer ruleType;
	// 状态（0 删除，1 正常）
	private Integer delFlag;
	// 创建时间
	private Date createTime;

	public Integer getPrId() {
		return prId;
	}

	public void setPrId(Integer prId) {
		this.prId = prId;
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
		this.ruleName = ruleName == null ? null : ruleName.trim();
	}

	public String getRuleContent() {
		return ruleContent;
	}

	public void setRuleContent(String ruleContent) {
		this.ruleContent = ruleContent == null ? null : ruleContent.trim();
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
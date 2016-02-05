package com.eleme.bean.product;

import java.io.Serializable;

import javax.validation.GroupSequence;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.eleme.validatorgroup.First;
import com.eleme.validatorgroup.Four;
import com.eleme.validatorgroup.Second;
import com.eleme.validatorgroup.Third;

/**
 * 融资产品申请条件添加界面的封装bean.
 * 
 * @author yonglin.zhu
 *
 */
@GroupSequence({ First.class, Second.class, Third.class, Four.class, ProductMaterialAddBean.class })
public class ProductMaterialAddBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8651487950299580193L;

	// 规则名称
	@NotEmpty(message = "{field.required}", groups = { First.class })
	@Length(min = 2, max = 100, message = "{field.range}", groups = { Second.class })
	private String ruleName;

	// 外键关联到产品表
	private Integer fpId;

	// 规则类型 1：申请条件 2：所需材料
	private Integer ruleType;

	// 规则ID
	private Integer prId;

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getFpId() {
		return fpId;
	}

	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public Integer getPrId() {
		return prId;
	}

	public void setPrId(Integer prId) {
		this.prId = prId;
	}

}

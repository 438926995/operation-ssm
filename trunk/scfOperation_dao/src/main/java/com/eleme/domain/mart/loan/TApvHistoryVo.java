package com.eleme.domain.mart.loan;

import java.io.Serializable;

/**
 * 审批历史记录表封装类
 * 
 * @author yonglin.zhu
 *
 */
public class TApvHistoryVo extends TApvHistory implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 487125229787931708L;
	// 是否是运营后台的人员审核 1:是 0:否
	private Integer isOps;

	public Integer getIsOps() {
		return isOps;
	}

	public void setIsOps(Integer isOps) {
		this.isOps = isOps;
	}

}
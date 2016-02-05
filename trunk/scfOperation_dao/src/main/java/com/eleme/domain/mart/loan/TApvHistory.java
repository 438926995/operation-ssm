package com.eleme.domain.mart.loan;

import java.io.Serializable;
import java.util.Date;

/**
 * 审批历史记录表封装类
 * 
 * @author yonglin.zhu
 *
 */
public class TApvHistory implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7716207711331655206L;
	// 主键ID
	private Integer ahId;
	// 单据ID
	private Integer slId;
	// 审批状态
	private String appStatus;
	// 审批意见
	private String opinions;
	// 审批时间
	private Date appDate;
	// 实际审批人
	private Integer reallyAppUserId;
	// 审批人用户id
	private String appUserIds;
	// 审批人用户姓名
	private String appUserNames;
	// 流程ID
	private Integer procsId;
	// 节点ID
	private Integer nodeId;
	// 节点名
	private String nodeName;

	public Integer getAhId() {
		return ahId;
	}

	public void setAhId(Integer ahId) {
		this.ahId = ahId;
	}

	public Integer getSlId() {
		return slId;
	}

	public void setSlId(Integer slId) {
		this.slId = slId;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus == null ? null : appStatus.trim();
	}

	public String getOpinions() {
		return opinions;
	}

	public void setOpinions(String opinions) {
		this.opinions = opinions == null ? null : opinions.trim();
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public Integer getReallyAppUserId() {
		return reallyAppUserId;
	}

	public void setReallyAppUserId(Integer reallyAppUserId) {
		this.reallyAppUserId = reallyAppUserId;
	}

	public String getAppUserIds() {
		return appUserIds;
	}

	public void setAppUserIds(String appUserIds) {
		this.appUserIds = appUserIds == null ? null : appUserIds.trim();
	}

	public String getAppUserNames() {
		return appUserNames;
	}

	public void setAppUserNames(String appUserNames) {
		this.appUserNames = appUserNames == null ? null : appUserNames.trim();
	}

	public Integer getProcsId() {
		return procsId;
	}

	public void setProcsId(Integer procsId) {
		this.procsId = procsId;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName == null ? null : nodeName.trim();
	}
}
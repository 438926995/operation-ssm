package com.eleme.domain.mart.product;

/**
 * 
 * 融资产品查询条件封装类
 * 
 * @author yonglin.zhu
 *
 */
public class ProductQueryCnd {
	// 分页开始
	private Integer startRecord;
	// 页面显示条数
	private Integer pageSize;
	// 产品名称
	private String ptName;
	// 所属金融机构ID
	private Integer foId;
	// 状态
	private Integer productStatus;
	// 产品ID
	private Integer fpId;

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

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public Integer getFoId() {
		return foId;
	}

	public void setFoId(Integer foId) {
		this.foId = foId;
	}

	public Integer getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
	}

	public Integer getFpId() {
		return fpId;
	}

	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}

	
}

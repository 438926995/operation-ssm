package com.eleme.domain.mart.seller;

import java.io.Serializable;
import java.util.Date;

/**
 * 商铺现金流导出类
 * 
 * @author sunwei
 *
 */
public class ExportSellerFundsFlowBean implements Serializable {

	/**
	 * serial VersionUID
	 */
	private static final long serialVersionUID = 6662235882167137622L;

	/**
	 * 店铺Id
	 */
	private Long shopId;
	/**
	 * 店铺名称
	 */
	private String shopName;
	/**
	 * 订单号
	 */
	private String orderNum;
	/**
	 * 下单时间
	 */
	private Date orderTime;
	/**
	 * 金额
	 */
	private Float amount;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

}

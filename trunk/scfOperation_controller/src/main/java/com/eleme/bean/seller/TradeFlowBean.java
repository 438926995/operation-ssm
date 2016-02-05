package com.eleme.bean.seller;

import java.util.Date;

public class TradeFlowBean {
	private Date order_date;
	private String id;
	private String restaurant_id;
	private String restaurant_name;
	private String rst_owner_id;
	private String total;
	private String eleme_total;
	private Date settled_at;
	private Date created_at;
	private String source;
	private String delivery_status;
	private Date update_time;
	private String md5_phone_1;

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRestaurant_id() {
		return restaurant_id;
	}

	public void setRestaurant_id(String restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public String getRestaurant_name() {
		return restaurant_name;
	}

	public void setRestaurant_name(String restaurant_name) {
		this.restaurant_name = restaurant_name;
	}

	public String getRst_owner_id() {
		return rst_owner_id;
	}

	public void setRst_owner_id(String rst_owner_id) {
		this.rst_owner_id = rst_owner_id;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getEleme_total() {
		return eleme_total;
	}

	public void setEleme_total(String eleme_total) {
		this.eleme_total = eleme_total;
	}

	public Date getSettled_at() {
		return settled_at;
	}

	public void setSettled_at(Date settled_at) {
		this.settled_at = settled_at;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDelivery_status() {
		return delivery_status;
	}

	public void setDelivery_status(String delivery_status) {
		this.delivery_status = delivery_status;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getMd5_phone_1() {
		return md5_phone_1;
	}

	public void setMd5_phone_1(String md5_phone_1) {
		this.md5_phone_1 = md5_phone_1;
	}

}

package com.eleme.domain.mart.seller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 交易流水封装类
 * 
 * @author yonglin.zhu
 *
 */
public class TSdcTradeFlow {
  private Integer tfId;

  private Integer sellerId;

  private Integer oid;

  private Date orderDate;

  private String orderId;

  private String restaurantName;

  private Integer rstOwnerId;

  private Integer total;

  /**
   * 将total转化为浮点型字符串，并且转化为真实值
   */
  private Float totalFlo;

  private Integer elemeTotal;

  private Date settledAt;

  private Date createdAt;

  private String createdAtStr;

  private String source;

  private Integer deliveryStatus;

  private Date updatedAt;

  private String md5Phone1;


  public Integer getTfId() {
    return tfId;
  }

  public void setTfId(Integer tfId) {
    this.tfId = tfId;
  }

  public Integer getSellerId() {
    return sellerId;
  }

  public void setSellerId(Integer sellerId) {
    this.sellerId = sellerId;
  }

  public Integer getOid() {
    return oid;
  }

  public void setOid(Integer oid) {
    this.oid = oid;
  }

  public Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId == null ? null : orderId.trim();
  }

  public String getRestaurantName() {
    return restaurantName;
  }

  public void setRestaurantName(String restaurantName) {
    this.restaurantName = restaurantName == null ? null : restaurantName.trim();
  }

  public Integer getRstOwnerId() {
    return rstOwnerId;
  }

  public void setRstOwnerId(Integer rstOwnerId) {
    this.rstOwnerId = rstOwnerId;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public Integer getElemeTotal() {
    return elemeTotal;
  }

  public void setElemeTotal(Integer elemeTotal) {
    this.elemeTotal = elemeTotal;
  }

  public Date getSettledAt() {
    return settledAt;
  }

  public void setSettledAt(Date settledAt) {
    this.settledAt = settledAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Integer getDeliveryStatus() {
    return deliveryStatus;
  }

  public void setDeliveryStatus(Integer deliveryStatus) {
    this.deliveryStatus = deliveryStatus;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getMd5Phone1() {
    return md5Phone1;
  }

  public void setMd5Phone1(String md5Phone1) {
    this.md5Phone1 = md5Phone1 == null ? null : md5Phone1.trim();
  }

  public Float getTotalFlo() {
    if (total != null) {
      return (float) total / 100f;
    }
    return 0.00f;
  }

  public String getCreatedAtStr() {
    if (this.createdAt != null) {
      DateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return formate.format(createdAt);
    }
    return null;
  }



}

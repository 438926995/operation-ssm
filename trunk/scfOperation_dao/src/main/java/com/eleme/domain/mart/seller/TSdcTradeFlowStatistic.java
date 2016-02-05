package com.eleme.domain.mart.seller;

import java.util.Date;

/**
 * 商户流水统计对象
 * 
 * @author sunwei
 * @since 2015年12月15日
 *
 */
public class TSdcTradeFlowStatistic {

  /**
   * 流水统计Id
   */
  private Integer tfsId;
  /**
   * 年份
   */
  private Integer year;
  /**
   * 月份
   */
  private Integer month;
  /**
   * 总金额
   */
  private Float sumTotal = 0f;
  /**
   * 订单总数
   */
  private Integer orderCount = 0;
  /**
   * 该月日均订单
   */
  private Float perDayTotal = 0f;
  /**
   * 该商户Id
   */
  private Integer sellerId;

  /**
   * 统计类型：0月统计，1所有数据统计
   */
  private Integer type = 0;

  /**
   * 创建时间
   */
  private Date createdAt;
  /**
   * 更新时间
   */
  private Date updatedAt;

  public Integer getTfsId() {
    return tfsId;
  }

  public void setTfsId(Integer tfsId) {
    this.tfsId = tfsId;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getMonth() {
    return month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }

  public Float getSumTotal() {
    return sumTotal;
  }

  public void setSumTotal(Float sumTotal) {
    this.sumTotal = sumTotal;
  }

  /**
   * 增加流水金额
   * 
   * @param total
   */
  public void addSumTotal(Float total) {
    this.sumTotal += total;
  }

  public Integer getOrderCount() {
    return orderCount;
  }

  public void setOrderCount(Integer orderCount) {
    this.orderCount = orderCount;
  }

  /**
   * 新增 数量
   * 
   * @param count
   */
  public void addOrderCount(Integer count) {
    this.orderCount += count;
  }

  public Float getPerDayTotal() {
    return perDayTotal;
  }

  public void setPerDayTotal(Float perDayTotal) {
    this.perDayTotal = perDayTotal;
  }

  public Integer getSellerId() {
    return sellerId;
  }

  public void setSellerId(Integer sellerId) {
    this.sellerId = sellerId;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

}

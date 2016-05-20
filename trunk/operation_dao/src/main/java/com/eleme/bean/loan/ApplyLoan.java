package com.eleme.bean.loan;

import java.util.Date;

/**
 * Created by huwenwen on 16/5/14.
 */
public class ApplyLoan {
    private Integer slId;
    private Integer fpId;
    private String fpName;
    private Integer loanAmount;
    private Integer userId;
    private String realName;
    private Date submitTime;
    private String appStatus;
    private String docNo;
    private Integer apvUserId;
    private String apvUserName;
    private Date apvTime;
    private String apvRemark;

    public Integer getApvUserId() {
        return apvUserId;
    }

    public void setApvUserId(Integer apvUserId) {
        this.apvUserId = apvUserId;
    }

    public String getApvUserName() {
        return apvUserName;
    }

    public void setApvUserName(String apvUserName) {
        this.apvUserName = apvUserName;
    }

    public Date getApvTime() {
        return apvTime;
    }

    public void setApvTime(Date apvTime) {
        this.apvTime = apvTime;
    }

    public String getApvRemark() {
        return apvRemark;
    }

    public void setApvRemark(String apvRemark) {
        this.apvRemark = apvRemark;
    }

    public String getFpName() {
        return fpName;
    }

    public void setFpName(String fpName) {
        this.fpName = fpName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSlId() {
        return slId;
    }

    public void setSlId(Integer slId) {
        this.slId = slId;
    }

    public Integer getFpId() {
        return fpId;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }
}

package com.eleme.bean.loan;

/**
 * Created by huwenwen on 16/5/15.
 */
public class ApplyLoanVo extends ApplyLoan {

    // 个人信息
    private String userName;
    private String mobilePhone;
    private Integer userAge;
    private String userAddr;
    private Integer userSex;
    private String userSid;
    // 产品信息
    private double minRaitRaito;
    private double maxRaitRaito;
    private Integer minLoanAmount;
    private Integer maxLoanAmount;
    private String payLimit;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserSid() {
        return userSid;
    }

    public void setUserSid(String userSid) {
        this.userSid = userSid;
    }

    public double getMinRaitRaito() {
        return minRaitRaito;
    }

    public void setMinRaitRaito(double minRaitRaito) {
        this.minRaitRaito = minRaitRaito;
    }

    public double getMaxRaitRaito() {
        return maxRaitRaito;
    }

    public void setMaxRaitRaito(double maxRaitRaito) {
        this.maxRaitRaito = maxRaitRaito;
    }

    public Integer getMinLoanAmount() {
        return minLoanAmount;
    }

    public void setMinLoanAmount(Integer minLoanAmount) {
        this.minLoanAmount = minLoanAmount;
    }

    public Integer getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public void setMaxLoanAmount(Integer maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    public String getPayLimit() {
        return payLimit;
    }

    public void setPayLimit(String payLimit) {
        this.payLimit = payLimit;
    }
}

package com.eleme.bean.product;

import java.util.List;

public class MFinanceProduct {

    private Integer fpId;
    private String fpName;
//    private String fpArea;
//    private String fpAreaName;
    private Integer minLoanAmount;
    private Integer maxLoanAmount;
    private String payLimit;
    private String payLimitTo;
    private double minRaitRatio;
    private double maxRaitRatio;
    private Integer productStatus;
    private String imageUrl;
    private String otherDesc;
    private List<ProductRuleDetail> ruleList;

    public List<ProductRuleDetail> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<ProductRuleDetail> ruleList) {
        this.ruleList = ruleList;
    }

    public String getPayLimitTo() {
        return payLimitTo;
    }

    public void setPayLimitTo(String payLimitTo) {
        this.payLimitTo = payLimitTo;
    }

    public Integer getFpId() {
        return fpId;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }

    public String getFpName() {
        return fpName;
    }

    public void setFpName(String fpName) {
        this.fpName = fpName;
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

    public double getMinRaitRatio() {
        return minRaitRatio;
    }

    public void setMinRaitRatio(double minRaitRatio) {
        this.minRaitRatio = minRaitRatio;
    }

    public double getMaxRaitRatio() {
        return maxRaitRatio;
    }

    public void setMaxRaitRatio(double maxRaitRatio) {
        this.maxRaitRatio = maxRaitRatio;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOtherDesc() {
        return otherDesc;
    }

    public void setOtherDesc(String otherDesc) {
        this.otherDesc = otherDesc;
    }

}

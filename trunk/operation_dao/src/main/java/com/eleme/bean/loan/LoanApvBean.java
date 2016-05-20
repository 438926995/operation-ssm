package com.eleme.bean.loan;

/**
 * Created by huwenwen on 16/5/15.
 */
public class LoanApvBean {
    private Integer slId;
    private String remark;
    private Integer approveFlag;
    private Integer apvUserId;
    private String appStatus;

    public String getAppStatus() {
        if(approveFlag == 1){
            appStatus = "C";
        } else {
            appStatus = "D";
        }
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public Integer getSlId() {
        return slId;
    }

    public void setSlId(Integer slId) {
        this.slId = slId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getApproveFlag() {
        return approveFlag;
    }

    public void setApproveFlag(Integer approveFlag) {
        this.approveFlag = approveFlag;
    }

    public Integer getApvUserId() {
        return apvUserId;
    }

    public void setApvUserId(Integer apvUserId) {
        this.apvUserId = apvUserId;
    }
}

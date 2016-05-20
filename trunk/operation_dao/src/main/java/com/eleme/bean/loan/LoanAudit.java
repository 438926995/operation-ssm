package com.eleme.bean.loan;

import java.util.Date;

/**
 * Created by huwenwen on 16/5/14.
 */
public class LoanAudit {
    private String appStatus;
    private Date submitTime;
    private int sumCount;
    private int passCount;
    private int disPassCount;
    private int auditCount;

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public int getSumCount() {
        return sumCount;
    }

    public void setSumCount(int sumCount) {
        this.sumCount = sumCount;
    }

    public int getPassCount() {
        return passCount;
    }

    public void setPassCount(int passCount) {
        this.passCount = passCount;
    }

    public int getDisPassCount() {
        return disPassCount;
    }

    public void setDisPassCount(int disPassCount) {
        this.disPassCount = disPassCount;
    }

    public int getAuditCount() {
        return auditCount;
    }

    public void setAuditCount(int auditCount) {
        this.auditCount = auditCount;
    }
}

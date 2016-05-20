package com.eleme.bean.loan;

import com.eleme.util.pager.BaseQueryBean;

import java.util.Date;

/**
 * Created by huwenwen on 16/5/14.
 */
public class LoanQueryBean extends BaseQueryBean {
    private Date submitTimeFrom;
    private Date submitTimeEnd;
    private Integer fpId;
    private String appStatus;
    private boolean queryAll;

    public boolean isQueryAll() {
        return queryAll;
    }

    public void setQueryAll(boolean queryAll) {
        this.queryAll = queryAll;
    }

    public Date getSubmitTimeFrom() {
        return submitTimeFrom;
    }

    public void setSubmitTimeFrom(Date submitTimeFrom) {
        this.submitTimeFrom = submitTimeFrom;
    }

    public Date getSubmitTimeEnd() {
        return submitTimeEnd;
    }

    public void setSubmitTimeEnd(Date submitTimeEnd) {
        this.submitTimeEnd = submitTimeEnd;
    }

    public Integer getFpId() {
        return fpId;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }
}

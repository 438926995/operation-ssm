package com.eleme.bean.product;

import java.io.Serializable;

import javax.validation.GroupSequence;

import org.hibernate.validator.constraints.NotEmpty;

import com.eleme.validatorgroup.First;
import com.eleme.validatorgroup.Four;
import com.eleme.validatorgroup.Second;
import com.eleme.validatorgroup.Third;

/**
 * 融资产品审核界面的封装bean.
 * 
 * @author yonglin.zhu
 */
@GroupSequence({First.class, Second.class, Third.class, Four.class, ProductApproveBean.class})
public class ProductApproveBean implements Serializable {

  private static final long serialVersionUID = -7465664170335798469L;

  // 产品ID
  private Integer fpId;
  // 审批状态
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String appStatus;
  // 审批意见
  private String opinions;

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

  public String getOpinions() {
    return opinions;
  }

  public void setOpinions(String opinions) {
    this.opinions = opinions;
  }
}

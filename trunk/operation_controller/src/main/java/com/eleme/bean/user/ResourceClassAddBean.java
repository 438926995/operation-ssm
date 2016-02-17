package com.eleme.bean.user;

import java.io.Serializable;

import javax.validation.GroupSequence;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.eleme.validatorgroup.First;
import com.eleme.validatorgroup.Four;
import com.eleme.validatorgroup.Second;
import com.eleme.validatorgroup.Third;

/**
 * 资源添加界面的封装bean.
 * 
 * @author zhangqiongbiao
 */
@GroupSequence({First.class, Second.class, Third.class, Four.class, ResourceClassAddBean.class})
public class ResourceClassAddBean implements Serializable {
  private static final long serialVersionUID = -6789462559529842497L;

  // 资源分组ID
  private Integer classId;

  // 资源分组名称
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 50, message = "{field.range}", groups = {Second.class})
  private String className;


  public Integer getClassId() {
    return classId;
  }

  public void setClassId(Integer classId) {
    this.classId = classId;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }
}

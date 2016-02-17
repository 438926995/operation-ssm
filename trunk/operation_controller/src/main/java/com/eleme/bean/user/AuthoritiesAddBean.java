package com.eleme.bean.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
@GroupSequence({First.class, Second.class, Third.class, Four.class, AuthoritiesAddBean.class})
public class AuthoritiesAddBean implements Serializable {
  private static final long serialVersionUID = -6167250879515978411L;

  // 主键
  private Integer authoritiesId;

  // 权限名称
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 50, message = "{field.range}", groups = {Second.class})
  private String authName;

  // 权限描述
  @NotEmpty(message = "{field.required}", groups = {First.class})
  @Length(min = 2, max = 50, message = "{field.range}", groups = {Second.class})
  private String authDesc;

  // 权限是否可用
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String isEnabled;

  // 权限是否默认
  @NotEmpty(message = "{field.required}", groups = {First.class})
  private String isDefault;

  // 所属权限模块
  @NotNull(message = "{field.required}", groups = {First.class})
  private Integer authModuleId;

  // 对应资源ID
  private Integer[] resourcesIds;
  private String resourcesIdsStr;

  public Integer getAuthoritiesId() {
    return authoritiesId;
  }

  public void setAuthoritiesId(Integer authoritiesId) {
    this.authoritiesId = authoritiesId;
  }

  public String getAuthName() {
    return authName;
  }

  public void setAuthName(String authName) {
    this.authName = authName;
  }

  public String getAuthDesc() {
    return authDesc;
  }

  public void setAuthDesc(String authDesc) {
    this.authDesc = authDesc;
  }

  public String getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(String isEnabled) {
    this.isEnabled = isEnabled;
  }

  public String getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(String isDefault) {
    this.isDefault = isDefault;
  }

  public Integer getAuthModuleId() {
    return authModuleId;
  }

  public void setAuthModuleId(Integer authModuleId) {
    this.authModuleId = authModuleId;
  }

  public Integer[] getResourcesIds() {
    return resourcesIds;
  }

  public void setResourcesIds(Integer[] resourcesIds) {
    this.resourcesIds = resourcesIds;
  }

  public String getResourcesIdsStr() {
    return resourcesIdsStr;
  }

  public void setResourcesIdsStr(String resourcesIdsStr) {
    this.resourcesIdsStr = resourcesIdsStr;
  }

  public void convertResourcesIdsStr2ResourcesIds() {
    if (StringUtils.isNotEmpty(resourcesIdsStr)) {
      String[] array = StringUtils.split(resourcesIdsStr, ",");
      List<Integer> tmp = new ArrayList<Integer>();
      for (String str : array) {
        if (NumberUtils.isDigits(str)) {
          tmp.add(Integer.valueOf(str));
        }
      }
      resourcesIds = tmp.toArray(ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY);
    }
  }
}

package com.eleme.domain.mart.export;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 导出excel，设置导出字段
 *
 * @author sunwei
 */
public class ExportExlColumnBean implements Serializable {

  /**
   * 导出字段id
   */
  private Integer id;
  /**
   * 导出字段名称
   */
  private String name;
  /**
   * 导出字段描述
   */
  private String text;

  /**
   * 是否是必须的
   */
  @JsonProperty("is_force")
  private boolean isForce = false;

  /**
   * 是否默认的
   */
  @JsonProperty("is_default")
  private boolean isDefault = true;

  public ExportExlColumnBean() {
  }

  public ExportExlColumnBean(int id, String name, String text) {
    this.id = id;
    this.name = name;
    this.text = text;
  }

  public ExportExlColumnBean(Integer id, String name, String text, boolean isForce) {
    this.id = id;
    this.name = name;
    this.text = text;
    this.isForce = isForce;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isForce() {
    return isForce;
  }

  public void setForce(boolean force) {
    isForce = force;
  }

  public boolean isDefault() {
    return isDefault;
  }

  public void setDefault(boolean aDefault) {
    isDefault = aDefault;
  }
}

package com.wen.bean;

/**
 * 异常消息封装类.
 * 
 */
public class ErrorMessage {

  /**
   * 错误字段
   */
  private String field;
  /**
   * 错误消息
   */
  private String errors;

  public ErrorMessage() {}

  public ErrorMessage(String field, String errors) {
    this.field = field;
    this.errors = errors;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getErrors() {
    return errors;
  }

  public void setErrors(String errors) {
    this.errors = errors;
  }

}

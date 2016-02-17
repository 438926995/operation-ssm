package com.eleme.constants;

/**
 * 邮件发送状态
 * 
 * @author sunwei
 * @since 2015年12月25日
 */
public enum MailSendStatus {
  UNSEND(0, "未发送"), SENDING(1, "正在发送"), SEND_SUCCESS(2, "发送成功"), SEND_FAILD(3, "发送失败");

  private int code;
  private String text;

  MailSendStatus(int code, String text) {
    this.code = code;
    this.text = text;
  }

  public int getCode() {
    return code;
  }

  public String getText() {
    return text;
  }



}

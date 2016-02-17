package com.eleme.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义图形验证码异常。
 * 
 * @author penglau
 *
 */
public class BadCaptchaException extends AuthenticationException {

  /**
   * serial VersionUID.
   */
  private static final long serialVersionUID = -827483342868308271L;

  public BadCaptchaException(String msg) {
    super(msg);
  }

  public BadCaptchaException(String msg, Throwable t) {
    super(msg, t);
  }

}

package com.eleme.soa.ers;

import me.ele.contract.exception.thrift.TUserException;

@SuppressWarnings("serial")
public class ERSUserException extends TUserException {
  public ERSUserException(String code, String message) {
    super(code, message);
  }

  public ERSUserException(String message) {
    super(message);
  }
}

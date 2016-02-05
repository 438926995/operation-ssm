package com.eleme.soa.eus;

import me.ele.contract.exception.thrift.TUserException;

@SuppressWarnings("serial")
public class EUSUserException extends TUserException {
  public EUSUserException(String code, String message) {
    super(code, message);
  }

  public EUSUserException(String message) {
    super(message);
  }
}

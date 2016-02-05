package com.eleme.soa.ers;

import me.ele.contract.exception.thrift.TUnknownException;

@SuppressWarnings("serial")
public class ERSUnknownException extends TUnknownException {
  public ERSUnknownException(String code, String message) {
    super(code, message);
  }

  public ERSUnknownException(String message) {
    super(message);
  }
}

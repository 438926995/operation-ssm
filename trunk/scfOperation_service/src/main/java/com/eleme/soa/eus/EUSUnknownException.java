package com.eleme.soa.eus;

import me.ele.contract.exception.thrift.TUnknownException;

@SuppressWarnings("serial")
public class EUSUnknownException extends TUnknownException {
  public EUSUnknownException(String code, String message) {
    super(code, message);
  }

  public EUSUnknownException(String message) {
    super(message);
  }
}

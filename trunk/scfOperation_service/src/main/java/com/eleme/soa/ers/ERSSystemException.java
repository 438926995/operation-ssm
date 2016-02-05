package com.eleme.soa.ers;

import me.ele.contract.exception.thrift.TSystemException;

@SuppressWarnings("serial")
public class ERSSystemException extends TSystemException {
  public ERSSystemException(String code, String message) {
    super(code, message);
  }

  public ERSSystemException(String message) {
    super(message);
  }
}

package com.eleme.soa.eus;

import me.ele.contract.exception.thrift.TSystemException;

@SuppressWarnings("serial")
public class EUSSystemException extends TSystemException {
  public EUSSystemException(String code, String message) {
    super(code, message);
  }

  public EUSSystemException(String message) {
    super(message);
  }
}

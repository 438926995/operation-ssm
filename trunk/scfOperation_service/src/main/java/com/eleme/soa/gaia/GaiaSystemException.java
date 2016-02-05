package com.eleme.soa.gaia;

import me.ele.contract.exception.thrift.TSystemException;

@SuppressWarnings("serial")
public class GaiaSystemException extends TSystemException {
  public GaiaSystemException(String code, String message) {
    super(code, message);
  }

  public GaiaSystemException(String message) {
    super(message);
  }
}

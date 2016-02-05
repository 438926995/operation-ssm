package com.eleme.soa.gaia;

import me.ele.contract.exception.thrift.TUnknownException;

@SuppressWarnings("serial")
public class GaiaUnknownException extends TUnknownException {
  public GaiaUnknownException(String code, String message) {
    super(code, message);
  }

  public GaiaUnknownException(String message) {
    super(message);
  }
}

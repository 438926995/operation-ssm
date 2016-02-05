package com.eleme.soa.gaia;

import me.ele.contract.exception.thrift.TUserException;

@SuppressWarnings("serial")
public class GaiaUserException extends TUserException {
  public GaiaUserException(String code, String message) {
    super(code, message);
  }

  public GaiaUserException(String message) {
    super(message);
  }
}

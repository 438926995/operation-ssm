package com.eleme.soa.fuss;

import me.ele.contract.exception.thrift.TUserException;

@SuppressWarnings("serial")
public class FussException extends TUserException {
  public FussException(String code, String message) {
    super(code, message);
  }

  public FussException(String message) {
    super(message);
  }
}

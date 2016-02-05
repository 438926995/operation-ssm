package com.eleme.soa.gaia;

import me.ele.contract.annotation.Index;

public class TCityQuery {
  private @Index(1) Boolean is_valid;
  private @Index(2) Integer offset;
  private @Index(3) Integer limit;

  public Boolean getIs_valid() {
    return is_valid;
  }

  public void setIs_valid(Boolean is_valid) {
    this.is_valid = is_valid;
  }

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }
}

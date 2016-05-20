package com.eleme.util.pager;

import java.io.Serializable;

public interface IQueryBean extends Serializable {

  public Integer getCurrentPage();

  public Integer getPageSize();
}
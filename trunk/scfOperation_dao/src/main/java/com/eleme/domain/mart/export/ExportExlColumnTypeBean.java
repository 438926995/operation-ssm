package com.eleme.domain.mart.export;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExportExlColumnTypeBean implements Serializable {

  private static final long serialVersionUID = 3529396824228283263L;

  private Integer id;
  private String text;

  @JsonProperty("columns")
  private List<ExportExlColumnBean> exportExlColumnBeans = new ArrayList<>();

  public ExportExlColumnTypeBean() {}


  public ExportExlColumnTypeBean(Integer id, String text) {
    this.id = id;
    this.text = text;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public List<ExportExlColumnBean> getExportExlColumnBeans() {
    return exportExlColumnBeans;
  }

  public void setExportExlColumnBeans(List<ExportExlColumnBean> exportExlColumnBeans) {
    this.exportExlColumnBeans = exportExlColumnBeans;
  }

  public void addExportExlColumnBeans(ExportExlColumnBean exportExlColumnBean) {
    this.exportExlColumnBeans.add(exportExlColumnBean);
  }
}

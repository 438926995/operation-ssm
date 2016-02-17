package com.eleme.controller.bind;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

/**
 * 请求参数转义工具.
 * 
 * @author penglau
 *
 */
public class StringEscapeEditor extends PropertyEditorSupport {

  private boolean escapeHTML;
  private boolean escapeJavaScript;
  private boolean escapeSQL;

  public StringEscapeEditor() {
    super();
  }

  public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript, boolean escapeSQL) {
    super();
    this.escapeHTML = escapeHTML;
    this.escapeJavaScript = escapeJavaScript;
    this.escapeSQL = escapeSQL;
  }

  @Override
  public void setAsText(String text) {
    if (text == null) {
      setValue(null);
    } else {
      String value = text;
      StringEscapeUtils.escapeHtml4("");
      if (escapeHTML) {
        value = HtmlUtils.htmlEscape(value);
      }
      if (escapeJavaScript) {
        value = JavaScriptUtils.javaScriptEscape(value);
      }
      if (escapeSQL) {
        /**
         * escapeSql As SQL is not Lang's focus, it didn't make sense to maintain this method. value
         * = StringEscapeUtils.escapeSql(value);
         */
      }
      setValue(value);
    }
  }

  @Override
  public String getAsText() {
    Object value = getValue();
    return value != null ? value.toString() : "";
  }

}

package com.eleme.soa.fuss;

import java.nio.ByteBuffer;

import me.ele.contract.annotation.Index;

public class FussFile {
  private @Index(1) ByteBuffer content;
  private @Index(2) String extension;
  private @Index(3) String category;

  public ByteBuffer getContent() {
    return content;
  }

  public void setContent(ByteBuffer content) {
    this.content = content;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
}

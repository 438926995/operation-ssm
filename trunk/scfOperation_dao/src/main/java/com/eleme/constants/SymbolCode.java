package com.eleme.constants;

/**
 * 产品规则常量
 * @author a
 *
 */
public enum SymbolCode {
  SymbolCode_2001("大于", 2001),
  SymbolCode_2002("大于等于", 2002),
  SymbolCode_2003("小于", 2003),
  SymbolCode_2004("小于等于", 2004);

  // 成员变量
  private String name;
  private int index;

  // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
  private SymbolCode(String name, int index) {
    this.name = name;
    this.index = index;
  }

  // 普通方法
  public static String getName(int index) {
    for (SymbolCode c : SymbolCode.values()) {
      if (c.getIndex() == index) {
        return c.name;
      }
    }
    return null;
  }

  // get set 方法
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}

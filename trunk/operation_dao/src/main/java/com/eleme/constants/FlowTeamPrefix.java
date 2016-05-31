package com.eleme.constants;

/**
 * 查找审批小组的前缀标识
 * 
 * @author huwenwen
 *
 */
public enum FlowTeamPrefix {


  FO("金融机构", "FO"), FP("金融产品", "FP");

  // 成员变量
  private String name;
  private String index;

  // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
  private FlowTeamPrefix(String name, String index) {
    this.name = name;
    this.index = index;
  }

  // 普通方法
  public static String getName(String index) {
    for (FlowTeamPrefix c : FlowTeamPrefix.values()) {
      if (c.getIndex().equals(index)) {
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

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

}

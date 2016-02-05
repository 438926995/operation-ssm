package com.eleme.constants;

/**
 * 商户借贷审核状态常量
 * 
 * @author yonglin.zhu
 *
 */
public enum LoanStatus {

	Status_N("未提交", "N"), Status_P("审批中", "P"), Status_C("通过", "C"), Status_D("未通过", "D"), Status_B("不可见", "B");

	// 成员变量
	private String name;
	private String index;

	// 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
	private LoanStatus(String name, String index) {
		this.name = name;
		this.index = index;
	}

	// 普通方法
	public static String getName(String index) {
		for (LoanStatus c : LoanStatus.values()) {
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

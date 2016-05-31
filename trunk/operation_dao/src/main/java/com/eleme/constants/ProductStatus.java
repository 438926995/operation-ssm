package com.eleme.constants;

/**
 * 产品状态常量
 * 
 * @author huwenwen
 *
 */
public enum ProductStatus {
	Status_0("待发布", 0), Status_1("已发布", 1), Status_2("下架", 2), Status_3("删除", 3);

	// 成员变量
	private String name;
	private int index;

	// 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
	private ProductStatus(String name, int index) {
		this.name = name;
		this.index = index;
	}

	// 普通方法
	public static String getName(int index) {
		for (ProductStatus c : ProductStatus.values()) {
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

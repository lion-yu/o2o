package com.zmy.enums;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月5日上午9:34:17
*Class Description： 
*/
public enum ProductCategoryStateEnum {
	SUCCESS(0,"创建成功"),INNER_ERROR(-1001,"内部系统错误"),EMPTY_LIST(-1002,"添加数小于1");
	
	private int state;
	private String stateInfo;
	
	private ProductCategoryStateEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	
	public static ProductCategoryStateEnum stateOf(int index) {
		for(ProductCategoryStateEnum state : values()) {
			if(state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}

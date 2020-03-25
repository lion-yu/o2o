package com.zmy.enums;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月15日下午8:36:02
*Class Description： 
*/
public enum ShopAuthMapStateEnum {
	SUCCESS(1,"操作成功"),INNER_ERROR(-1001,"操作失败"),NULL_SHOPAUTH_ID(-1002,"shopAuthId为空"),NULL_SHOPAUTH_INFO(-1003,"传入了空的信息");
	
	private int state;
	private String stateInfo;
	private ShopAuthMapStateEnum(int state,String stateInfo) {
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
	
}

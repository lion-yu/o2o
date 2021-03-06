package com.zmy.enums;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月6日下午10:46:21
*Class Description： 
*/
public enum WechatAuthStateEnum {
	LOGINFAIL(-1,"openId输入有误"),SUCCESS(0,"操作成功"),NULL_AUTH_INFO(1,"无账号信息");
	
	private int state;
	private String stateInfo;
	
	private WechatAuthStateEnum(int state, String stateInfo) {
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

	
package com.zmy.enums;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月24日下午7:28:44
*Class Description： 
*/
public enum UserAwardMapStateEnum {
	SUCCESS(1000,"领取成功"),NULL_AWARD_INFO(-1000,"空奖品信息");
	
	private int state;
	private String stateInfo;
	
	private UserAwardMapStateEnum(int state, String stateInfo) {
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

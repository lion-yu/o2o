package com.zmy.enums;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月21日下午8:23:50
*Class Description： 
*/
public enum AwardStateEnum {
	SUCCESS(1001, "店铺奖品操作成功"), FAILED(-1002, "店铺奖品操作失败"),
	NULL_AWARD_INFO(1003, "奖品信息为空错误"),INNER_ERROR(1004,"内部错误");
	
	private int state;
	private String stateInfo;
	
	private AwardStateEnum(int state, String stateInfo) {
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

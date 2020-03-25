package com.zmy.enums;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月8日下午4:50:58
*Class Description： 
*/
public enum LocalAuthStateEnum {
	NULL_AUTH_INFO(1000,"非法操作"),SUCCESS(1001,"绑定本地账号成功"),ONLY_ONE_ACCOUNT(1002,"账号已经绑定过")
	,CHANGE_PSW_FAILED(1003,"更新密码失败");
	
	private int state;
	private String stateInfo;
	private LocalAuthStateEnum(int state, String stateInfo){
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

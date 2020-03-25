package com.zmy.dto;

import java.util.List;

import com.zmy.entity.LocalAuth;
import com.zmy.enums.LocalAuthStateEnum;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月8日下午4:47:45
*Class Description： 
*/
public class LocalAuthExecution {
	private int state;
	private String stateInfo;
	private int count;
	private LocalAuth localAuth;
	private List<LocalAuth> localAuthList;
	public LocalAuthExecution() {
	}
	
	public LocalAuthExecution(LocalAuthStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	public LocalAuthExecution(LocalAuthStateEnum stateEnum, LocalAuth localAuth) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.localAuth = localAuth;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public LocalAuth getLocalAuth() {
		return localAuth;
	}

	public void setLocalAuth(LocalAuth localAuth) {
		this.localAuth = localAuth;
	}

	public List<LocalAuth> getLocalAuthList() {
		return localAuthList;
	}

	public void setLocalAuthList(List<LocalAuth> localAuthList) {
		this.localAuthList = localAuthList;
	}
}

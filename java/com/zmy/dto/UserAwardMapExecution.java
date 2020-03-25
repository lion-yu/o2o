package com.zmy.dto;

import java.util.List;

import com.zmy.entity.UserAwardMap;
import com.zmy.enums.UserAwardMapStateEnum;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月24日下午6:17:39
*Class Description： 
*/
public class UserAwardMapExecution {
	private int state;
	private String stateInfo;
	private int count;
	
	public UserAwardMapExecution() {
		
	}
	public UserAwardMapExecution(UserAwardMapStateEnum stateEnum){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	private UserAwardMap userAwardMap;
	private List<UserAwardMap>  userAwardMapList;
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
	public UserAwardMap getUserAwardMap() {
		return userAwardMap;
	}
	public void setUserAwardMap(UserAwardMap userAwardMap) {
		this.userAwardMap = userAwardMap;
	}
	public List<UserAwardMap> getUserAwardMapList() {
		return userAwardMapList;
	}
	public void setUserAwardMapList(List<UserAwardMap> userAwardMapList) {
		this.userAwardMapList = userAwardMapList;
	}
	
}

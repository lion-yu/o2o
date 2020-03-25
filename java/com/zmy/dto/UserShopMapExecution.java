package com.zmy.dto;

import java.util.List;

import com.zmy.entity.UserShopMap;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月24日下午5:38:01
*Class Description： 
*/
public class UserShopMapExecution {
	private int state;
	private String stateInfo;
	private int count;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	private UserShopMap userShopMap;
	private List<UserShopMap>  userShopMapList;
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
	public UserShopMap getUserShopMap() {
		return userShopMap;
	}
	public void setUserShopMap(UserShopMap userShopMap) {
		this.userShopMap = userShopMap;
	}
	public List<UserShopMap> getUserShopMapList() {
		return userShopMapList;
	}
	public void setUserShopMapList(List<UserShopMap> userShopMapList) {
		this.userShopMapList = userShopMapList;
	}
	
}

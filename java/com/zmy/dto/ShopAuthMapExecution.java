package com.zmy.dto;

import java.util.List;

import com.zmy.entity.ShopAuthMap;
import com.zmy.enums.ShopAuthMapStateEnum;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年5月15日下午8:30:45 Class Description：
 */
public class ShopAuthMapExecution {
	private int state;
	private String stateInfo;
	// 授权数
	private Integer count;
	// 操作的shopAuthMap
	private ShopAuthMap shopAuthMap;
	// 授权列表
	private List<ShopAuthMap> shopAuthMapList;

	public ShopAuthMapExecution() {
	}
	public ShopAuthMapExecution(ShopAuthMapStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	//成功
	public ShopAuthMapExecution(ShopAuthMapStateEnum stateEnum, ShopAuthMap shopAuthMap) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopAuthMap = shopAuthMap;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public ShopAuthMap getShopAuthMap() {
		return shopAuthMap;
	}
	public void setShopAuthMap(ShopAuthMap shopAuthMap) {
		this.shopAuthMap = shopAuthMap;
	}
	public List<ShopAuthMap> getShopAuthMapList() {
		return shopAuthMapList;
	}
	public void setShopAuthMapList(List<ShopAuthMap> shopAuthMapList) {
		this.shopAuthMapList = shopAuthMapList;
	}
	
}

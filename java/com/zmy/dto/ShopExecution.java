package com.zmy.dto;

import java.util.List;

import com.zmy.entity.Shop;
import com.zmy.enums.ShopStateEnum;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月2日下午9:35:30
*Class Description： 店铺操作返回的信息描述
*/
public class ShopExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	
	//店铺数量
	private int count;
	
	//操作的shop(增删店铺的时候用到)
	private Shop shop;
	
	//shop列表(查询店铺列表的时候使用)
	private List<Shop> shopList;
	
	public ShopExecution() {
		
	}
	//店铺操作失败使用的构造器
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	//店铺操作成功使用构造器
	public ShopExecution(ShopStateEnum stateEnum,Shop shop) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
	}
	//店铺列表操作成功使用构造器
	public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shopList;
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
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<Shop> getShopList() {
		return shopList;
	}
	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	
}

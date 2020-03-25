package com.zmy.entity;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月13日下午10:22:59
*Class Description： 顾客店铺积分映射
*/

import java.util.Date;

public class UserShopMap {
	private Long userShopId;
	private Date createTime;
	//顾客在该店铺的积分
	private Integer point;
	//顾客信息实体类
	private PersonInfo user;
	private Shop shop;
	public Long getUserShopId() {
		return userShopId;
	}
	public void setUserShopId(Long userShopId) {
		this.userShopId = userShopId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public PersonInfo getUser() {
		return user;
	}
	public void setUser(PersonInfo user) {
		this.user = user;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
}

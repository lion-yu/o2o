package com.zmy.entity;

import java.util.Date;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月13日下午10:36:42
*Class Description： 店铺授权
*/
public class ShopAuthMap {
	private Long shopAuthId;
	//职称名
	private String title;
	//职称符号（可用于权限空值）
	private Integer titleFlag;
	//授权有效状态
	private Integer enableStatus;
	private Date createTime;
	private Date lastEditTime;
	//员工信息实体类
	private PersonInfo employee;
	public Long getShopAuthId() {
		return shopAuthId;
	}
	public void setShopAuthId(Long shopAuthId) {
		this.shopAuthId = shopAuthId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getTitleFlag() {
		return titleFlag;
	}
	public void setTitleFlag(Integer titleFlag) {
		this.titleFlag = titleFlag;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public PersonInfo getEmployee() {
		return employee;
	}
	public void setEmployee(PersonInfo employee) {
		this.employee = employee;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	//店铺信息实体类
	private Shop shop;
}

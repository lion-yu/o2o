package com.zmy.entity;

import java.util.Date;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月13日下午9:54:15
*Class Description： 顾客领取的奖品映射
*/
public class UserAwardMap {
	private Long userAwardId;
	private Date createTime;
	//是否兑换 0：为兑换，1.已兑换
	private Integer usedStatus;
	//领取奖品所消耗的积分
	private Integer point;
	//顾客信息实体类
	private PersonInfo user;
	//奖品信息实体类
	private Award award;
	//店铺信息实体类
	private Shop shop;
	//操作员信息实体类
	private PersonInfo operator;
	public Long getUserAwardId() {
		return userAwardId;
	}
	public void setUserAwardId(Long userAwardId) {
		this.userAwardId = userAwardId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getUsedStatus() {
		return usedStatus;
	}
	public void setUsedStatus(Integer usedStatus) {
		this.usedStatus = usedStatus;
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
	public Award getAward() {
		return award;
	}
	public void setAward(Award award) {
		this.award = award;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public PersonInfo getOperator() {
		return operator;
	}
	public void setOperator(PersonInfo operator) {
		this.operator = operator;
	}
	
}

package com.zmy.entity;

import java.util.Date;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月13日下午10:10:09
*Class Description： 顾客消费的商品映射
*/
public class UserProductMap {
	private Long userProductId;
	private Date createTime;
	//消费商品所获得的积分
	private Integer point;
	//顾客信息实体类
	private PersonInfo user;
	private Product product;
	private Shop shop;
	private PersonInfo operator;
	private Integer commentState; 
	public Integer getCommentState() {
		return commentState;
	}
	public void setCommentState(Integer commentState) {
		this.commentState = commentState;
	}
	public Long getUserProductId() {
		return userProductId;
	}
	public void setUserProductId(Long userProductId) {
		this.userProductId = userProductId;
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
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

package com.zmy.dto;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年6月9日上午1:53:41
*Class Description： 购物车
*/
public class Car {
	private Long productId;
	private Integer count;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}

package com.zmy.enums;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月6日下午2:01:17
*Class Description： 
*/
public enum ProductStateEnum {
	EMPTY(0,"商品为空"),SUCCESS(1, "操作成功");
	
	private int state;
	private String stateInfo;
	
	private ProductStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 依据传入的state返回相应的enum值
	 * @return
	 */
	public static ProductStateEnum stateOf(int state) {
		for(ProductStateEnum stateEnum : values()) {
			if(stateEnum.getState()==state) {
				return stateEnum;
			}
		}
		return null;
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
	
	
}

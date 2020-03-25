package com.zmy.dto;

import java.util.HashSet;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月24日上午10:18:31
*Class Description： echart里的xAxis项
*/
public class EchartXAxis {
	private String type = "category";
	//为了去重复
	private HashSet<String> data;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public HashSet<String> getData() {
		return data;
	}
	public void setData(HashSet<String> data) {
		this.data = data;
	}
}

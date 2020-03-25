package com.zmy.dto;

import java.util.List;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月24日上午10:21:43
*Class Description： echart里的series项
*/
public class EchartSeries {
	private String name;
	//柱状图
	private String type = "bar";
	private List<Integer> data;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Integer> getData() {
		return data;
	}
	public void setData(List<Integer> data) {
		this.data = data;
	}
	
	
}

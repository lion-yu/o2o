package com.zmy.dto;

import java.util.List;

import com.zmy.entity.Award;
import com.zmy.enums.AwardStateEnum;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月21日下午8:19:49
*Class Description： 
*/
public class AwardExecution {
	private int state;
	private String stateInfo;
	
	private int count;
	private Award award;
	private List<Award> awardList;
	
	public AwardExecution() {
		
	}
	public List<Award> getAwardList() {
		return awardList;
	}
	public void setAwardList(List<Award> awardList) {
		this.awardList = awardList;
	}
	//奖品表操作失败调用的构造方法
	public AwardExecution(AwardStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	//奖品表操作成功调用的构造方法
	public AwardExecution(AwardStateEnum stateEnum, Award award){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.award = award;
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
	public Award getAward() {
		return award;
	}
	public void setAward(Award award) {
		this.award = award;
	}
	
	
}

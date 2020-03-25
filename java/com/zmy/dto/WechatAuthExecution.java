package com.zmy.dto;

import java.util.List;

import com.zmy.entity.WeChatAuth;
import com.zmy.enums.WechatAuthStateEnum;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月6日下午10:41:27
*Class Description： 
*/
public class WechatAuthExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	
	private int count;
	private WeChatAuth wechatAuth;
	private List<WeChatAuth> wechatAuthList;
	
	public WechatAuthExecution() {
	}
	//平台注册微信账号失败的构造方法
	public WechatAuthExecution(WechatAuthStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	//平台注册微信账号成功的构造方法
	public WechatAuthExecution(WechatAuthStateEnum stateEnum, WeChatAuth wechatAuth) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.wechatAuth = wechatAuth;
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
	public WeChatAuth getWechatAuth() {
		return wechatAuth;
	}
	public void setWechatAuth(WeChatAuth wechatAuth) {
		this.wechatAuth = wechatAuth;
	}
	public List<WeChatAuth> getWechatAuthList() {
		return wechatAuthList;
	}
	public void setWechatAuthList(List<WeChatAuth> wechatAuthList) {
		this.wechatAuthList = wechatAuthList;
	}
	
}

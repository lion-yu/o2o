package com.zmy.dto;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月21日下午11:56:32
*Class Description： 用来接收accesstoken以及openid等信息
*/

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAccessToken {
	//获取到的凭证
	@JsonProperty(value="access_token")
	private String accessToken;
	//凭证有效时间  单位秒
	@JsonProperty(value="expires_in")
	private String expiresIn;
	//表示更新令牌，用来获取下一次的访问令牌，这里没多大用处
	@JsonProperty(value="refresh_token")
	private String refresh_token;
	//该用户在此公众号下的身份标识，对于此微信号具有唯一性
	@JsonProperty(value="openid")
	private String openId;
	//标识权限范围，这里可以省略
	@JsonProperty(value="scope")
	private String scope;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	@Override
	public String toString() {
		
		return "accessToken:" + this.getAccessToken() + ",openId:"
				+ this.getOpenId();
	}
}

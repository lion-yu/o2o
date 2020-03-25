package com.zmy.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年4月22日上午12:07:28 
 * Class Description：微信用户实体类，用来接收openid等用户信息
 */
public class WechatUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4724140789485694827L;
	// openId 标识该公众号下面的该用户的唯一Id
	@JsonProperty("openid")
	private String openId;
	// 用户昵称
	@JsonProperty("nickname")
	private String nickName;
	// 性别
	@JsonProperty("sex")
	private String sex;
	// 省份
	@JsonProperty("province")
	private String province;
	// 城市
	@JsonProperty("city")
	private String city;
	// 国家
	@JsonProperty("country")
	private String country;
	// 头像图片地址
	@JsonProperty("headimgurl")
	private String headImgUrl;
	// 语言
	@JsonProperty("language")
	private String language;
	// 用户权限，这里没什么作用
	@JsonProperty("privilege")
	private String[] privilege;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String[] getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String[] privilege) {
		this.privilege = privilege;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {

		return "openId" + this.getOpenId() + ",nikename:" + this.getNickName();
	}

}

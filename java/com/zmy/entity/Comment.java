package com.zmy.entity;

import java.util.Date;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月25日下午7:43:52
*Class Description： 评价实体类
*/
public class Comment {
	private Long commentId;
	private String comments;
	private Date createTime;
	private Date lastEditTime;
	private UserProductMap userProductMap;
	public  Long  getCommentId() {
		return commentId;
	}
	public void setCommentId( Long commentId) {
		this.commentId = commentId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public UserProductMap getUserProductMap() {
		return userProductMap;
	}
	public void setUserProductMap(UserProductMap userProductMap) {
		this.userProductMap = userProductMap;
	}
}

package com.zmy.service;

import java.util.List;

import com.zmy.entity.Comment;
import com.zmy.entity.UserProductMap;
import com.zmy.exceptions.CommentOperationException;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月26日下午1:26:58
*Class Description： 评价
*/
public interface ICommentService {
	/**
	 * 添加评价信息
	 * @param orderId
	 * @return
	 */
	int addCommentByOrderId(Comment comment) throws CommentOperationException;
	List<Comment> getCommentList(Comment comment);
	/**
	 * 获取订单号对应的评价信息
	 * @param orderId
	 * @return
	 */
	Comment getCommentByOrderId(Long orderId);
	/**
	 * 更新订单评价信息
	 * @param comment
	 * @return
	 */
	int modifyCommentByOrderId(Comment comment);
	/**
	 * 通过商店id商品id获取该商品的评价列表
	 * @param userProductMap
	 * @return
	 */
	List<Comment> getCommentByShopAndProduct(UserProductMap userProductMap);
}

package com.zmy.dao;

import java.util.List;

import com.zmy.entity.Comment;
import com.zmy.entity.UserProductMap;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月26日上午8:55:53
*Class Description： 评论dao接口
*/
public interface ICommentDao {
	/**
	 * 添加商品评论，返回评论主键值
	 * @param comment
	 * @return
	 */
	Integer insertComment(Comment comment);
	/**
	 * 更新评论，返回更新成功的条数
	 * @param orderId
	 * @return
	 */
	int updateCommentByOrderId(Comment comment);
	/**
	 * 通过订单号，查询订单的评价信息
	 * @param orderId
	 * @return
	 */
	Comment queryCommentByOrderId(long orderId);
	/**
	 * 获取某个店铺的商品评论列表
	 * @param userProductMap
	 * @return
	 */
	List<Comment> queryCommentByShopAndProduct(UserProductMap userProductMap);
	/**
	 * 获取某个店铺的商品评论列表
	 * @param userProductMap
	 * @return
	 */
	List<Comment> queryCommentListByShopId(UserProductMap userProductMap);
}

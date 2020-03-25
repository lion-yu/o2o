package com.zmy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zmy.dao.ICommentDao;
import com.zmy.dao.IUserProductMapDao;
import com.zmy.entity.Comment;
import com.zmy.entity.UserProductMap;
import com.zmy.exceptions.CommentOperationException;
import com.zmy.service.ICommentService;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年5月26日下午1:32:34 Class Description：
 */
@Service
public class CommentServiceImpl implements ICommentService {
	@Autowired
	private ICommentDao commentDao;
	@Autowired
	private IUserProductMapDao userProductDao;

	/**
	 * 添加评价记录，成功后修改订单表的评价状态 任意条记录更新失败，回滚所有成功的操作
	 */
	@Override
	@Transactional
	public int addCommentByOrderId(Comment comment) throws CommentOperationException {
		// 设置评价穿件时间
		comment.setCreateTime(new Date());
		// 插入是否成功的标志
		int effectNum = 0;
		try {
			effectNum = commentDao.insertComment(comment);
			if(effectNum > 0) {
				UserProductMap userProductMap = comment.getUserProductMap();
				userProductMap.setCommentState(1);
				effectNum = userProductDao.updateUserProductMap(userProductMap);
				if(effectNum >0) {
					return effectNum;
				}
			}
		} catch (RuntimeException e) {
			throw new CommentOperationException("更新评价失败:" + e.getMessage());
		}
		return effectNum;
	}

	@Override
	public List<Comment> getCommentList(Comment coment) {

		return null;
	}

	@Override
	public Comment getCommentByOrderId(Long orderId) {
		return commentDao.queryCommentByOrderId(orderId);
	}

	@Override
	public int modifyCommentByOrderId(Comment comment) {
		//创建修改时间
		comment.setLastEditTime(new Date());
		return commentDao.updateCommentByOrderId(comment);
	}

	@Override
	public List<Comment> getCommentByShopAndProduct(UserProductMap userProductMap) {
		return  commentDao.queryCommentByShopAndProduct(userProductMap);
	}

}

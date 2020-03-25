package com.zmy.service;

import java.util.List;

import com.zmy.dto.HeadLineExecution;
import com.zmy.dto.ImageHolder;
import com.zmy.entity.HeadLine;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月14日下午1:13:03
*Class Description： 
*/
public interface IHeadLineService {
	public static final String HEADLINELIST = "headlinelist";
	/**
	 * 获取头条所有信息
	 * @return
	 */
	List<HeadLine> getHeadLineList();
	/**
	 * 添加头条信息，并存储头条图片
	 * 
	 * @param headLine
	 * @param thumbnail
	 * @return
	 */
	HeadLineExecution addHeadLine(HeadLine headLine, ImageHolder thumbnail);

	/**
	 * 修改头条信息
	 * 
	 * @param headLine
	 * @param thumbnail
	 * @return
	 */
	HeadLineExecution modifyHeadLine(HeadLine headLine, ImageHolder thumbnail);

	/**
	 * 删除单条头条
	 * 
	 * @param headLineId
	 * @return
	 */
	HeadLineExecution removeHeadLine(long headLineId);

	/**
	 * 批量删除头条
	 * 
	 * @param headLineIdList
	 * @return
	 */
	HeadLineExecution removeHeadLineList(List<Long> headLineIdList);
}

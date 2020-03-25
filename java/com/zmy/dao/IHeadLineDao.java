package com.zmy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zmy.entity.HeadLine;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月14日上午11:01:00
*Class Description： 
*/
public interface IHeadLineDao {
	/**
	 * 查询应用程序所有头条信息
	 * @return
	 */
	List<HeadLine> queryHeadLineList();
	/**
	 * 根据传入的查询条件(头条名查询头条)
	 * 
	 * @return
	 */
	List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);

	/**
	 * 根据头条id返回唯一的头条信息
	 * 
	 * @param lineId
	 * @return
	 */
	HeadLine queryHeadLineById(long lineId);

	/**
	 * 根据传入的Id列表查询头条信息(供超级管理员选定删除头条的时候用，主要是处理图片)
	 * 
	 * @param lineIdList
	 * @return
	 */
	List<HeadLine> queryHeadLineByIds(List<Long> lineIdList);

	/**
	 * 插入头条
	 * 
	 * @param headLine
	 * @return
	 */
	int insertHeadLine(HeadLine headLine);

	/**
	 * 更新头条信息
	 * 
	 * @param headLine
	 * @return
	 */
	int updateHeadLine(HeadLine headLine);

	/**
	 * 删除头条
	 * 
	 * @param headLineId
	 * @return
	 */
	int deleteHeadLine(long headLineId);

	/**
	 * 
	 * @param lineIdList
	 * @return
	 */
	int batchDeleteHeadLine(List<Long> lineIdList);
}

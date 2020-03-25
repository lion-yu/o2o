package com.zmy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zmy.entity.Award;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月15日上午9:13:40
*Class Description： 
*/
public interface IAwardDao {
	/**
	 * 根据查询条件分页显示商品信息列表
	 * @param awardCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Award> queryAwardList(@Param("awardCondition") Award awardCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	/**
	 * 与上面查询条件相同，返回查询奖品数
	 * @param awardCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	int queryAwardCount(@Param("awardCondition") Award awardCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	/**
	 * 通过awardId查询商品信息
	 * @param awardId
	 * @return
	 */
	Award queryAwardByAwardId(long awardId);
	/**
	 * 添加商品信息
	 * @param award
	 * @return
	 */
	int insertAward(Award award);
	/**
	 * 更新商品信息
	 * @param award
	 * @return
	 */
	int updateAward(Award award);
	/**
	 * 删除商品信息
	 * @param awardId
	 * @param shopId
	 * @return
	 */
	int deleteAward(@Param("awardId")long awardId, @Param("shopId")long shopId);
}

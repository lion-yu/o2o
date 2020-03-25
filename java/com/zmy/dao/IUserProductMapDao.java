package com.zmy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zmy.entity.UserProductMap;
import com.zmy.exceptions.CommentOperationException;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月15日下午2:04:19
*Class Description： 
*/
public interface IUserProductMapDao {
	/**
	 * 根据查询条件分页返回用户购买商品的记录列表
	 * @param userProductMap
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<UserProductMap> queryUserProductMapList(@Param("userProductCondition")UserProductMap userProductMap,
			@Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	/**
	 * 根据用户id返回订单列表
	 * @param userProductMap
	 * @return
	 */
	List<UserProductMap> queryUserProductMapListByUser(@Param("userProductMap")UserProductMap userProductMap, @Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	/**
	 * 同等查询条件下返回用户订单条数
	 * @param userProductMap
	 * @return
	 */
	int queryUserProductMapListByUserCount(UserProductMap userProductMap);
	/**
	 * 返回相同查询条件下的用户购买商品的记录总数
	 * @param userProductMapCondition
	 * @return
	 */
	int queryUserProductMapCount(UserProductMap userProductCondition);
	/**
	 * 添加一条用户购买商品的记录
	 * @param userProductMap
	 * @return
	 */
	int insertUserProductMap(UserProductMap userProductMap);
	
	/**
	 * 更新订单记录
	 * @param userProductMap
	 * @return
	 */
	int updateUserProductMap(UserProductMap userProductMap)  throws CommentOperationException;
	/**
	 * 查询某个商品的销量情况
	 * @param productId
	 * @return
	 */
	Long queryUserProductMapByProductId(Long productId);
}

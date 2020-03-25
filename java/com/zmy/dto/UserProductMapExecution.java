package com.zmy.dto;

import java.util.List;

import com.zmy.entity.UserProductMap;
import com.zmy.enums.UserProductMapStateEnum;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月21日上午1:28:39
*Class Description： 
*/
public class UserProductMapExecution {
	//结果状态
		private int state;
		//状态标识
		private String stateInfo;
		
		//数量
		private int count;
		
		//操作的shop(增删店铺的时候用到)
		private UserProductMap userProductMap;
		
		//shop列表(查询店铺列表的时候使用)
		private List<UserProductMap> userProductMapList;
		
		public UserProductMapExecution() {
			
		}
		//店铺操作失败使用的构造器
		public UserProductMapExecution(UserProductMapStateEnum stateEnum) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
		}
		//店铺操作成功使用构造器
		public UserProductMapExecution(UserProductMapStateEnum stateEnum,UserProductMap userProductMap) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.userProductMap = userProductMap;
		}
		//店铺列表操作成功使用构造器
		public UserProductMapExecution(UserProductMapStateEnum stateEnum,List<UserProductMap> userProductMapList) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.userProductMapList = userProductMapList;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getStateInfo() {
			return stateInfo;
		}
		public void setStateInfo(String stateInfo) {
			this.stateInfo = stateInfo;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public UserProductMap getUserProductMap() {
			return userProductMap;
		}
		public void setUserProductMap(UserProductMap userProduct) {
			this.userProductMap = userProduct;
		}
		public List<UserProductMap> getUserProductMapList() {
			return userProductMapList;
		}
		public void setUserProductMapList(List<UserProductMap> userProductMapList) {
			this.userProductMapList = userProductMapList;
		}
		
}

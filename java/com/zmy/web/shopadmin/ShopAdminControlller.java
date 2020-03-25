package com.zmy.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月6日下午6:08:04
*Class Description： 需要访问web-inf下的内容，需定义路由由程序内部转发
*/
@Controller
@RequestMapping(value="/shopadmin",method= {RequestMethod.GET})
/**
 * 主要用来解析路由并转发到相应的html中
 * @author 86227
 *
 */
public class ShopAdminControlller {
	@RequestMapping(value="/shopoperation")
	private String shopOperation() {
		//转发至店铺注册/编辑页面
		return "shop/shopoperation";
	}
	@RequestMapping(value="/shoplist")
	private String shopList() {
		return "shop/shoplist";
	}
	@RequestMapping(value="/shopmanagement")
	private String shopManagement() {
		return "shop/shopmanagement";
	}
	@RequestMapping(value="/productcategorymanagement")
	private String shopCategoryManagement() {
		return "shop/productcategorymanagement";
	}
	@RequestMapping(value="/productoperation")
	private String productOperation() {
		return "shop/productoperation";
	}
	@RequestMapping(value="/productmanagement")
	private String productManagement() {
		return "shop/productmanagement";
	}
	
	@RequestMapping(value="/shopauthmanagement")
	private String shopAuthManagement() {
		//转发至店铺授权页面
		return "shop/shopauthmanagement";
	}
	
	@RequestMapping(value="/shopauthedit")
	private String shopAuthEdit() {
		//转发至授权信息修改页面
		return "shop/shopauthedit";
	}
	@RequestMapping(value="/success")
	private String permisionQRCode() {
		//扫描二维码跳转页面
		return "shop/success";
	}
	@RequestMapping(value="/awardoperation")
	private String addAward() {
		//奖品编辑路由页面
		return "shop/awardoperation";
	}
	@RequestMapping(value="/awardmanagement")
	private String awardManagement() {
		
		return "shop/awardmanagement";
	}
	//转发至店铺的消费记录的页面
	@RequestMapping(value="/productbuycheck", method=RequestMethod.GET)
	private String productBuyCheck() {
		return "shop/productbuycheck";
	}
	//店铺用户积分统计路由
	@RequestMapping(value="/usershopcheck", method=RequestMethod.GET)
	private String userShopCheck() {
		return "shop/usershopcheck";
	}
	//店铺用户积分兑换路由
	@RequestMapping(value="/awarddelivercheck", method=RequestMethod.GET)
	private String awardDeliverCheck() {
		return "shop/awarddelivercheck";
	}
	//店铺订单页面路由
	@RequestMapping(value="/orderlist", method=RequestMethod.GET)
	private String getOrderList() {
		return "shop/orderlist";
	}
}

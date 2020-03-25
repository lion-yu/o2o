package com.zmy.web.shopadmin;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月21日上午1:52:54
*Class Description： 
*/

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zmy.dto.EchartSeries;
import com.zmy.dto.EchartXAxis;
import com.zmy.dto.UserProductMapExecution;
import com.zmy.entity.Product;
import com.zmy.entity.ProductSellDaily;
import com.zmy.entity.Shop;
import com.zmy.entity.UserProductMap;
import com.zmy.service.IProductSellDailyService;
import com.zmy.service.IUserProductMapService;
import com.zmy.util.HttpServletRequestUtil;
@Controller
@RequestMapping("/shopadmin")
public class UserProductManagementController {
	@Autowired
	private IProductSellDailyService productSellDailyService;
	@Autowired
	private IUserProductMapService userProductMapService;
	//列出每个店铺商品的销售情况
	@RequestMapping(value="/listuserproductmapsbyshop", method= RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserProductMapsByShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
			//添加查询条件
			UserProductMap userProductMapCondition = new UserProductMap();
			userProductMapCondition.setShop(currentShop);
			String productName = HttpServletRequestUtil.getString(request, "productName");
			if(productName != null) {
				//按前端向按照商铺名模糊查询，则传入productName
				Product product = new Product();
				product.setProductName(productName);
				userProductMapCondition.setProduct(product);
			}
			//根据传入的查询条件获取店铺的销售情况
			UserProductMapExecution ue = userProductMapService.listUserProductMap(userProductMapCondition, pageIndex, pageSize);
			modelMap.put("success", true);
			modelMap.put("userProductMapList", ue.getUserProductMapList());
			modelMap.put("count", ue.getCount());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/listprodctselldailyinfobyshop", method= RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductSellDailyByShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		if ((currentShop != null) && (currentShop.getShopId() != null)) {
			//添加查询条件
			ProductSellDaily productSellDailyCondition= new ProductSellDaily();
			productSellDailyCondition.setShop(currentShop);
			Calendar calendar = Calendar.getInstance();
			//获取昨天的日期
			calendar.add(Calendar.DATE, -1);
			Date endTime = calendar.getTime();
			//获取七天的日期
			calendar.add(Calendar.DATE, -9);
			Date beginTime = calendar.getTime();
			//根据传入的查询条件获取该店铺的商品销售情况
			List<ProductSellDaily> productSellDailyList = productSellDailyService.listProductSellDaily(productSellDailyCondition, beginTime, endTime);
			//指定日期格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
			//商品列表
			HashSet<String> legendData = new HashSet<>();
			//x轴数据
			HashSet<String> xData = new HashSet<>();
			//定义series
			List<EchartSeries> series = new ArrayList<>();
			//日销量列表
			List<Integer> totalList = new ArrayList<>();
			//当前商品名，默认为空
			String currentProductName = "";
			for(int i=0; i < productSellDailyList.size(); i++) {
				ProductSellDaily productSellDaily = productSellDailyList.get(i);
				//自动去重
				legendData.add(productSellDaily.getProduct().getProductName());
				xData.add(sdf.format(productSellDaily.getCreateTime()));
				if(!currentProductName.equals(productSellDaily.getProduct().getProductName()) && !currentProductName.isEmpty()) {
					//如果currentProductName不等于获取的商品名，或者已遍历到列表的末尾，且currentProductName不为空
					//则是遍历到下一个商品的日销量信息了，将前一轮遍历的信息放入series中
					//包括了商品名以及商品对应的统计日期以及当日的销量
					EchartSeries es = new EchartSeries();
					es.setName(currentProductName);
					es.setData(totalList.subList(0, totalList.size()));
					series.add(es);
					//重置totalList
					totalList = new ArrayList<>();
					//变换下currentProductId为当前的productId
					currentProductName = productSellDaily.getProduct().getProductName();
					//继续添加新的值
					totalList.add(productSellDaily.getTotal());
				}else {
					//如果还是当前的productId则继续添加新值
					totalList.add(productSellDaily.getTotal());
					currentProductName = productSellDaily.getProduct().getProductName();
				}
				//队列之末，需要将最后一个商品销量信息也添加上
				if( i == productSellDailyList.size() -1) {
					EchartSeries es = new EchartSeries();
					es.setName(currentProductName);
					es.setData(totalList.subList(0, totalList.size()));
					series.add(es);
				}
			}
			modelMap.put("series", series);
			modelMap.put("legendData", legendData);
			//拼接出xAxis
			List<EchartXAxis> xAxis = new ArrayList<>();
			EchartXAxis exa = new EchartXAxis();
			exa.setData(xData);
			xAxis.add(exa);
			modelMap.put("xAxis", xAxis);
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	
}

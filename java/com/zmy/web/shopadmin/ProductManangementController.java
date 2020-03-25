package com.zmy.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zmy.dto.ImageHolder;
import com.zmy.dto.ProductExecution;
import com.zmy.entity.Product;
import com.zmy.entity.ProductCategory;
import com.zmy.entity.Shop;
import com.zmy.enums.ProductStateEnum;
import com.zmy.exceptions.ProductOperationException;
import com.zmy.service.IProductCategoryService;
import com.zmy.service.IProductService;
import com.zmy.util.CodeUtil;
import com.zmy.util.HttpServletRequestUtil;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年4月10日下午1:37:47 
 * Class Description：店铺-商品管理操作方法集散地
 */
@Controller
@RequestMapping(value = "/shopadmin")
public class ProductManangementController {
	@Autowired
	private IProductService productService;
	@Autowired
	private IProductCategoryService productCategoryService;
	
	private static final int MAXIMGCOUNT = 8;
	
	@RequestMapping(value = "getproductlistbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductListByShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		if(pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
			//获取传入的需要检索的条件，包括是否需要从某个商品类别以及模糊查找商品名取筛选某个店铺下的商品列表
			//筛选的条件可以进行排列组合
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			String productName = HttpServletRequestUtil.getString(request, "productName");
			Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
			//传入查询条件以分页信息查询，返回相应商品列表和总数
			ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or PageIndex or shopId");
		}
  		return modelMap;
	}
	
	private Product compactProductCondition(Long shopId, long productCategoryId, String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		//若有指定类别的要求则添加进去
		if(productCategoryId != -1L) {
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		//若有商品名模糊查询的要求则添加进去
		if(productName != null) {
			productCondition.setProductName(productName);
		}
		return productCondition;
	}

	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductById(@RequestParam Long productId){
		Map<String, Object> modelMap = new HashMap<>();
		List<ProductCategory> productCategoryList = new ArrayList<>();
		Product productCondition = new Product();
		try {
			if(productId > -1) {
				productCondition = productService.getProductById(productId);
				System.out.println(productCategoryService);
//				Shop shop = new Shop();
//				shop.setShopId(1L);
//				productCondition.setShop(shop);
				productCategoryList = productCategoryService.getProductCategoryList(productCondition.getShop().getShopId());
				modelMap.put("success", true);
				modelMap.put("product", productCondition);
				modelMap.put("productCategoryList", productCategoryList);
			}
		}catch(ProductOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "获取商品信息失败");
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/releaseproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> releaseProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", true);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		// 接收前台商品、缩略图、详情图列表实体类信息
		ObjectMapper mapper = new ObjectMapper();
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		Product product = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<>();
		try {
			// 若请求中有文件流，则取出相关的文件
			if (multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request, thumbnail, productImgList);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传的图片不能为空");
				return modelMap;
			}
		} catch (ProductOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		//将前端获取到的商品信息添加到tb_product
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch (IOException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		//若product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
		if(product != null && thumbnail != null && productImgList != null) {
			try {
				//从session中获取当前店铺的id并赋值给product,减少对前端数据的依赖
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
				if(pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
	return modelMap;
	}

	private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws IOException {
		//转换成多部分request
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
		//获取商品缩略图
		if(thumbnailFile != null) {
			thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
		}
		//取出详情页图片并构建list<ImageHolder>列表对象，
		for(int i=0; i < MAXIMGCOUNT ; i++) {
			CommonsMultipartFile productImgFile = (CommonsMultipartFile)multipartRequest.getFile("productImg" +i);
			if(productImgFile != null) {
				//若取出的第i个详情图片流不为空，则将其加入详情图列表
				productImgList.add(new ImageHolder(productImgFile.getInputStream(), productImgFile.getOriginalFilename()));
			}else {
				//若取出的第i个详情图片文件流为空，则终止循环
				break;
			}
		}
		return thumbnail;
	}
	
	@RequestMapping(value="/modifyproduct", method=RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyProduct(HttpServletRequest request){
		Map<String, Object> modelMap =  new HashMap<>();
		//是商品编辑时候调用还是上下架操作的时候调用
		//若为前者进行验证码判断，后者则跳过验证码判断
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		
		if(!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		//接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		ImageHolder thumbnail =null;
		List<ImageHolder> productImgList = new ArrayList<>();
		//先判断用户是否有文件流上传
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			if(multipartResolver.isMultipart(request)) {
//				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
//				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile)multipartRequest.getFile("thumbnail");
//				if(thumbnail != null) {
//					thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
//				}
//				//取出详情图列表
//				for(int i = 0; i < MAXIMGCOUNT; i++) {
//					CommonsMultipartFile productImgFile = (CommonsMultipartFile)multipartRequest.getFile("productImg" + i);
//					if(productImgFile != null) {
//						ImageHolder productImg = new ImageHolder(productImgFile.getInputStream(), productImgFile.getOriginalFilename());
//						productImgList.add(productImg);
//					}else {
//						break;
//					}
//				}
				thumbnail = handleImage(request, thumbnail, productImgList);
			}
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			String productStr = HttpServletRequestUtil.getString(request, "productStr");
			product = mapper.readValue(productStr, Product.class);
		}catch(Exception e){ 
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//非空判断
		if(product != null) {
			try {
				//从session中获取店铺id并赋值给product
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				//开始进行商品信息变更操作
				ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
				if(pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
			return modelMap;
		}
		return modelMap;
	}

//	private void handleImage(HttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> productImgList)
//			throws IOException {
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
//		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile)multipartRequest.getFile("thumbnail");
//		if(thumbnail != null) {
//			thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
//		}
//		//取出详情图列表
//		for(int i = 0; i < MAXIMGCOUNT; i++) {
//			CommonsMultipartFile productImgFile = (CommonsMultipartFile)multipartRequest.getFile("productImg" + i);
//			if(productImgFile != null) {
//				ImageHolder productImg = new ImageHolder(productImgFile.getInputStream(), productImgFile.getOriginalFilename());
//				productImgList.add(productImg);
//			}else {
//				break;
//			}
//		}
	}
	
	
	
	

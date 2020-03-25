$(function(){
	//定义获取头条列表和店铺分类一级列表的URL
	var url = '/o2o/frontend/getmainpagelist';
	//检查用户是否登录状态
	var loginStateCheckUrl = '/o2o/local/loginstatecheck';
	var width =getDeviceWidth();
	var height =getDeviceHeight();
	//获取屏幕高向下取整数值
	height =Math.floor(height/3);
	$.getJSON(url, function(data){
		if(data.success){
			var headList = data.headLineList;
			var swiperHtml = '';
			//遍历头条列表,并拼接出轮播图组
			headList.map(function(item, index){
				swiperHtml +='<div class="swiper-slide img-wrap">'
				+'<a href="'+item.lineLink+'" external>'
				+'<img class="banner-img" src="' + item.lineImg+'"'
				+' width="'+width+'" height="'+height+'"'
				+'alt="' + item.lineName + '">'
				+'</a></div>';
			});
			$('.swiper-wrapper').html(swiperHtml);
			//设定轮播图转换时间为3秒
			$('.swiper-container').swiper({
				autoplay : 3000,
				//用户对轮播图操作时，是否自动停止autoplay
				autoplayDisableOnInteraction : false
			});
			//获取后台传递过来的大类列表
			var shopCategoryList = data.shopCategoryList;
			var categoryHtml ='';
			shopCategoryList.map(function(item, index){
				categoryHtml +='<div class="col-20 shop-classify" data-category="'+item.shopCategoryId+'">'
				+'<div class="shop-classify-img-warp">'
				+'<img class="shop-img" src="'+item.shopCategoryImg+'" width="30" height="30px">'
				+'</div>'
				+ '<div class="word">'
				+ '<p class="shop-desc">'+item.shopCategoryDesc +'</p >'
				+'</div>'
				+'</div>';
			});
			$('.firstclassify').html(categoryHtml);
			notice(data.notice);
		}
	});
	
	//点击“我的”，向后台发起loginStateCheckUrl请求,判断用户是否已登录
	$('#me').click(function(){
			$.getJSON(loginStateCheckUrl, function(data){
				if(data.success){
					if(data.userType == 1){
						window.location.href = '/o2o/frontend/myinfomation';
					}else{
						window.location.href = '/o2o/shopadmin/shoplist';
					}
				}else{
					$.toast(data.errMsg);
					window.location.href = '/o2o/local/login';
				}
			});
	});
	
	$('.row').on('click','.shop-classify',function(e){
		var shopCategoryId = e.currentTarget.dataset.category;
		var newUrl = '/o2o/frontend/shoplist?parentId='+shopCategoryId;
		window.location.href=newUrl;
	});
	
	$(document).on('load', function(){
		sessionStorage.setItem("pop","true");
	});
	
	/**
	 * 首页弹窗公告
	 * @returns
	 */
	function notice(notice){
	$(document).on('click', '.create-popup', function(){
		 var popupHTML = '<div class="popup">'
		 +'<div class="content-block">'
		 +'<p>'+ notice.title +'</p>'
		 + '<p style="font-family:fantasy;font-size: 14px;color: #f40">'+ notice.text +'</p>'
		 + '<p><a href="#" class="close-popup">Close me</a></p>'
		 + '</div>'
		 + '</div>'
		$.popup(popupHTML);
		});
	}
	
	$('#search').on('change', function(e) {
		var productName = e.target.value;
		window.location.href = '/o2o/frontend/productlist?productName=' + productName;
	});
})
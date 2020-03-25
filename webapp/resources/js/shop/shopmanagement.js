$(function(){
	//获取用户登录状态加用户类型方便做页面的跳转
	var getTypeUrl = '/o2o/shopadmin/getshopusertype';
	$.getJSON(getTypeUrl, function(data){
		if(data.success){
			if(data.userType == 1){
				$.toast("请登录商家账号进行操作");
				window.location.href = '/o2o/frontend/index';
			}
		}else{
			//用户未登录
			window.location.href = '/o2o/frontend/index';
		}
	});
	
	var shopId = getQueryString('shopId');
	var shopInfoUrl = '/o2o/shopadmin/getshopmanagementinfo?shopId=' + shopId;
	$.getJSON(shopInfoUrl,function(data){
		if(data.redirect){
			window.location.href = data.url;
		}else{
			if(data.shopId != undefined && data.shopId != null){
				shopId = data.shopId;
			}
			$('#shopInfo').attr('href','/o2o/shopadmin/shopoperation?shopId=' + shopId);
		}
		
	});
})
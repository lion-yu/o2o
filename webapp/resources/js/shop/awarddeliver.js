$(function(){
	// 获取用户登录状态加用户类型方便做页面的跳转
	var getTypeUrl = '/o2o/local/getusertype';
	$.getJSON(getTypeUrl, function(data){
		if(data.success){
			if(data.userType == 1){
				$.toast("权限不足");
				window.location.href = '/o2o/frontend/index';
			}
		}else{
			$.toast("请登录再操作");
			window.location.href = '/o2o/frontend/index';
		}
	});
	var awardName = '';
	var listUrl = '/o2o/shopadmin/listuserawardmapsbyshop?pageIndex=1&pageSize=999&awardName=' + awardName
	getList();
	function getList(){
		$.getJSON(listUrl,function(data){
			if(data.success){
				var userAwardMapList = data.userShopMapList;
				var tempHtml = '';
				userAwardMapList.map(function(item,index){
					tempHtml +='<div class="card">'
					+	'<div class="card-header award-name">奖品名:'+item.award.awardName+'</div>'
					+ '<div class="card-content"><div class="list-block media-list"><ul>'
					+ '<li class="item-content">'
					+' <div class="item-inner"><div class="item-title-row">'
					+'<div class="item-title user-name">用户名:'+item.user.name+'</div>'
					+'</div><div class="item-subtitle operator">操作员:'+item.operator.name+'</div><div class="item-subtitle">消耗积分:'+ item.point+'</div>'
					+'<div class="item-subtitle">兑换时间:'+new Date(item.createTime).Format("yyyy-MM-dd hh:mm:ss")+'</div>'
					+'</div></li><ul></div></div>'
					+'</div> '
//					tempHtml +=''+'<div class="row row-awarddeliver">'
//					+'<div class="col-10">' + item.award.awardName + '</div>'
//					+'<div class="col-25 awarddeliver-time">' + new Date(item.createTime).Format("yyyy-MM-dd hh:mm:ss")
//					+ '</div class="20">' + item.user.name
//					+'</div>' + '<div class="col-10">' + item.point
//					+'</div>' + '<div class="col-20">'
//					+item.operator.name +'</div></div>'
				});
				$('.awarddeliver-wrap').html(tempHtml);
			}
		});
	}
	
	$('#search').on('change', function(e){
		awardName = e.target.value;
		$('.awarddeliver-wrap').empty();
		 listUrl = '/o2o/shopadmin/listuserawardmapsbyshop?pageIndex=1&pageSize=999&awardName=' + awardName
		getList();
	});
	
	
	
})
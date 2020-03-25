$(function(){
	
	// 获取用户登录做页面权限验证
	var getStateUrl = '/o2o/frontend/checkuserloginstatus';
	$.getJSON(getStateUrl, function(data) {
		if (data.success) {
		} else {
			$.toast("未登录");
			window.location.href = '/o2o/frontend/index';
		}
	});
	
	var loading = false;
	var maxItems = 20;
	var pageSize = 10;
	//获取该用户的奖品领取记录列表的URL
	var listUrl = '/o2o/frontend/listuserawardmapsbycustomer';
	
	var pageNum =1;
	var awardName = '';
	addItems(pagSize, pageNum);
	//按照查询条件获取奖品兑换记录列表，并生成对应的html元素添加到页面中
	function addItems(pageSize,pageIndex){
		var url = listUrl + '?pageIndex=' + pageIndex + '&pageSize=' + pageSize
		+ '&awardName=' + awardName;
		loading = true;
		$.getJSON(url , function(data){
			if(data.success){
				maxItems = data.count;
				var html ='';
				data.userAwardMapList.map(function(item, index){
					var status = '';
					//根据usedStatus显示是否已在实体店领取过奖品
					if(item.usedStatus == 0){
						status ='未领取';
					}else{
						status = '未领取';
					}
					html +='' + '<div class="card" data-user-award-id='
					+ item.userAwardId + '>'
					+'<div class="card-header">' + item.shop.shopName
					+'<span class="pull-right">'+ status
					+'</span></div>' + '<div class="card-content">'
					+'<div class="list-block media-list">' + '<ul>'
					+'<li class="item-content">'
					+'<div class="item-inner">'
					+'<div class="item-subtitle">' + item.award.awardName
					+'</div>' + '</div>' +'</li>' + '</ul>'
					+'</div>' + '</div>' + '<div class="card-footer">' 
					+'<p class="color-gray">'
					+ new Date(item.createTime).Format("yyyy-MM-dd hh:mm:ss")
					+'</p>' + '<span>消耗积分:' +item.point + '</span>'
					+'</div></div>' 
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
				if(total >= maxItems){
					//加载完毕，则注销无限加载事件，以防止不必要的加载
					$.detachInfiniteScroll($('.infinite-scroll'));
					$('.infinite-scroll-preloader').remove();
					return;
				}
				pageNum +=1;
				loading = false;
				$.refreshScroller();
			}
		});
	}
	//顾客凭借详情页中的二维码到实体店给店员扫描领取实物奖品
	$('.list-div').on('click','.card',function(e){
		var userAwardId = e.currentTarget.dataset.userAwardId;
		window.location.href='/o2o/frontend/myawarddetail?userAwardId=' +userAwardId;
	});
	//无限滚动
	$(ducument).on('infinite','.infinite-scroll-bottom',function(){
		if(loading)
			return;
		addItems(pageSize, pageNum);
	});
	
	$('#search').on('change', function(e) {
		awardName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});
	
})
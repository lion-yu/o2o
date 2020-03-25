$(function(){
	var getStatusUrl = '/o2o/frontend/checkuserloginstatus';
	$.getJSON(getStatusUrl, function(data){
		if(data.success){
		}else{
			//用户未登录
			window.location.href = '/o2o/frontend/index';
		}
	});
	var loading = false;
	var maxItems = 999;
	var awardListUrl = '/o2o/frontend/listawardbyshop';
	var shopId = getQueryString('shopId');
	var pageIndex= 1;
	var pageSize = 3;
	var awardName ='';
	// 默认先加载四条
	addItems(pageIndex, pageSize);
	/**
	 * 获取分页展示的奖品列表信息
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @returns
	 */
	function addItems(index, size) {
		// 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
		var url = awardListUrl + '?' + 'pageIndex=' + index + '&pageSize='
				+ size+ '&awardName=' + awardName + '&shopId=' + shopId;
		// 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
		loading = true;
		// 访问后台获取相应查询条件下的店铺列表
		$.getJSON(url, function(data) {
			if (data.success) {
				if(data.count == 0){
					$('.award-list').html('<span align="center">该店铺没有奖品哦</span>');
					$('.infinite-scroll-preloader').hide();
					return;
				}
				// 获取当前查询条件下店铺的总数
				maxItems = data.count;
				var status = '';
				var tempHtml = '';
					data.awardList.map(function(item, index){
						tempHtml +='<div class="card" data-award-id="'+item.awardId+'" data-award-point="'+item.point+'">'
						+	'<div class="card-header">'+item.awardName+'</div>'
						+ '<div class="card-content"><div class="list-block media-list"><ul>'
						+ '<li class="item-content" data-award-id="'+item.awardId+'"><div class="item-media">'
						+'<img src="'+item.awardImg+'" width="100"></div>'
						+' <div class="item-inner"><div class="item-title-row">'
						+'<div class="item-title">'+item.awardDesc+'</div>'
						+'</div><div class="item-subtitle">'+item.point+'</div></div></li><ul></div></div>'
						+'<div class="card-footer">'
						+'<span>'+new Date(item.lastEditTime).Format("yyyy-MM-dd")+'更新</span>'
						+'</div>'
						+'</div> '
					});
				$('.award-list').html(tempHtml);
				$('.title').text(data.totalPoint);
				// 获取目前为止已显示的卡片总数，包含之前已经加载的
				var total = $('.list-div .card').length;
				// 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
				if (total >= maxItems) {
					// 隐藏提示符
					$('.infinite-scroll-preloader').hide();
				} else {
					$('.infinite-scroll-preloader').show();
				}
				// 否则页码加1，继续load
				pageIndex += 1;
				// 加载结束，可以再次加载了
				loading = false;
				// 刷新页面，显示新加载的店铺
				$.refreshScroller();
			}
		});
	}
	//卡片添加积分兑换事件
	$('.award-list').on('click', '.card', function(e){
		var awardId = e.currentTarget.dataset.awardId;
		var point = e.currentTarget.dataset.awardPoint;
		$.confirm('确定兑换吗',function(){
			if(point > $('.title').text()){
				$.toast('积分不足'); 
				return;
			}
			var url = '/o2o/frontend/exchangeaward?awardId=' + awardId + '&point=' + point
			+ '&shopId=' + shopId;
			$.getJSON(url, function(data){
				if(data.success){
					window.location.href='/o2o/frontend/userawardlist';
				}
			})
		})
	});
	// 下滑屏幕自动进行分页搜索
	$(document).on('infinite', '.infinite-scroll-bottom', function(e) {
		if (loading)
			return;
		addItems(pageIndex, pageSize);
	});
	

	
	// 需要查询的奖品名字发生变化后，重置页码，清空原先的店铺列表，按照新的名字去查询
	$('#search').on('change', function(e) {
		awardName = e.target.value;
		$('.list-div').empty();
		pageIndex = 1;
		addItems(pageIndex, pageSize);
	});


	

	$.init();
})
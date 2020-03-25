/**
 * 顾客的订单列表
 */
$(function(){
	var getStatusUrl = '/o2o/frontend/checkuserloginstatus';
	$.getJSON(getStatusUrl, function(data){
		if(data.success){
		}else{
			//用户未登录
			window.location.href = '/o2o/frontend/index';
		}
	});
	var maxItems = 999;
	var pageIndex = 1;
	var pageSize = 5;
	var productName ='';
	//设定加载符
	var loading = false;
	//默认加载三条用户奖品信息
	addItems(pageIndex, pageSize);
	
	function addItems(index, size){
		var url = '/o2o/frontend/getuserproductlistbyuserid?pageIndex=' + pageIndex +'&pageSize=' + pageSize + '&productName=' + productName;
		$.getJSON(url , function(data){
			if(data.success){
				//如果没有一条记录的话
				if(data.count == 0){
					$('.list-div').text('没有订单记录哦');
					return;
				}
				maxItems = data.count;
				var html = '';
				data.userProductMapList.map(function(item, index){
					var commentState = (item.commentState == 0) ? '未评价'
							: '已评价';
					html += '<div class="card">'
							+ ' <div class="card-header" data-shop-id="'
							+ item.shop.shopId
							+ '">'
							+ item.shop.shopName
							+ '</div>'
							+ '<div class="card-content" data-product-id="'
							+ item.product.productId
							+ '"><div class="list-block media-list">'
							+ '<ul><li class="item-content"><div class="item-media">'
							+ '<img src="'
							+ item.product.imgAddr
							+ '" width="44">'
							+ '</div><div class="item-inner"><div class="item-title-row">'
							+ ' <div class="item-title">'
							+ item.product.productName
							+ '</div>'
							+ ' </div>'
							+ '<div class="item-subtitle">积分:'
							+ item.point
							+ '</div>'
							+ '<div class="item-subtitle">合计:￥'
							+ item.product.promotionPrice
							+ '</div>'
							+ '</div></li></ul></div></div>'
							+ '<div class="card-footer"><span>'
							+ new Date(item.createTime)
									.Format('yyyy-MM-dd hh:mm:ss')
							+ '</span>'
							+ '<a id="comment" data-userproduct-id="'
							+ item.userProductId
							+ '" data-comment-state="'
							+ item.commentState
							+ '">'
							+ commentState
							+ '</a></div>'
							+ '</div>'
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
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
	
	// 下滑屏幕自动进行分页搜索
	$(document).on('infinite', '.infinite-scroll-bottom', function(e) {
		if (loading)
			return;
		addItems(pageIndex, pageSize);
	});
	
	// 需要查询的商品名发生变化后，重置页码，清空原先的商品列表，按照新的名字去查询
	$('#search').on('change', function(e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageIndex = 1;
		addItems(pageIndex, pageSize);
	});
	
	// 给评价按钮添加事件
	$('.list-div')
			.on(
					'click',
					'a',
					function(e) {
						var userProductId = e.currentTarget.dataset.userproductId;
						// 获取评价状态
						var commentState = e.currentTarget.dataset.commentState
						// 如果时未评价页面0跳转至评价页面，如果是已评价1跳转修改评价页面
						if (commentState == 0) {
							// 跳转到评价页面
							window.location.href = '/o2o/frontend/comment?userProductMapId='
									+ userProductId;
						} else {
							window.location.href = '/o2o/frontend/commentedit?userProductMapId='
									+ userProductId;
						}
					})
	// 给订单卡片头部添加店铺事件
	$('.list-div').on(
			'click',
			'.card-content',
			function(e) {
				var productId = e.currentTarget.dataset.productId;
				// 跳转到店铺详情页面
				window.location.href = '/o2o/frontend/productdetail?productId='
						+ productId;
			})

	// 给订单中部添加商品详情页图片
	$('.list-div').on('click', '.card-header', function(e) {
		var shopId = e.currentTarget.dataset.shopId;
		// 跳转到店铺详情页面
		window.location.href = '/o2o/frontend/shopdetail?shopId=' + shopId;
	})
	$.init();
})
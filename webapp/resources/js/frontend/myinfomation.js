$(function() {
	// 获取用户登录做页面权限验证
	var getStateUrl = '/o2o/frontend/checkuserloginstatus';
	$.getJSON(getStateUrl, function(data) {
		if (data.success) {
		} else {
			$.toast("未登录");
			window.location.href = '/o2o/frontend/index';
		}
	});

	// 用户基本信息请求url
	var baseInfomationUrl = '/o2o/local/myinfomation';
	$
			.getJSON(
					baseInfomationUrl,
					function(data) {
						if (data.success) {
							var personInfo = data.personInfo;
							$('#user-name').text(personInfo.name);
							$('#create-time').text(
									'生日:'
											+ new Date(personInfo.createTime)
													.Format('yyyy-MM-dd'));
							$('#personImg').attr('src', personInfo.profileImg);
							if (data.count == 0) {
								$('.list-order').html(
										'<p align="center">无历史订单</p>');
								return;
							}
							var html = '';
							data.userProductMapList
									.map(function(item, index) {
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
							$('.list-order').html(html);
						} else {
							$.toast(data.errMsg);
							window.location.href = '/o2o/local/login';
						}
					});
	// 给评价按钮添加事件
	$('.list-order')
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
	$('.list-order').on(
			'click',
			'.card-content',
			function(e) {
				var productId = e.currentTarget.dataset.productId;
				// 跳转到店铺详情页面
				window.location.href = '/o2o/frontend/productdetail?productId='
						+ productId;
			})

	// 给订单中部添加商品详情页图片
	$('.list-order').on('click', '.card-header', function(e) {
		var shopId = e.currentTarget.dataset.shopId;
		// 跳转到店铺详情页面
		window.location.href = '/o2o/frontend/shopdetail?shopId=' + shopId;
	})
	$('#points').click(
			function() {
				var url = '/o2o/frontend/getuserpoint';
				$.getJSON(url, function(data) {
					if (data.success) {
						var html = '';
						data.userShopMapList.map(function(item, index) {
							html += ' <div class="card-header">'
									+ item.shop.shopName + ':' + item.point
									+ '分</div>'
						});
						$('.list-div').html(html);
					} else {
						$.toast(data.errMsg);
					}
				})
			});

	$('#records').click(function() {
		window.location.href = '/o2o/frontend/userawardlist';
	})

	$('#personinfo').click(function() {
		window.location.href = '/o2o/frontend/personinfo'
	})

	// 点击后打开右侧栏
	$('#setting').click(function() {
		$.openPanel('#panel-right-my');
	});

	$('#bind').click(function() {
		$.ajax({
			url : "/o2o/shopadmin/getshopusertype",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					window.location.href = '/o2o/local/accountbind';
				} else {
					window.location.href = '/o2o/local/login';
				}
			}
		});
	});
	$('#bindAndChange').click(function() {
		$.ajax({
			url : "/o2o/shopadmin/getshopusertype",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					window.location.href = '/o2o/local/changepsw';
				} else {
					window.location.href = '/o2o/local/login';
				}
			}
		});
	});
})
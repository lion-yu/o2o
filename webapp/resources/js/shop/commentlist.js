$(function() {
	var orderListUrl = '/o2o/shopadmin/getuserproductmaplist';
	// 获取订单列表
	getList();
	function getList() {
		$
				.getJSON(
						orderListUrl,
						function(data) {
							if (data.success) {
								var commentList = data.commentList;
								var html = '';
								var waitCommentHtml = '';
								var commentedHtml = '';
								data.userProductMapList
										.map(function(item, index) {
											var temp = commentList[index] == null ? '未评价'
													: commentList[index].comments;
											if (temp != '未评价') {
												waitCommentHtml += '<div class="card" style="border-radius : .5rem">'
														+ ' <div class="card-header">订单编号:'
														+ item.userProductId
														+ '</div>'
														+ '<div class="card-content">'
														+ '<div class="list-block media-list">'
														+ '<ul><li class="item-content"><div class="item-media">'
														+ '<img src="'
														+ item.product.imgAddr
														+ '" width="44">'
														+ '</div><div class="item-inner"><div class="item-title-row">'
														+ ' <div class="item-title">'
														+ item.product.productName
														+ '</div>'
														+ ' </div>'
														+ '<div class="item-subtitle">'
														+ item.product.productDesc
														+ '</div>'
														+ '<div class="item-subtitle success">成交价:'
														+ item.product.promotionPrice
														+ '</div>'
														+ '</div></li></ul></div></div>'
														+ '<div class="card-footer"><span>购买用户:'
														+ item.user.name
														+ '</span>'
														+ '<span>评价信息:'
														+ temp
														+ '</span></div>'
														+ '</div>'
											} else {
												commentedHtml += '<div class="card" style="border-radius : .5rem">'
														+ ' <div class="card-header">订单编号:'
														+ item.userProductId
														+ '</div>'
														+ '<div class="card-content">'
														+ '<div class="list-block media-list">'
														+ '<ul><li class="item-content"><div class="item-media">'
														+ '<img src="'
														+ item.product.imgAddr
														+ '" width="44">'
														+ '</div><div class="item-inner"><div class="item-title-row">'
														+ ' <div class="item-title">'
														+ item.product.productName
														+ '</div>'
														+ ' </div>'
														+ '<div class="item-subtitle">'
														+ item.product.productDesc
														+ '</div>'
														+ '<div class="item-subtitle success">成交价:'
														+ item.product.promotionPrice
														+ '</div>'
														+ '</div></li></ul></div></div>'
														+ '<div class="card-footer"><span>购买用户:'
														+ item.user.name
														+ '</span>'
														+ '<span>评价信息:'
														+ temp
														+ '</span></div>'
														+ '</div>'
											}

											html += '<div class="card" style="border-radius : .5rem">'
													+ ' <div class="card-header">订单编号:'
													+ item.userProductId
													+ '</div>'
													+ '<div class="card-content">'
													+ '<div class="list-block media-list">'
													+ '<ul><li class="item-content"><div class="item-media">'
													+ '<img src="'
													+ item.product.imgAddr
													+ '" width="44">'
													+ '</div><div class="item-inner"><div class="item-title-row">'
													+ ' <div class="item-title">'
													+ item.product.productName
													+ '</div>'
													+ ' </div>'
													+ '<div class="item-subtitle">'
													+ item.product.productDesc
													+ '</div>'
													+ '<div class="item-subtitle success">成交价:'
													+ item.product.promotionPrice
													+ '</div>'
													+ '</div></li></ul></div></div>'
													+ '<div class="card-footer"><span>购买用户:'
													+ item.user.name
													+ '</span>'
													+ '<span>评价信息:'
													+ temp
													+ '</span></div>'
													+ '</div>'
										});
								$('#tab1-1').html(html);
								$('#waitcomment-wrap').html(waitCommentHtml);
								$('#commented').html(commentedHtml);
							}
						});
	}

	$('#search')
			.change(
					function(e) {
						var target = e.currentTarget;
						var productName = target.value;
						orderListUrl = '/o2o/shopadmin/getuserproductmaplist?productName='
								+ productName;
						getList();
					})
})
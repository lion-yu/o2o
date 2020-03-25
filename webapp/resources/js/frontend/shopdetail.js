/**
 * 
 */
$(function() {
	// 购物车数量
	var productCount = 1;
	// 总价
	var priceCount = 0;
	// 购物车容器的初始高度
	var height = 50;
	// 购物车是否展示；
	var hide = true;

	var loading = false;
	// 分页允许返回的最大条数，超过此数则禁止访问后台
	var maxItems = 20;
	// 默认一页返回的商品数
	var pageSize = 3;
	// 列出商品列表的URL
	var listUrl = '/o2o/frontend/listproductsbyshop';
	// 默认的页码
	var pageNum = 1;
	// 从地址栏里获取ShopId
	var shopId = getQueryString('shopId');
	var productCategoryId = '';
	var productName = '';
	// 获取本店铺信息以及商品类别信息列表的URL
	var searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId=' + shopId;
	// 渲染出店铺基本信息以及商品类别列表以供搜索
	getSearchDivData();
	// 预先加载10条商品信息
	addItems(pageSize, pageNum);

	// 给兑换礼品的a标签赋值兑换礼品的URL
	$('#exchangelist').attr('href', '/o2o/frontend/awardlist?shopId=' + shopId);
	// 获取本店铺信息以及商品类别信息列表
	function getSearchDivData() {
		var url = searchDivUrl;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								var shop = data.shop;
								$('#shop-cover-pic').attr('src', shop.shopImg);
								$('#shop-update-time').text(
										"最近更新时间"
												+ new Date(shop.lastEditTime)
														.Format("yyyy-MM-dd"));
								$('#shop-name').text(shop.shopName);
								$('#shop-location').text('点我获取位置');
								$('#shop-desc').text(shop.shopDesc);
								$('#shop-addr').text(shop.shopAddr);
								$('#shop-phone').text(shop.phone);
								// 获取后台返回的该店铺的商品类别列表
								var productCategoryList = data.productCategoryList;

								var html = '<div class="content-padded grid-demo"><div class="row">';
								// 遍历商品类别列表，生成可以点击搜索相应商品类别下的商品的a标签
								productCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="button col-33" data-product-search-id='
													+ item.productCategoryId
													+ '>'
													+ item.productCategoryName
													+ '</a>';
										});
								html += '</div></div>';
								// 将商品类别a标签绑定到相应的HTML组件中
								$('#shopdetail-button-div').html(html);
							}
						});
	}
	/**
	 * 获取分页展示的商品列表信息
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @returns
	 */
	function addItems(pageSize, pageIndex) {
		// 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&productCategoryId=' + productCategoryId
				+ '&productName=' + productName + '&shopId=' + shopId;
		// 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
		loading = true;
		// 访问后台获取相应查询条件下的商品列表
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								// 获取当前查询条件下商品的总数
								maxItems = data.count;
								var html = '';
								// 遍历商品列表，拼接出卡片集合
								data.productList
										.map(function(item, index) {
											html += ''
													+ '<div class="card">'
													+ '<div class="card-header">'
													+ item.productName
													+ '<input class="addbutton" type="button" style="border-radius:50%" value="+"'
													+ ' data-product-id='
													+ item.productId
													+ ' data-promotion-price='
													+ item.promotionPrice
													+ ' data-product-name='
													+ item.productName
													+ '>'
													+ '</div>'
													+ '<div class="card-content">'
													+ '<div class="list-block media-list">'
													+ '<ul>'
													+ '<li class="item-content"  data-product-id='
													+ item.productId
													+ '>'
													+ '<div class="item-media">'
													+ '<img src="'
													+ item.imgAddr
													+ '" width="70" height="70">'
													+ '</div>'
													+ '<div class="item-inner">'
													+ '<div class="item-subtitle promotion-price">'
													+ item.promotionPrice
													+ '</div>'
													+ '<div class="item-subtitle normal-price">'
													+ item.normalPrice
													+ '</div>'
													+ '<div class="item-subtitle ">'
													+ item.productDesc
													+ '</div>'
													+ '<div class="item-subtitle" align="right">'
													+ '</div>'
													+ '</div>'
													+ '</li>'
													+ '</ul>'
													+ '</div>'
													+ '</div>'
													+ '<div class="card-footer">'
													+ '<p class="color-gray">'
													+ new Date(
															item.lastEditTime)
															.Format("yyyy-MM-dd")
													+ '更新</p>'
													+ '<span>销量:'
													+ data.countList[index]
													+ '</span>'
													+ '</div>'
													+ '</div>';
										});
								// 将卡片集合添加到目标HTML组件里
								$('.list-div').append(html);
								// 获取目前为止已显示的卡片总数，包含之前已经加载的
								var total = $('.list-div .card').length;
								// 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
								if (total >= maxItems) {
									// 隐藏提示符
									$('.infinite-scroll-preloader').hide();
								} else {
									$('.infinite-scroll-preloader').show();
								}
								// 否则页码加1，继续load出新的店铺
								pageNum += 1;
								// 加载结束，可以再次加载了
								loading = false;
								// 刷新页面，显示新加载的店铺
								$.refreshScroller();
							}
						});
	}

	// 下滑屏幕自动进行分页搜索
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});
	// 选择新的商品类别之后，重置页码，清空原先的商品列表，按照新的类别去查询
	$('#shopdetail-button-div').on(
			'click',
			'.button',
			function(e) {
				// 获取商品类别Id
				productCategoryId = e.target.dataset.productSearchId;
				if (productCategoryId) {
					// 若之前已选定了别的category,则移除其选定效果，改成选定新的
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						productCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
				}
			});

	// 点击商品的卡片进入该商品的详情页
	$('.list-div').on(
			'click',
			'li',
			function(e) {
				var productId = e.currentTarget.dataset.productId;
				window.location.href = '/o2o/frontend/productdetail?productId='
						+ productId;
			});
	// 点击添加按钮将商品加入购物车
	$('.list-div')
			.on(
					'click',
					'.card-header .addbutton',
					function(e) {
						loginCheck();
						var productId = e.currentTarget.dataset.productId;
						var productName = e.currentTarget.dataset.productName;
						var promotionPrice = e.currentTarget.dataset.promotionPrice;
						var elements = $('.product-wrap div').find('#count');
						if (elements.length <= 0) {
							height = height + 25;
							$('.a').css('height', height + 'px');
							$(' .product-wrap')
									.append(
											'<div class="row"><span class="col-40" id="productid" data-product-id="'
													+ productId
													+ '">'
													+ productName
													+ '</span><span class="col-40" id="count" data-product-id="'
													+ productId + '">' + 1
													+ '</span><span>'
													+ promotionPrice
													+ '</span></div>');
							$('.buy').after('<span class="badge"></span>');
							$('.badge').text(productCount++);
						}
						for (var i = 0; i < elements.length; i++) {
							// 如果添加的是购物车里已有的，则在相应的商品数量上加1
							if (productId == $(elements[i]).attr(
									'data-product-id')) {
								var temp = $(elements[i]).text();
								temp++;
								$(elements[i]).text(temp);
								break;
							}
							if (i >= elements.length - 1) {
								height = height + 25;
								$('.a').css('height', height + 'px');
								$(' .product-wrap')
										.append(
												'<div class="row"><span class="col-40" data-product-id="'
														+ productId
														+ '">'
														+ productName
														+ '</span><span class="col-40" id="count" data-product-id="'
														+ productId + '">' + 1
														+ '</span><span>'
														+ promotionPrice
														+ '</span></div>');
								$('.buy').after('<span class="badge"></span>');
								$('.badge').text(productCount++);
							}

						}

						priceCount = priceCount + parseInt(promotionPrice);
						$('#abc').text('总计(' + priceCount + ')元');
					});
	// 点击购物车弹出窗口
	$('.buy').click(function() {
		if($('.product-wrap div').find('#count').length != 0){
			if (hide) {
				$('.a').css('display', 'block');
				hide = false;
			}else{
				hide = true;
				$('.a').css('display', 'none');
			}
		}
	});
	// 给购物车容器设置个失去焦点事件 !!!!无效
	$('.a').blur(function() {
		$('.a').css('display', 'none');
	})
	// 清空购物车
	$('#empty').click(function() {
		$('.product-wrap').empty();
		productCount = 1;
		priceCount = 0;
		height = 50;
		promotionPrice = 0;
		$('.badge').remove();
		$('.a').css('height', height + 'px');
		$('.a').css('display', 'none');
	})
	// 点击购物
	$('#go').click(function(){
		loginCheck();
		var elements = $('.product-wrap div');
//		遍历购物车第二个商品获取不到productId值
		var productList = [];
		for(var i = 0; i<elements.length; i++){
			var obj = new Object();
			obj.productId = $(elements[i]).find('#productid').attr('data-product-id');
			obj.count = $(elements[i]).find('#count').text();
			productList.push(obj);
		}
		var jsonData = JSON.stringify(productList);
		$.ajax({
			url : '/o2o/frontend/batchaddorder',
			type : 'post',
			contentType : 'application/json',
			data : jsonData,
			dataType : 'json',
			cache : false,
			async : false,
			success :  function(data){
				if(data.success){
					$.toast('下单成功');
					window.location.href = '/o2o/frontend/myinfomation';
				}else{
					$.toast(data.errMsg);
				}
			}
			
		
		});	
	});
	// 需要查询的商品名字发生变化后，重置页码，清空原先的商品列表，按照新的名字去查询
	$('#search').on('change', function(e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});
	// 点击商品的卡片进入该商品的详情页
	$('#shop-location').click(
			function() {
				var shopLocation = $('#shop-addr').text();
				window.location.href = '/o2o/frontend/gaode?shoplocation='
						+ shopLocation;
			});
	// 检查用户是否登录状态
	var loginStateCheckUrl = '/o2o/local/loginstatecheck';
	function loginCheck(){
		$.getJSON(loginStateCheckUrl, function(data){
			if(data.success){
				
			}else{
				window.location.href = '/o2o/local/login';
			}
		})
	}
	// 点击“我的”，向后台发起loginStateCheckUrl请求,判断用户是否已登录
	// $('#me').click(function() {
	// $.getJSON(loginStateCheckUrl, function(data) {
	// if (data.success) {
	// if (data.userType == 1) {
	// window.location.href = '/o2o/frontend/myinfomation';
	// } else {
	// window.location.href = '/o2o/shopadmin/shoplist';
	// }
	// } else {
	// $.toast(data.errMsg);
	// window.location.href = '/o2o/local/login';
	// }
	// });
	// });
	$.init();
});
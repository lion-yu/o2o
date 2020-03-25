$(function(){
	//从地址栏中获取productName
	var productName = getQueryString('productName');
	//后台获取模糊查询的商品列表
	var url = '/o2o/frontend/getproductlistbyname?productName=' + productName;
	
		$.getJSON(url, function(data){
			$('#search').val(productName);
			if(data.success){
				var html = '';
				data.productList.map(function(item,index){
					html += '' + '<div class="card" data-product-id="'
					+ item.productId + '">' + '<div class="card-content">'
					+ '<div class="list-block media-list">' + '<ul>'
					+ '<li class="item-content">'
					+ '<div class="item-media">' + '<img src="'
					+ item.imgAddr + '" width="130" height="130">' + '</div>'
					+ '<div class="item-inner"><div class="item-title-row">'
	                + '<div class="item-title" id="shop-name">'+item.productName+'</div></div>'
					+ '<div class="item-subtitle" id="shop-desc">' + item.productDesc + '</div>'+ '<div class="item-subtitle" id="promotion-price">' 
					+ '<span style="color: #f40;font-size:20px">'+item.promotionPrice + '&nbsp;&nbsp;&nbsp;</span><span id="sales">销量:'+data.saleCountList[index]+'</span></div>' 
					+ '<a class="item-subtitle" id="shop-name" data-shop-id="'+item.shop.shopId+'">' 
					+ item.shop.shopName + '&nbsp;&nbsp;>进店</a>'+'</div>' + '</li>' + '</ul>'
					+ '</div>' + '</div>' 
					+ '</div>';
				});
				$('.product-list').html(html);
			}else{
				$('.product-list').html('<div><p align="center">'+data.errMsg+'</p></div>');
			}
		});
	
	
	// 点击商品的卡片进入该店铺的详情页
	$('.product-list').on('click', '.card', function(e) {
		var productId = e.currentTarget.dataset.productId;
		window.location.href = '/o2o/frontend/productdetail?productId=' + productId;
	});
	$('.product-list').on('click','#shop-name', function(e) {
		var shopId = e.currentTarget.dataset.shopId;
		window.location.href = '/o2o/frontend/shopdetail?shopId=' + shopId;
	});
	$('#search').on('change', function(e) {
		var productName = e.target.value;
		window.location.href = '/o2o/frontend/productlist?productName=' + productName;
	});			
	$('.searchbar-cancel').on('click', function(e) {
		$('.product-list').show();
	});
//	$('input').on('click', function(e) {
//		$('.product-list').hide();
//	});
})
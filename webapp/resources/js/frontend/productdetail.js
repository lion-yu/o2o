$(function(){
	//获取productId
	var productId = getQueryString("productId");
	//后台获取商品信息url
	var productInfoUrl = '/o2o/frontend/getproductdetail?productId=' + productId;
	getProductdetail();
	function getProductdetail(){
		$.getJSON(productInfoUrl, function(data){
			var product = data.product;
			var datailList = product.productImgList;
			var comments = data.comment;
			//获取商品基本信息
			if(data.success){
				$('#product-time').text("最近更新时间：" + new Date(product.lastEditTime).Format("yyyy-MM-dd"));
				$('#promotion-price').html('￥' +product.promotionPrice);
				if(product.point != 0){
					$('#product-point').html('购买可得<b style="color:#f40">' + product.point + '</b>积分'); 
				}
				$('#normal-price').html('价格:￥' +product.normalPrice);
				$('#product-name').html(product.productName);
				$('.card-cover').attr("src",product.imgAddr);
				$('#sales').text('销量' + data.sales);
				$('#comments').text('评价(' + data.commentCount + ')');
				var tempHtml = '';
				datailList.map(function(item, index){
					tempHtml += '<img  src="'+item.imgAddr+'"'
					+ 'width="'+getDeviceWidth()+'"/>';
				});
				$('.list-product-info').html(tempHtml);
				$('.list-product-info').hide();
				var commentsHtml ='';
				comments.map(function(item, index){
					commentsHtml += '<div class="card"><div class="card-content">'
				        +'<div class="card-content-inner">'+ item.comments + '</div></div></div>'
				});
				$('.list-comments-info').html(commentsHtml);
				$('.list-comments-info').hide();
			}
		});
	}
	

	$('.card-footer').on('click', '#detail',function(e){
		$('.list-comments-info').hide();
		$('.list-product-info').show();
		if($(e.target).hasClass('detail-color')){
		}else{
			$(e.target).addClass('detail-color').siblings().removeClass('detail-color');
		}
	});
	
	$('.card-footer').on('click', '#comments', function(e){
		$('.list-product-info').hide();
		$('.list-comments-info').show();
		if($(e.target).hasClass('detail-color')){
		}else{
			$(e.target).addClass('detail-color').siblings().removeClass('detail-color');
		}
	});
	
	$('.buy').click(function(){
		 loginCheck();
		$.getJSON('/o2o/frontend/addorder?productId=' + productId, function(data){
			if(data.success){
				$.toast('下单成功');
				window.location.href = '/o2o/frontend/myinfomation';
			}else{
				$.toast(data.errMsg);
			}
		})
	});
	
	// 检查用户是否登录状态
	var loginStateCheckUrl = '/o2o/local/loginstatecheck';
	function loginCheck(){
		$.getJSON(loginStateCheckUrl, function(data){
			if(data.success){
				
			}else{
				window.location.href = '/o2o/local/login';
			}
		});
	}
	
	

	
	
	
	
	
	
	
	
})
$(function() {
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
	var isEdit = shopId ? true : false;
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	var registerShopUrl = '/o2o/shopadmin/registershop';
	var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
	var editShopUrl = '/o2o/shopadmin/modifyshop';
	if (!isEdit) {
		getShopInitInfo();
	} else {
		getShopInfo(shopId);
	}

	function getShopInitInfo() {
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml = '';
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});

		$('#submit').click(function() {
					var shop = {};
					shop.shopName = $('#shop-name').val();
					shop.shopAddr = $('#shop-addr').val();
					shop.phone = $('#shop-phone').val();
					shop.shopDesc = $('#shop-desc').val();
					shop.shopCategory = {
						shopCategoryId : $('#shop-category').find('option')
								.not(function() {
									return !this.selected;
								}).data('id')
					};
					shop.area = {
						areaId : $('#area').find('option').not(function() {
							return !this.selected;
						}).data('id')
					};
					var shopImg = $('#shop-img')[0].files[0];
					var formData = new FormData();
					formData.append('shopImg', shopImg);
					formData.append('shopStr', JSON.stringify(shop));
					var verifyCodeActual = $('#j_captcha').val();
					if (!verifyCodeActual) {
						$.toast('请输入验证码');
						return;
					}
					formData.append('verifyCodeActual', verifyCodeActual);
					$.ajax({
						url : (isEdit ? editShopUrl : registerShopUrl),
						type : 'POST',
						data : formData,
						contentType : false,
						processData : false,
						cache : false,
						success : function(data) {
							if (data.success) {
								$.toast('提交成功！');
							} else {
								$.toast('提交失败' + data.errMsg);
							}
							$('#captcha_img').click();
						}
					})
				});
	}
	/**
	 * 根据传给服务器的shopId获取店铺相关信息，以此作为更新店铺的页面信息
	 */
	function getShopInfo(shopId) {
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				var shop = data.shop;
				var oldImgHtml = '<li><div class="item-content">'
					+'<div class="item-inner">'
					+'<div class="item-title label">旧头像</div>'
					+'<div class="item-input">'
					+'<p style="font-size:12px;color:#c40">店铺旧主图像：</p>'
					+'<img alt="店铺头像" title="店铺头像" src='+shop.shopImg+' height="100" width="100">'
					+'</p>'
					+'<div>'
					+'</div></div>'
					+'</li>';
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				$('#ll').after(oldImgHtml);
				$('#shop-category').html(
						'<option data-id="' + shop.shopCategory.shopCategoryId + '" + "selected">'
								+ shop.shopCategory.shopCategoryName + '</option>'
				);
				var tempAreaHtml = '';
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#area').html(tempAreaHtml);
				$('#area').attr('data-id', shop.areaId);
				$('#shop-category').attr('disabled', 'disabled');

			}
		});
		$('#submit').click(function() {
			var shop = {};
			if(isEdit){
				shop.shopId = shopId;
			}
			shop.shopName = $('#shop-name').val();
			shop.shopAddr = $('#shop-addr').val();
			shop.phone = $('#shop-phone').val();
			shop.shopDesc = $('#shop-desc').val();
			shop.shopCategory = {
				shopCategoryId : $('#shop-category').find('option')
						.not(function() {
							return !this.selected;
						}).data('id')
			};
			shop.area = {
				areaId : $('#area').find('option').not(function() {
					return !this.selected;
				}).data('id')
			};
			var shopImg = $('#shop-img')[0].files[0];
			var formData = new FormData();
			formData.append('shopImg', shopImg);
			formData.append('shopStr', JSON.stringify(shop));
			var verifyCodeActual = $('#j_captcha').val();
			if (!verifyCodeActual) {
				$.toast('请输入验证码');
				return;
			}
			formData.append('verifyCodeActual', verifyCodeActual);
			$.ajax({
				url : (isEdit ? editShopUrl : registerShopUrl),
				type : 'POST',
				data : formData,
				contentType : false,
				processData : false,
				cache : false,
				success : function(data) {
					if (data.success) {
						$.toast('提交成功！');
						$('#captcha_img').click();
					} else {
						$.toast('提交失败' + data.errMsg);
						$('#captcha_img').click();
					}
					
				}
			})
		});
	}

})
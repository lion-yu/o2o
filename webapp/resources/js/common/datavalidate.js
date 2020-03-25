/**
 * 表单动态验证所有函数存放处 
 * 判断非法字符正则表达式/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im
 * 判断手机号码正则表达式/^1[34578]\d{9}$/
 */
function shopName() {
	var reg = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;
	var shopName = $('#shop-name').val();
	if (shopName == null || shopName == '') {
		$.toast('店铺名不能为空');
	}
	if (reg.test(shopName)) {
		$.toast('店铺名称非法，不能含有非法字符');
		$('#shop-name').val('');
		return false;
	} else
		return true;

}
function shopPhone() {
	var reg = /^1[34578]\d{9}$/;
	var shopPhone = $('#shop-phone').val();
	if (!reg.test(shopPhone)) {
		$.toast('手机号码格式错误');
		$('#shop-phone').val('');
		return false;
	} else
		return true;

}


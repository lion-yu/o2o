/**
 * 更新订单评价信息
 */
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
	
	var orderId = getQueryString('userProductMapId');
	var url = '/o2o/frontend/addcommentbyorderid?userProductMapId=' + orderId;

	$('#submit').click(function() {
		var comments =$('textarea').val();
		var formData = new FormData();
		formData.append('comments',comments);
		if (comments != '') {
			$.ajax({
				url : url,
				type : 'POST',
				data : formData,
				contentType : false,
				processData : false,
				cache : false,
				success : function(data) {
					if (data.success) {
						$.toast('评价成功！');
					} else {
						$.toast('评价失败:' + data.errMsg);
					}
				}
			});
		}else{
			$.toast('不能为空');
		}
	})
})
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
	var url = '/o2o/frontend/getcommentbyorderid?userProductMapId=' + orderId;
	$.getJSON(url, function(data){
		if(data.success){
			$('textarea').val(data.comments);
		}else{
			$.toast('评价信息初始化失败');
		}
	})
	
	$('#submit').click(function() {
		var url = '/o2o/frontend/changecommentbyorderid?userProductMapId=' + orderId;
		var comments =$('textarea').val();
		var formData = new FormData();
		formData.append('comments',comments);
		if(comments != ''){
			$.ajax({
				url : url,
				type : 'POST',
				data : formData,
				contentType : false,
				processData : false,
				cache : false,
				success : function(data) {
					if (data.success) {
						$.toast('更新成功！');
					} else {
						$.toast(data.errMsg);
					}
				}
			})
		}else{
			$.toast('评价信息不能为空');
		}
	})
})
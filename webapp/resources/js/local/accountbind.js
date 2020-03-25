$(function() {
	var bindUrl = '/o2o/local/bindlocalauth';
	//获取用户登录状态加用户类型方便做页面的跳转
	var getTypeUrl = '/o2o/local/getusertype';
	$.getJSON(getTypeUrl, function(data){
		if(!data.success){
			$.toast("请登录再操作");
			window.location.href = '/o2o/frontend/index';
		}
	});
	
	$('#submit').click(function() {
		var userName = $('#user-name').val();
		var password = $('#password').val();
		// 获取输入的验证码
		var verifyCodeActual = $('#j_captcha').val();
		if($('#user-name').val() == "" || $('#password').val() == ""){
			$.toast("用户名或密码不能为空");
			return;
		}
		if (!verifyCodeActual) {
			$.toast("请输入验证码");
			$('img').click();
			return;
		}
		// 访问后台，绑定账号
		$.ajax({
			url : bindUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCodeActual : verifyCodeActual
			},
			success : function(data) {
				if(data.success){
					$.toast('绑定成功');
					if(data.userType ==1){
						window.location.href = '/o2o/frontend/index';
					}else{
						window.location.href ='/o2o/shopadmin/shoplist';
					}
				}else{
					$.toast("绑定失败：" + data.errMsg);
					if(data.errMsg == "非法操作"){
						window.location.href = '/o2o/frontend/index';
					}
				}
			}
		});
	});
})
$(function(){
	var changeUrl = '/o2o/local/changelocalpwd';
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
		if($('#user-name').val() == "" || $('#password').val() == ""){
			$.toast("用户名或原密码不能为空");
		}
		//获取新密码
		var newPassword =$('#newPassword').val();
		//确认新密码
		var confirmPassword=$('#confirmPassword').val();
		if(newPassword != confirmPassword){
			$.toast("两次输入的新密码不一致");
			return;
		}
		//新密码不能和旧密码相同
		var password = $('#password').val();
		if(password == newPassword){
			$.toast("新密码不能和旧密码相同");
			return;
		}
		// 获取输入的验证码
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast("请输入验证码");
			$('img').click();
			return;
		}
		//添加表单数据
		var formData = new FormData();
		formData.append('userName',userName);
		formData.append('password',password);
		formData.append('newPassword',newPassword);
		formData.append('verifyCodeActual',verifyCodeActual);
		$.ajax({
			url : changeUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data){
				if(data.success){
					$.toast('修改成功');
					logOut();
					if(data.userType ==1){
						window.location.href = '/o2o/frontend/index';
					}else{
						window.location.href ='/o2o/shopadmin/shoplist';
					}
				}else{
					$.toast("修改失败：" + data.errMsg);
					$('img').click();
				}
			}
		});
	});
	
	$('#back').click(function(){
		window.location.href='/o2o/frontend/index';
	});
	
	function logOut(){
		$.ajax({
			url : "/o2o/local/logout",
			type : 'post',
			async : false,
			cache : false,
			dataType : 'json',
			error : function(data,error){
				alert(error);
			}
		});
	}
});
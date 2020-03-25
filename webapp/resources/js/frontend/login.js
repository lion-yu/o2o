$(function() {
	var shopId = getQueryString('shopId');
	var awardId = getQueryString('awardId');
	var loginUrl = '/o2o/frontend/logincheck?shopId=' + shopId + '&awardId=' + awardId;
	// 登录次数，累计登录三次失败之后自动弹出验证码要求输入
	var loginCount = 0;
	$('#submit').click(function() {
		var userName = $('#user-name').val();
		var password = $('#password').val();
		if ($('#user-name').val() == "" || $('#password').val() == "") {
			$.toast("用户名或密码不能为空");
			$('#user-name').val("");
			$('#password').val("");
			return;
		}
		// 获取输入的验证码
		var verifyCodeActual = $('#j_captcha').val();
		// 是否需要验证码验证，默认为false
		var needVerify = false;
		if (loginCount >= 3) {
			if (!verifyCodeActual) {
				$.toast("请输入验证码");
				$('img').click();
				return;
			} else {
				needVerify = true;
			}
		}
		// 访问后台进行登录验证
		$.ajax({
			url : loginUrl,
			async : false,
			cache : false,
			type : 'post',
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCodeActual : verifyCodeActual,
				// 是否需要做验证码校验
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					window.location.href = '/o2o/frontend/noneed';
					setTimeOut(function(){
						window.location.href = '/o2o/frontend/success';
					}, 10000);
				}else {
					$.toast("登录失败:" + data.errMsg);
					loginCount++;
				}
				if (loginCount >= 3) {
					$('#verifyPart').show();
				}
				
			}
		});
	});
});
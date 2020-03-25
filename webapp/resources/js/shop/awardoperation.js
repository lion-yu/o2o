$(function(){
	// 获取用户登录状态加用户类型方便做页面的跳转
	var getTypeUrl = '/o2o/local/getusertype';
	$.getJSON(getTypeUrl, function(data){
		if(data.success){
			if(data.userType == 1){
				$.toast("权限不足");
				window.location.href = '/o2o/frontend/index';
			}
		}else{
			$.toast("请登录再操作");
			window.location.href = '/o2o/frontend/index';
		}
	});
	var awardId = getQueryString("awardId");
	var isEdit = awardId ? true : false;
	var addAwardUrl = '/o2o/shopadmin/addaward';
	var awardInfoUrl = '/o2o/shopadmin/getawardinfo?awardId=' + awardId;
	var editAwardUrl = '/o2o/shopadmin/modifyaward';
	if (isEdit) {
		getAwardInfo();
	} else{
		$('.unnecessary').css('display','none');
	}
	function getAwardInfo(){
		$.getJSON(awardInfoUrl, function(data){
			if(data.success){
				$('#award-name').text(data.award.awardName);
				$('#point').text(data.award.point);
				$('#award-desc').text(data.award.awardDesc);
				$('#old-picture').attr('src',data.award.awardImg);
			}
		});
	}
	
	$('#submit').click(function() {
		var award = {};
		award.awardName = $('#award-name').val();
		award.awardDesc = $('#award-desc').val();
		award.point = $('#point').val();
		award.awardId = awardId;
		var awardImg = $('#award-img')[0].files[0];
		var formData = new FormData();
		formData.append('awardImg', awardImg);
		formData.append('awardStr', JSON.stringify(award));
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码');
			return;
		}
		formData.append('verifyCodeActual', verifyCodeActual);
		$.ajax({
			url : (isEdit ? editAwardUrl : addAwardUrl),
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
				$('img').click();
			}
		})
	});
	
	
	
	
	
	
})
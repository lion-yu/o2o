$(function() {
	// 获取用户登录状态加用户类型方便做页面的跳转
	var getTypeUrl = '/o2o/local/getusertype';
	$.getJSON(getTypeUrl, function(data){
		if(data.success){
			if(data.userType == 1){
				$.toast("权限不足");
				window.location.href = '/o2o/frontend/index';
			}else{
				getlist();
				getHours();
			}
		}else{
			$.toast("请登录再操作");
			window.location.href = '/o2o/frontend/index';
		}
	});
	
	function getlist(e) {
	$.ajax({
			url : "/o2o/shopadmin/getshoplist",
			type : "get",
			dataType : "json",
			success : function(data) {
			if (data.success) {
				handleList(data.shopList);
				handleUser(data.user);
			} else {
						if (data.redirect) {
						$.toast(data.errMsg);
						window.location.href = '/o2o/local/login';
						}
			 }
		   }
		});
	}

	

	function getHours() {
		var date = new Date();
		var hour = date.getHours();
		if (hour > 0 && hour <= 5) {
			$('#currentHour').text('😡深夜了,请注意休息！');
		} else if (hour > 5 && hour <= 11) {
			$('#currentHour').text('😀又是愉快的一天');
		} else if (hour > 11 && hour <= 17) {
			$('#currentHour').text('☺下午啦，午休一下吧');
		} else {
			$('#currentHour').text('👀元气满满的新一天马上来临！');
		}
	}

	function handleUser(data) {
		$('#user-profileimg').attr('src', data.profileImg);
		$('#user-name').text(data.name);
		var time = new Date(data.createTime).Format("yyyy-MM-dd")
		$('#create-time').text("生日:" + time);
	}

	function handleList(data) {
		var html = '';
		if(data.length == 0){
			$('.shop-wrap').html('<p align="center">您还没有开设店铺哦</p>');
		}else{
		data.map(function(item, index) {
			html += '<div class="row row-shop"><div class="col-40">'
					+ item.shopName + '</div><div class="col-40">'
					+ shopStatus(item.enableStatus)
					+ '</div><div class="col-20">'
					+ goShop(item.enableStatus, item.shopId) + '</div></div>';
		});
		$('.shop-wrap').html(html);
		}
	}

	function shopStatus(status) {
		if (status == 0) {
			return '审核中';
		} else if (status == -1) {
			return '店铺非法';
		} else {
			return '审核通过';
		}
	}

	function goShop(status, id) {
		if (status == 1) {
			return '<a href="/o2o/shopadmin/shopmanagement?shopId=' + id
					+ '"  external>进入</a>';
		} else {
			return '';
		}
	}

	$('#bind').click(function() {
		$.ajax({
			url : "/o2o/shopadmin/getshopusertype",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					window.location.href = '/o2o/local/accountbind';
				} else {
					window.location.href = '/o2o/local/login';
				}
			}
		});
	});
	$('#bindAndChange').click(function() {
		$.ajax({
			url : "/o2o/shopadmin/getshopusertype",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					window.location.href = '/o2o/local/changepsw';
				} else {
					window.location.href = '/o2o/local/login';
				}
			}
		});
	});
});

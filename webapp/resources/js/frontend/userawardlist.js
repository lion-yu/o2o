$(function(){
	var getStatusUrl = '/o2o/frontend/checkuserloginstatus';
	$.getJSON(getStatusUrl, function(data){
		if(data.success){
		}else{
			//用户未登录
			window.location.href = '/o2o/frontend/index';
		}
	});
	var maxItems = 999;
	var pageIndex = 1;
	var pageSize = 3;
	var awardName ='';
	//设定加载符
	var loading = false;
	//默认加载三条用户奖品信息
	addItems(pageIndex, pageSize);
	
	function addItems(index, size){
		var url = '/o2o/frontend/getuserawardlist?pageIndex=' + pageIndex +'&pageSize=' + pageSize + '&awardName=' + awardName;
		$.getJSON(url , function(data){
			if(data.success){
				//如果没有一条记录的话
				if(data.count == 0){
					$('.list-div').text('没有兑换记录哦');
					return;
				}
				maxItems = data.count;
				var html = '';
				data.userAwardMapList.map(function(item, index){
					var state = (item.usedStatus==0) ? '未兑换' : '已兑换';
					html +='<div class="card" data-award-id="'+item.award.awardId+'" data-used-state="'+item.usedStatus+'">'
					+	'<div class="card-header">'+item.award.awardName+'</div>'
					+ '<div class="card-content"><div class="list-block media-list"><ul>'
					+ '<li class="item-content"><div class="item-media">'
					+'<img src="'+item.award.awardImg+'" width="100"></div>'
					+' <div class="item-inner"><div class="item-title-row">'
					+'<div class="item-title">'+item.award.awardDesc+'</div>'
					+'</div><div class="item-subtitle">消耗:'+item.point+'积分</div></div></li><ul></div></div>'
					+'<div class="card-footer">'
					+'<span>'+new Date(item.createTime).Format("yyyy-MM-dd hh:mm:ss")+'</span>'
					+'<span>' + state + '</span>'
					+'</div>'
					+'</div> '
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					// 隐藏提示符
					$('.infinite-scroll-preloader').hide();
				} else {
					$('.infinite-scroll-preloader').show();
				}
				// 否则页码加1，继续load
				pageIndex += 1;
				// 加载结束，可以再次加载了
				loading = false;
				// 刷新页面，显示新加载的店铺
				$.refreshScroller();
			}
		});
	}
	
	// 下滑屏幕自动进行分页搜索
	$(document).on('infinite', '.infinite-scroll-bottom', function(e) {
		if (loading)
			return;
		addItems(pageIndex, pageSize);
	});
	
	// 需要查询的奖品名字发生变化后，重置页码，清空原先的店铺列表，按照新的名字去查询
	$('#search').on('change', function(e) {
		awardName = e.target.value;
		$('.list-div').empty();
		pageIndex = 1;
		addItems(pageIndex, pageSize);
	});
	
	$('.list-div').on('click', '.card',function(e){
		var state = e.currentTarget.dataset.usedState;
		if(state == 0){
			var awardId = e.currentTarget.dataset.awardId;
			window.location.href='/o2o/frontend/userawarddetail';
		}
	})
	
	$.init();
})
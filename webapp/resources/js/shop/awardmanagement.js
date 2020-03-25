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
	var loading = false;
	var maxItems = 999;
	var awardListUrl = '/o2o/shopadmin/getawardlistbyshopid';
	var pageIndex= 1;
	var pageSize = 4;
	var awardName ='';
	// 默认先加载四条
	addItems(pageIndex, pageSize);
	/**
	 * 获取分页展示的奖品列表信息
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @returns
	 */
	function addItems(index, size) {
		// 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
		var url = awardListUrl + '?' + 'pageIndex=' + index + '&pageSize='
				+ size+ '&awardName=' + awardName;
		// 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
		loading = true;
		// 访问后台获取相应查询条件下的店铺列表
		$.getJSON(url, function(data) {
			if (data.success) {
				// 获取当前查询条件下店铺的总数
				maxItems = data.count;
				var status = '';
				var tempHtml = '';
					data.awardList.map(function(item, index){
						if(item.enableStatus == 0){
							status = '上架';
						}else{
							status = '下架';
						}
						tempHtml +='<div class="card" data-award-id="'+item.awardId+'">'
						+	'<div class="card-header">'+item.awardName+'</div>'
						+ '<div class="card-content"><div class="list-block media-list"><ul>'
						+ '<li class="item-content" data-award-id="'+item.awardId+'"><div class="item-media">'
						+'<img src="'+item.awardImg+'" width="100"></div>'
						+' <div class="item-inner"><div class="item-title-row">'
						+'<div class="item-title">'+item.awardDesc+'</div>'
						+'</div><div class="item-subtitle">'+item.point+'</div></div></li><ul></div></div>'
						+'<div class="card-footer">'
						+'<span>'+new Date(item.lastEditTime).Format("yyyy-MM-dd")+'更新</span>'
						+'<a class="status" data-status="'+item.enableStatus+'" data-award-id="'+item.awardId+'">'
						+status +'</a></div>'
						+'</div> '
					});
				$('.award-list').html(tempHtml);
				// 获取目前为止已显示的卡片总数，包含之前已经加载的
				var total = $('.list-div .card').length;
				// 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
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


	
	$('.award-list').on('click','.item-content',function(e){
		var awardId = e.currentTarget.dataset.awardId;
		window.location.href = '/o2o/shopadmin/awardoperation?awardId=' + awardId;
	});
	
	$('.award-list').on('click','a',function(e){
		var status = e.currentTarget.dataset.status;
		var awardId = e.currentTarget.dataset.awardId;
		var modifyUrl = '/o2o/shopadmin/modifyaward?awardId=' + awardId + '&enableStatus=' +status;
		$.confirm('确定修改吗', function(){
			$.getJSON(modifyUrl, function(data){
				if(data.success){
					$.toast("操作成功");
					location.reload(); 
				}else{
					$.toast(data.errMsg);
				}
			});
		});
		
	});
	$.init();
})
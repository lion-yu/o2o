/**
 * 
 */
$(function(){
	//获取用户登录状态加用户类型方便做页面的跳转
	var getTypeUrl = '/o2o/shopadmin/getshopusertype';
	$.getJSON(getTypeUrl, function(data){
		if(data.success){
			if(data.userType == 1){
				$.toast("请登录商家账号进行操作");
				window.location.href = '/o2o/frontend/index';
			}
		}else{
			//用户未登录
			window.location.href = '/o2o/frontend/index';
		}
	});
	
	var listUrl = '/o2o/shopadmin/getproductcategorylist';
	var addUrl = '/o2o/shopadmin/addproductcategorys';
	var deleteUrl = '/o2o/shopadmin/removeproductcategory';
	getList();
	function getList(){
		$.getJSON(listUrl,function(data){
			if(data.success){
				var dataList = data.data;
				$('.catagory-wrap').html('');
				var tempHtml = '';
				dataList.map(function(item,index){
					tempHtml += ''
						+ '<div class="row row-product-category now">'
						+ '<div class="col-33 product-category-name">'
						+ item.productCategoryName
						+ '</div>'
						+ '<div class="col-33">'
						+ item.priority
						+ '</div>'
						+ '<div class="col-18"><a href="#" class="button delete" data-id="'
						+ item.productCategoryId
						+ '">删除</a></div>'
						+ '</div>';
				});
				$('.category-wrap').append(tempHtml);
			}
		})
	}
	
	$('#new').click(function(){
		var tempHtml = '<div class="row row-product-category temp">'
			+ '<div class="col-33"><input class="category-input category" type="text" placeholder="商品种类"></div>'
			+ '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
			+ '<div class="col-18"><a href="#" class="button delete">删除</a></div>'
			+ '</div>'
			$('.category-wrap').append(tempHtml);
	});
	
	$('.category-wrap').on('click','.row-product-category.temp .delete', function(e){
		$(this).parent().parent().remove();
		$.toast('删除成功！');
	});
	
	$('.category-wrap').on('click', '.row-product-category.now .delete', function(e){
		var target = e.currentTarget;
		$.confirm('你确定么？',  function(){
			$.ajax({
				url : deleteUrl,
				type : 'post',
				data : {productCategoryId : target.dataset.id},
				dataType : 'json',
				success : function(data){
					if(data.success){
						$.toast('删除成功！');
						$('.category-wrap').empty();
						getList();
					}else{
						$.toast('删除失败！');
					}
				}
			});
		});
	});
	
	$('#submit').click(function(){
		var tempArr = $('.temp');
		var productCategoryList = [];
		tempArr.map(function(index, item){
			var tempObj = {};
			tempObj.productCategoryName = $(item).find('.category').val();
			tempObj.priority= $(item).find('.priority').val();
			if(tempObj.productCategoryName && tempObj.priority){
				productCategoryList.push(tempObj);
			}
		});
		$.ajax({
		url : addUrl,
		type : 'post',
		data : JSON.stringify(productCategoryList),
		contentType : 'application/json',
		success : function(data){
			if(data.success){
				$.toast('提交成功');
				$('.category-wrap').empty();
				getList();
			}else{
				$.toast('提交失败');
			}
		}
	});
})
	
	
	
	
	
	
	
})

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
	
	var productListUrl = '/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999';
	//商品下架url
	var statusUrl ='/o2o/shopadmin/modifyproduct';
	getlist();
//	function getlist(e){
//		$.ajax({
//			url : getProductList,
//			type : 'get',
//			dataType : 'json',
//			success : function(data){
//				if(data.success){
//					handleProductList(data);
//				}
//			
//			}
//		});
//	}
	function getlist(){
		//从后台获取此店铺的商品列表
		$.getJSON(productListUrl, function(data){
			if(data.success){
				var productList = data.productList;
				var tempHtml = '';
				//遍历每条商品信息，拼接一行显示
				//商品名称，优先级，上架/下架，编辑按钮
				//预览
				productList.map(function(item, index){
					var textOp = "下架";
					var contraryStatus = 0;
					if(item.enableStatus == 0){
						textOp='上架';
						contraryStatus = '1';
					}
					//拼接每件商品的行信息
					tempHtml +='' +'<div class="row row-product">'
					+'<div class="col-80">'
					+item.productName+'</div>'
					+ '<div class="col-30">'
					+item.priority+'</div>'
					+ '<div class="col-100">'
					+ '<a href="#" class="edit" data-id="'
					+ item.productId
					+ '" data-status="'
					+ item.enableStatus
					+ '">编辑</a>'
					+ '<a href="#" class="status" data-id="'
					+ item.productId
					+ '" data-status="'
					+ contraryStatus
					+ '">'
					+ textOp
					+ '</a>'
					+ '<a href="#" class="preview" data-id="'
					+ item.productId
					+ '" data-status="'
					+ item.enableStatus
					+ '">预览</a>'
					+ '</div>'
					+ '</div>';
				});
				$('.product-wrap').html(tempHtml);
			}
		})
	}
	//将class为product-wrap里面的a标签绑定上点击的事件
	$('.product-wrap').on('click','a',function(e){
		var target = $(e.currentTarget);
		if(target.hasClass('edit')){
			//如果有class edit则进入店铺信息编页面，并带有productId参数
			window.location.href = '/o2o/shopadmin/productoperation?productId='
				+ e.currentTarget.dataset.id;
		}else if(target.hasClass('status')){
			//如果有class status则调用后台功能上下架上/下架商品页面，并带有productId参数
			changeItemStatus(e.currentTarget.dataset.id,e.currentTarget.dataset.status);
		}else if(target.hasClass('preview')){
			//如果有class preview则去前台展示系统显示商品情况
			window.location.href='/o2o/frontend/productdetail?productId=' 
				+ e.currentTarget.dataset.id;
		}
	});
	
	function changeItemStatus(id, enableStatus){
		//定义product json对象并添加productId以及状态（上架/下架）
		var product = {};
		product.productId = id;
		product.enableStatus = enableStatus;
		$.confirm('确定吗', function(){
			//上下架
			$.ajax({
				url : statusUrl,
				type : 'POST',
				data : {
					productStr : JSON.stringify(product),
					statusChange : true
				},
				dataType : 'json',
				success : function(data){
					if(data.success){
						$.toast("操作成功")
						getlist();
					}else{
						$.toast('操作失败');
					}
				}
			})
		});
	}
	
	
	
	
})
$(function(){
	// 获取用户登录状态加用户类型方便做页面的跳转
	var getTypeUrl = '/o2o/shopadmin/getshopusertype';
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
	var productName = '';
	getProductSellDailyList();
	getList();
	function getList(){
		//获取用户购买信息
		var listUrl = '/o2o/shopadmin/listuserproductmapsbyshop?pageIndex=1&pageSize=999&productName=' +productName;
		//访问后台，获取该店铺购买信息列表
		$.getJSON(listUrl, function(data){
			if(data.success){
				var userProductMapList = data.userProductMapList;
				var tempHtml = '';
				//遍历购买信息列表，拼接出列信息
				userProductMapList.map(function(item, index){
					tempHtml += ''+'<div class="row row-productbuycheck">'
					+'<div class="col-20">'+item.product.productName
					+'</div>'
					+'<div class="col-25 productbuycheck-time">'
					+new Date(item.createTime).Format("yyyy-MM-dd hh:mm:ss")
					+'</div>'+'<div class="col-20">'
					+item.user.name + '</div>'
					+'<div class="col-10">' + item.point + '</div>'
					+'<div class="col-20">' + item.operator.name + '</div>'
					+'</div>'
				});
				$('.productbuycheck-wrap').html(tempHtml);
			}
		});
	}
	
	$('#search').on('change',function(e){
		productName = e.target.value;
		$('.productbuycheck-wrap').empty();
		//再次加载
		getList();
	});
	/**
	 * 获取7天的销量
	 */
	function getProductSellDailyList(){
		//获取该店铺商品7天销量的url
		var listProductSellDailyUrl = '/o2o/shopadmin/listprodctselldailyinfobyshop';
		$.getJSON(listProductSellDailyUrl, function(data){
			if(data.success){
				var myChart = echarts.init(document.getElementById('chart'));
				//生成静态的Echart信息的部分
				var option = generateStaticEchartPart();
				//遍历销售统计列表，动态设定echarts的值
				option.legend.data = data.legendData;
				option.xAxis.data = data.xAxis;
				option.series.data = data.series;
				myChart.setOption(option);
			}
		});
	}
	function  generateStaticEchartPart(){
		var option = {
				//提示框，鼠标悬浮交互时的信息提示
				tooltip : {
					trigger : 'axis',
					axisPoint : {//坐标轴指示器，坐标轴触发有效
						type : 'shadow'//默认为直线，可选为line|shadow
					}
				},
				//图例,每个图表最多仅有一个图例
				legend : {
					//图例中内容数组，数组项通常为{string}，每一项代表一个系列的name
					data : ['墨香奶茶','珍珠奶茶','冰雪奇缘']
				},
				//直角坐标系内绘图网格
				grid : {
					left : '3%',
					right : '4%',
					bottom : '3%',
					containLabel : true
				},
				//直角坐标系中横轴数组，数组中的每一项代表i一条横轴坐标轴
				xAxis : [{
					//类目型：需要指定类目列表，坐标轴内有且仅有这些指定类目的坐标
					type : 'category',
					data : ['周一','周二','周三','周四','周五','周六','周日']
				}],
				//直角坐标系纵轴数组，每一项代表一条纵轴坐标轴
				yAxis : [{
					type : 'value'
				}],
				//驱动图表生成的数据内容数组，数组中每一项为一个系列的选项和数据
				series : [{
					name : '墨香奶茶',
					type : 'bar',
					data : [120,132,101,134,290,230,220]
				},{
					name : '珍珠奶茶',
					type : 'bar',
					data : [60,72,71,74,190,130,110]
				},{
					name : '冰雪奇缘',
					type : 'bar',
					data : [62,82,91,84,190,110,120]
				}]
		};
		return option;
	}
//	echarts逻辑部分
//	var myChart = echarts.init(document.getElementById('chart'));
//	var option = {
//			//提示框，鼠标悬浮交互时的信息提示
//			tooltip : {
//				trigger : 'axis',
//				axisPoint : {//坐标轴指示器，坐标轴触发有效
//					type : 'shadow'//默认为直线，可选为line|shadow
//				}
//			},
//			//图例,每个图表最多仅有一个图例
//			legend : {
//				//图例中内容数组，数组项通常为{string}，每一项代表一个系列的name
//				data : ['墨香奶茶','珍珠奶茶','冰雪奇缘']
//			},
//			//直角坐标系内绘图网格
//			grid : {
//				left : '3%',
//				right : '4%',
//				bottom : '3%',
//				containLabel : true
//			},
//			//直角坐标系中横轴数组，数组中的每一项代表i一条横轴坐标轴
//			xAxis : [{
//				//类目型：需要指定类目列表，坐标轴内有且仅有这些指定类目的坐标
//				type : 'category',
//				data : ['周一','周二','周三','周四','周五','周六','周日']
//			}],
//			//直角坐标系纵轴数组，每一项代表一条纵轴坐标轴
//			yAxis : [{
//				type : 'value'
//			}],
//			//驱动图表生成的数据内容数组，数组中每一项为一个系列的选项和数据
//			series : [{
//				name : '墨香奶茶',
//				type : 'bar',
//				data : [120,132,101,134,290,230,220]
//			},{
//				name : '珍珠奶茶',
//				type : 'bar',
//				data : [60,72,71,74,190,130,110]
//			},{
//				name : '冰雪奇缘',
//				type : 'bar',
//				data : [62,82,91,84,190,110,120]
//			}]
//	};
//	myChart.setOption(option);
})
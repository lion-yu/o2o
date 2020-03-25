//该段代码为什么执行不了？
$(function(){
	var total = 60;
	function timeDown() {
		if(total == 0){
			window.location.href = '/o2o/frontend/index';
		}else{
			total = total - 1;
		}
		$('#time').html('还有<b style="color:red;font-size:5px">'+total +'</b>秒退出系统👨‍💻');
	}
	window.setInterval("timeDown()",1000);
})
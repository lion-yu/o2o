function changeVerifyCode(img) {
	img.src="../Kaptcha?" + Math.floor(Math.random()*100);
}

function getQueryString(name){
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null){
		return decodeURIComponent(r[2]);
	}
	return '';
}

Date.prototype.Format=function(fmt){
	var o = {
			"M+" : this.getMonth() + 1,
			"d+" : this.getDate(),//日
			"h+" : this.getHours(),//小时
			"m+" : this.getMinutes(),//分
			"s+" : this.getSeconds(),//秒
			"q+" : Math.floor((this.getMonth()+3)/3),//季度
			"S+" : this.getMilliseconds()//毫秒
	};
	if(/(y+)/.test(fmt)){
		fmt = fmt.replace(RegExp.$1,(this.getFullYear() + "").substr(4-RegExp.$1.length));
		for(var k in o)
			if(new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1,(RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
}


function pop(){
	if(sessionStorage.getItem("pop")){
		
	}else{
		sessionStorage.setItem("pop",true);
		$('#pop').click();
		var myaudio = document.getElementById('myaudio');
		myaudio.play();
	}
	
}

function getDeviceWidth(){
	var width = screen.width;
	return width;
}

function getDeviceHeight(){
	var height = screen.height;
	return height;
}
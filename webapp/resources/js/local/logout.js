$(function(){
	logOut();
	function logOut(){
		$.ajax({
			url : "/o2o/local/logout",
			type : 'post',
			async : false,
			cache : false,
			dataType : 'json',
			error : function(data,error){
				alert(error);
			}
		});
	}
});
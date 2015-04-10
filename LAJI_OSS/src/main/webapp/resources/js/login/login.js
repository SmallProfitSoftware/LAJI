var userModal, pwdModal;
var c_userId = "c_userId";
$(document).ready(function(){
	//从cookie中获取用户名
	if ($.cookie(c_userId)) {
		setVal("userId", $.cookie(c_userId));
	}
	$('.loginbox').css({
		'position' : 'absolute',
		'left' : ($(window).width() - 692) / 2
	});
	$(window).resize(function() {
		$('.loginbox').css({
			'position' : 'absolute',
			'left' : ($(window).width() - 692) / 2
		});
	});
	userModal = tooltip("userId", $('#userId'), "right", "center");
	pwdModal = tooltip("userPwd", $('#userPwd'), "right", "center");
	//messageModal = 
	//回车监听事件
	$("input").keyup(function(event){
	  if(event.keyCode == 13){
		  doLogin();
	  }
	});
	$("#loginbtn").click(function(){
		doLogin();
	});
});

/**
 * 登录
 */
function doLogin() {
	var userId = $("#userId").val();
	var userPwd = $("#userPwd").val();
	if (isEmpty(userId)) {
		userModal.setContent('用户名不能为空！');
		userModal.open();
		return;
	}
	if (isEmpty(userPwd)) {
		pwdModal.setContent('密码不能为空！');
		pwdModal.open();
		return;
	}
	$("#loginbtn").attr('disabled', true);
	userModal.close();
	pwdModal.close();
	var params = {};
	params["userId"] = userId;
	params["userPwd"] = userPwd;
	$.ajax({
		type : "POST",
		url : "/views/system/login",
		async : true,
		data : params,
		complete : function(response) {
			var data = eval("(" + response.responseText + ")");
			if (data.retCode != 200) {
				notice(data.message);
				$("#loginbtn").attr('disabled', false);
			} else {
				//设置到cookie中，7天有效期
				if ($("#remBtn").attr("checked"))
					setCookie(c_userId, userId, 7);
				window.location.href = "/views/system/main.html";
			}
		}
	});
}

/**
 * 显示信息
 * @param message
 */
function notice(message) {
	new jBox('Notice', {
        content: message,
        autoClose: 3000,
        theme:'jBox-NoticeBorder',
        addClass:'jBox-Notice-yellow',
        attributes: {
            x: 'left',
            y: 'top'
        },
        position: {
            x: $(window).width() / 2,
            y: 50
        }
    });
}
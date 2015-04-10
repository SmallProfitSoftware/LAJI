/**
 * 修改密码
 */
function changePwd() {
	var oldPwd = $("#oldPwd").val();
	var newPwd = $("#newPwd").val();
	var rePwd = $("#rePwd").val();
	var isError = false;
	
	var oT = "", nT = "", rT = "";
	if (isEmpty(oldPwd)) {
		oT = "请输入旧密码";
		isError = true;
	} else {
		var data = check(oldPwd);
		if (data.retCode != 200) {
			oT = "密码错误";
			isError = true;
		}
	}
	$("#oldPwd").next().css("color", "red").html(oT);
	if (isEmpty(newPwd)) {
		nT = "请输入新密码";
		isError = true;
	} else if (eq(oldPwd, newPwd)) {
		nT = "新密码不能与旧密码一致";
		isError = true;
	} else if (!lenOut(newPwd, 6)) {
		nT = "密码长度不能少于6位";
		isError = true;
	}
	$("#newPwd").next().css("color", "red").html(nT);
	if (isEmpty(rePwd)) {
		rT = "请重复输入新密码";
		isError = true;
	}
	$("#rePwd").next().css("color", "red").html(rT);
	if (!isEmpty(newPwd) && !isEmpty(rePwd) && !eq(newPwd, rePwd)) {
		$("#rePwd").next().css("color", "red").html("两次输入的密码不一致");
		isError = true;
	}
	if (isError)
		return;
	$("#rePwd").next().css("color", "red").html("");
	//修改
	change(oldPwd, newPwd);
}

/**
 * 密码校验
 * @param pwd
 */
function check(pwd) {
	var data;
	$.ajax({
		type : "POST",
		url : WEB_URL + "/views/system/user_pwd_check.json",
		async : false,
		data : {"userPwd" : pwd},
		complete : function(response) {
			data = eval("(" + response.responseText + ")");
		}
	});
	return data;
}

/**
 * 修改
 * @param pwd
 * @param newPwd
 */
function change(pwd, newPwd){
	$.ajax({
		type : "POST",
		url : WEB_URL + "/views/system/user_pwd_edit.json",
		async : true,
		data : {"userPwd" : pwd, "newPwd" : newPwd},
		complete : function(response) {
			var data = eval("(" + response.responseText + ")");
			closeBox("p_d");
			if (data.retCode != 200) {
				error(data.message);
			} else {
				var throughBox = art.dialog.through;
				throughBox({
					id : "c_d",
					title : "提示",
					content : "密码修改成功，请重新登陆！",
					ok : function(){
						window.location.href = "/views/system/logout";
					},
					close : function(){
						window.location.href = "/views/system/logout";
					}
				});
			}
		}
	});
}
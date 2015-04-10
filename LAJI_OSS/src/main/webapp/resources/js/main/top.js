$(document).ready(function(){
	$("#changePwd").click(dialog);
	 setInterval("currTime($('#currTimes'))", 1000);  
	 //退出
	 $("#exitBtn").click(exit);
});

function dialog() {
	var div = $("<div></div>");
	div.addClass("formbody");
	var ul = $("<ul></ul>");
	ul.addClass("forminfo");
	var oli = $("<li><label>旧密码<b>*</b></label><input type='password' id='oldPwd' class='dfinput' style='width: 200px;' title='请输入旧密码！' placeholder='请输入旧密码！' maxlength='32' /><i>不能超过32个字符</i></li>");
	var nli = $("<li><label>新密码<b>*</b></label><input type='password' id='newPwd' class='dfinput' style='width: 200px;' title='请输入新密码！' placeholder='请输入新密码！' maxlength='32' /><i>不能超过32个字符</i></li>");
	var rli = $("<li><label>旧密码<b>*</b></label><input type='password' id='rePwd' class='dfinput' style='width: 200px;' title='请重复输入新密码！' placeholder='请重复输入新密码！' maxlength='32' /><i>不能超过32个字符</i></li>");
	var bli = $("<li><label>&nbsp;</label></li>");
	var oBtn = $("<input type='button' onclick='changePwd();' class='btn' value='修改' style='width: 96px;'/>");
	var cBtn = $("<input type='button' onclick=\"closeBox('p_d');\" class='cancel' value='取消' style='margin-left: 10px;'>");
	bli.append(oBtn).append(cBtn);
	ul.append(oli).append(nli).append(rli).append(bli);
	div.append(ul);
	var params = {};
	params["id"] = "p_d";
	params["title"] = "修改密码";
	params["content"] = div.andSelf().html();
	var throughBox = art.dialog.through;
	throughBox(params);
}

/**
 * 退出
 */
function exit() {
	var throughBox = art.dialog.through;
	throughBox({
		id : "e_d",
		title : "退出提示",
		content : "您确定要退出吗？",
		lock: true,
		ok : function(){
			parent.window.location.href = WEB_URL + "/views/system/logout";
		},
		cancel: true
	});
}

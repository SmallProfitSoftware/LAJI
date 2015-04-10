var grid;
var id;
$(document).ready(function() {
	//列表
	list();
	//下拉框
	$("select").select2({
		width : 100
	});
	$("select[name!=role]").change(function(){
		search();
	});
	$("#usual1 ul").idTabs(); 
	$("#operateBtn").click(operator);
	//查询
	$("#searchBtn").click(function(){
		search();
	});
});

/**
 * 列表
 */
function list() {
	grid = grid("user_tab", WEB_URL + "/views/system/user_list.json", null, "pageTarget", "tabCallBack");
}

/**
 * 查询
 * @param params
 */
function search(params) {
	if (!params)
		params = {};
	var content = $("#content").val();
	var userfield = $("#userfield").select2("val");
	var role = $("#roles").select2("val");
	params["userfield"] = userfield;
	params["content"] = content;
	params["role"] = role;
	//从第一页开始搜索
	grid.setCurrPage(1);
	grid.reload(params);
} 

/**
 * 账号
 * @param tr
 * @param index
 * @param record
 * @param field
 */
function userTD(tr, index, record, field) {
	if (isTrue(record.isLock))
		return "<span class='td-icon-lock'></span>" + record.userId;
	return "<span style='margin-left:10px;'>" + record.userId + "</span>";
}

/**
 * 操作
 */
function operateTD(tr, index, record, field) {
	if (isSuper(record.role))
		return "";
	var span = $("$<span></span>");
	var viewBtn = $("#viewBtn");
	var lockBtn = $("#lockBtn");
	var resetBtn = $("#resetBtn");
	if(viewBtn) { //查看/修改
		var cloneView = $(viewBtn).clone(false);
		cloneView.click(function(){
			view(record);
		});
		span.append(cloneView);
	}
	if(lockBtn) {//锁定/解锁
		var cloneLock = $(lockBtn).clone(false);
		cloneLock.html("锁定");
		if (isTrue(record.isLock))
			cloneLock.html("解锁");
		cloneLock.click(function(){
			doLock(record);
		});
		span.append(cloneLock);
	}
	if(resetBtn) { //密码重置
		var cloneReset = $(resetBtn).clone(false);
		cloneReset.click(function(){
			reset(record);
		});
		span.append(cloneReset);
	}
	return span;
}

/**
 * 查看
 * @param record
 */
function view(record) {
	id = record.id;
	$("#userId").val(record.userId);
	$("#userId").attr("readonly", true);
	$("#userName").val(record.userName);
	$("#operateBtn").val("保存");
	var u_s = $("#u_s");
	if (u_s) {
		$("#operateBtn").show();
	} else {
		$("#operateBtn").hide();
	}
	$("a[href=#tab2]").click();
}

/**
 * 密码重置
 * @param record
 */
function reset(record) {
	art.dialog({
	    content: '确定要重置帐号[' + record.userId + "]的密码？",
	    ok: function () {
	    	$.ajax({
	    		type : "POST",
	    		url : WEB_URL + "/views/system/user_pwd_reset.json",
	    		async : true,
	    		data : {"userId" : record.userId},
	    		complete : function(response) {
	    			var data = eval("(" + response.responseText + ")");
	    			if (data.retCode != 200) {
	    				error(data.message);
	    			} else {
	    				search(null);
	    				success(MSG_SUCCESS_RESET + "</br>帐号[" + record.userId + "]的新初始密码为：" + data.message);
	    			}
	    		}
	    	});
	    },
	    cancel: true
	});
}

/**
 * 操作
 */
function operator() {
	var userId = $("#userId").val();
	var userName = $("#userName").val();
	var role = $("#role").select2("val");
	if (isEmpty(userId)){
		warnning("帐号ID不能为空");
		return;
	} else if (!isPattern(/^[a-zA-Z0-9]+$/, userId)){
		warnning("帐号ID只能是数字或者字母");
		return;
	}
	if (isEmpty(userName)){
		warnning("帐号名称不能为空");
		return;
	}
	var params = {};
	params["userId"] = userId;
	params["userName"] = userName;
	params["role"] = role;
	var _url = WEB_URL + "/views/system/user_add.json";
	var isUpdate = false;
	if (!isEmpty(id)) {
		params["id"] = id;
		_url = WEB_URL + "/views/system/user_edit.json";
		isUpdate = true;
	}
	$.ajax({
		type : "POST",
		url : _url,
		async : true,
		data : params,
		complete : function(response) {
			var data = eval("(" + response.responseText + ")");
			if (data.retCode != 200) {
				error(data.message);
			} else {
				clear();
				if (isUpdate) {
					success(MSG_SUCCESS_UPDATE);
				} else {
					success(MSG_SUCCESS_ADD + "</br>帐号[" + userId + "]的初始密码为：" + data.message);
				}
			}
		}
	});
}

/**
 * 锁定/解锁
 * @param record
 */
function doLock(record) {
	art.dialog({
	    content: '确定要锁定帐号[' + record.userId + "]？",
	    ok: function () {
	    	$.ajax({
	    		type : "POST",
	    		url : WEB_URL + "/views/system/user_lock.json",
	    		async : true,
	    		data : {"userId" : record.userId},
	    		complete : function(response) {
	    			var data = eval("(" + response.responseText + ")");
	    			if (data.retCode != 200) {
	    				error(data.message);
	    			} else {
	    				search(null);
	    				if (isTrue(record.isLock)) {
	    					success(MSG_SUCCESS_UNLOCK);
	    				} else {
	    					success(MSG_SUCCESS_LOCK);
	    				}
	    			}
	    		}
	    	});
	    },
	    cancel: true
	});
}

/**
 * 清除
 */
function clear() {
	$("#userId").val("");
	$("#userId").attr("readonly", false);
	$("#userName").val("");
	$("#operateBtn").val("添加");
	var a_s = $("#a_s");
	if (a_s) {
		$("#operateBtn").show();
	} else {
		$("#operateBtn").hide();
	}
	$("a[href=#tab1]").click();
	search(null);
}
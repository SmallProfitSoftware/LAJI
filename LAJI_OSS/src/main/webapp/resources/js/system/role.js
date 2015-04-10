var grid;
var id = null;
$(document).ready(function() {
	//列表
	list();
	$("#usual1 ul").idTabs(); 
	//新增/修改
	$("#operateBtn").click(operate);
	$("#addBtn").click(showAdd);
	$("#cancelBtn").click(function(){
		clear();
	});
	//权限设置-全选/全不选
	$("#auth_chk_all").click(function(){
		check($(this), $("input[type=checkbox]"));
	});
	//目录全选/全不选
	$("input[id^=dir_chk_]").click(function(){
		check($(this), $("input[id^=module_chk_" + $(this).attr("code") + "]"));
		check($(this), $("input[id^=func_chk_" + $(this).attr("code") + "]"));
		checkAll();
	});
	//模块全选/全不选
	$("input[id^=module_chk_]").click(function(){
		check($(this), $("input[id^=func_chk_" + $(this).attr("code") + "]"));
		var hasCheck = false;
		$("input[id^=module_chk_]").each(function(){
			if ($(this).attr("checked")) {
				hasCheck = true;
				return;
			}
		});
		$("input[id^=dir_chk_" + $(this).attr("code").substring(0, 2) + "]").attr("checked", hasCheck);
		checkAll();
	});
	//功能
	$("input[id^=func_chk_]").click(function(){
		if ($(this).attr("checked")) { //全选
			$("input[id^=module_chk_" + $(this).attr("code").substring(0, 4) + "]").attr("checked", true);
			$("input[id^=dir_chk_" + $(this).attr("code").substring(0, 2) + "]").attr("checked", true);
		}
		checkAll();
	});
	//双击展开/折叠
	$("span[id^=dir_span_]").dblclick(function(){
		fade($("#dir_" + $(this).attr("code")));
	});
	$("span[id^=module_span_]").dblclick(function(){
		fade($("#module_" + $(this).attr("code")));
	});
	//获取角色结果集
	getRoles();
	//获取角色权限
	$("#roles").change(function(){
		getAuths($("#roles").select2("val"));
	});
	//保存角色权限设置
	$("input[name=saveBtn]").click(save);
});

/**
 * 列表
 */
function list() {
	grid = grid("role_tab", WEB_URL + "/views/system/role_list.json", null, "pageTarget", "tabCallBack");
}

/**
 * 
 * @param tr
 * @param index
 * @param record
 * @param field
 */
function usersTD(tr, index, record, field) {
	var users = record.users;
	var contents = "";
	if (users != null) {
		for (var i = 0, j = users.length; i < j; i ++) {
			contents += users[i].userName + ";";
		}
	}
	return contents;
}

/**
 * 获取角色集合
 */
function getRoles() {
	$.ajax({
		type : "POST",
		url : WEB_URL + "/views/system/roles.json",
		async : true,
		data : null,
		complete : function(response) {
			var data = eval("(" + response.responseText + ")");
			if (data.retCode != 200) {
				error(data.message);
			} else {
				var results = data.results;
				for (var i = 0, j = results.length; i < j; i ++) {
					$("#roles").append($("<option value='" + results[i].code + "'>" + results[i].name + "</option>"));
				}
				$("#roles").select2({
					width:100
				});
			}
		}
	});
}

/**
 * 获取角色权限
 * @param code
 */
function getAuths(code) {
	$("input[type=checkbox]").attr("checked", false);
	var params = {};
	params["code"] = code;
	$.ajax({
		type : "POST",
		url : WEB_URL + "/views/system/role_auths.json",
		async : true,
		data : params,
		complete : function(response) {
			var data = eval("(" + response.responseText + ")");
			if (data.retCode != 200) {
				error(data.message);
			} else {
				var results = data.results;
				if (results != null) {
					for (var i = 0, j = results.length; i < j; i ++) {
						var code = results[i].authCode;
						if (code.length == 2) {
							$("input[id^=dir_chk_" + code + "]").attr("checked", true);
						} else if (code.length == 4){
							$("input[id^=module_chk_" + code + "]").attr("checked", true);
						} else if (code.length == 6){
							$("input[id^=func_chk_" + code + "]").attr("checked", true);
						}
					}
				}
			}
		}
	});
}

/**
 * 查询
 * @param params
 */
function search(params) {
	if (!params)
		params = {};
	//从第一页开始搜索
	grid.setCurrPage(1);
	grid.reload(params);
} 

/**
 * 操作
 */
function operate() {
	var roleName = $("#roleName").val();
	if (isEmpty(roleName)) {
		error("角色名称不能为空");
		return;
	}
	var params = {};
	params["name"] = roleName;
	
	var _url = WEB_URL + "/views/system/role_add.json";
	var isUpdate = false;
	if (!isEmpty(id)) { //修改
		params["id"] = id;
		isUpdate = true;
		_url = WEB_URL + "/views/system/role_edit.json";
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
				clear(true);
				if (isUpdate) {
					success(MSG_SUCCESS_UPDATE);
				} else {
					success(MSG_SUCCESS_ADD);
				}
			}
		}
	});
}

function showAdd() {
	art.dialog({
		id : "a_r",
	    content: document.getElementById('roleForm')
	});
}

/**
 * 清除
 */
function clear(isSearch) {
	$("#roleName").val("");
	$("#operateBtn").html("添加");
	closeBox("a_r");
	if (isSearch)
		search(null);
}

/**
 * 全选/全不选
 */
function check(obj, chidrens) {
	if ($(obj).attr("checked")) { //全选
		$(chidrens).attr("checked", true);
	} else {
		$(chidrens).attr("checked", false);
	}
}

/**
 * 全选/全不选
 */
function checkAll() {
	var isAll = true;
	$("input[type=checkbox][id!='auth_chk_all']").each(function(){
		if (!$(this).attr("checked")) {
			isAll = false;
			return;
		}
	});
	$("#auth_chk_all").attr("checked", isAll);
}

/**
 * 展开/折叠
 * @param chidrens
 */
function fade(chidrens) {
	if (chidrens.is(":hidden")) {
		chidrens.fadeIn();
	} else {
		chidrens.fadeOut();			
	}
}

/**
 * 保存
 */
function save() {
	var role = $("#roles").select2("val");
	if (isEmpty(role)){
		warnning("请选择角色！");
		return;
	}
	var codes = [];
	$("input[type=checkbox][id!='auth_chk_all']").each(function(){
		if ($(this).attr("checked")) {
			codes.push($(this).attr("code"));
		}
	});
	var params = {};
	params["role"] = role;
	params["codes"] = codes.toString();
	$.ajax({
		type : "POST",
		url : WEB_URL + "/views/system/role_auth_setting.json",
		async : true,
		data : params,
		complete : function(response) {
			var data = eval("(" + response.responseText + ")");
			if (data.retCode != 200) {
				error(data.message);
			} else {
				success("角色权限设置成功");
			}
		}
	});
}


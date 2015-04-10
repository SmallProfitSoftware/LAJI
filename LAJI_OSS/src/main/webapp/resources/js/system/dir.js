var id = null, pid = null;
var selectNode;
$(document).ready(function() {
	ztree_setting["async"].url = WEB_URL + "/views/system/dir_tree.json";
	//加载树节点
	zTreeObj = $.fn.zTree.init($("#dirTree"), ztree_setting);
	//显示添加框
	$("#addBtn").click(function(){
		showAdd(null);
	});
	//保存
	$("#opBtn").click(save);
	$("#cancelBtn").click(function(){
		closeBox("a_d");
	});
});

/**
 * 节点过滤
 * @param treeId
 * @param parentNode
 * @param childNodes
 * @returns
 */
function dataFilter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	for (var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

/**
 * 异步加载成功后对节点添加样式
 * @param event
 * @param treeId
 * @param treeNode
 * @param msg
 */
function onAsyncSuccess(event, treeId, treeNode, msg) {
	var nodes = zTreeObj.getNodes();
	if (nodes != null && "undefined" != typeof (nodes)) {
		for (var i = 0, j = nodes.length; i < j; i++) {
			var curr_nodes = nodes[i];
			addClass(curr_nodes);
		}
	}
}

/**
 * 节点添加样式
 * @param treeNode
 * @param isChildNode
 */
function addClass(treeNode, isChildNode) {
	var spanObj = $("#" + treeNode.tId + "_switch");
	var liObj = $("#" + treeNode.tId);
	$(liObj).css("position", "relative").css("margin", "10px 0");
	if (!treeNode.isLastNode || !isChildNode) {
		$(liObj).css("border-bottom", "1px #ccc dashed").css("padding-bottom",
				"5px");
	}
	var urlSpan = $("<span></span>");
	urlSpan.attr("id", "url_" + treeNode.id);
	urlSpan.css("color", "#07b").css("cursor", "pointer").css("text-align", "left").css("position", "absolute");
	if (isChildNode) {
		urlSpan.css("left", "697px");
	} else {
		urlSpan.css("left", "715px");
	}
	urlSpan.html(treeNode.url);
	spanObj.before(urlSpan);
}

/**
 * 创建按钮
 * @param treeNodeId
 * @param id
 * @param title
 * @returns
 */
function createBtn(treeNodeId, id, title) {
	var btn = $("<span></span>");
	btn.attr("id", id + treeNodeId).attr("title", title).attr("onfocus", "this.blur();");
	btn.css("color", "#07b").css("cursor", "pointer").css("margin-left", "20px");
	btn.html(title);
	return btn;
}

/**
 * 节点悬浮效果
 * @param treeId
 * @param treeNode
 */
function addHoverDom(treeId, treeNode) {
	var spanObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#updateBtn_" + treeNode.id).length > 0)
		return;
	/*var addBtn = createBtn(treeNode.id, "addBtn_", "添加下级");
	var updateBtn = createBtn(treeNode.id, "updateBtn_", "修改");
	var delBtn = createBtn(treeNode.id, "delBtn_", "删除");
	var btn = addBtn.prop("outerHTML") + updateBtn.prop("outerHTML") + delBtn.prop("outerHTML");
	if (treeNode.id.length != 2) {
		btn = updateBtn.prop("outerHTML") + delBtn.prop("outerHTML");
	}*/
	//权限控制
	var addBtn = $("#a_s");
	var updateBtn = $("#u_s");
	var delBtn = $("#d_s");
	var btn = "";
	if(addBtn && treeNode.id.length == 2) {
		var cloneA = $(addBtn).clone(false);
		cloneA.attr("id", "addBtn_" + treeNode.id).attr("title", "添加下级");
		cloneA.html("添加下级");
		btn += cloneA.prop("outerHTML");
	}
	if(updateBtn) {
		var cloneU = $(updateBtn).clone(false);
		cloneU.attr("id", "updateBtn_" + treeNode.id).attr("title", "修改");
		cloneU.html("修改");
		btn += cloneU.prop("outerHTML");
	}
	if(delBtn) {
		var cloneD = $(delBtn).clone(false);
		cloneD.attr("id", "delBtn_" + treeNode.id).attr("title", "删除");
		cloneD.html("删除");
		btn += cloneD.prop("outerHTML");
	}
	spanObj.after(btn);
	//添加
	if (addBtn)
		$("#addBtn_" + treeNode.id).bind("click", function() {
			id = null;
			pid = treeNode.id;
			selectNode = treeNode;
			showAdd(treeNode);
		});
	//修改
	$("#updateBtn_" + treeNode.id).bind("click", function() {
		id = treeNode.id;
		pid = null;
		selectNode = treeNode;
		showEdit(treeNode);
	});
	//删除
	$("#delBtn_" + treeNode.id).bind("click", function() {
		id = treeNode.id;
		pid = null;
		selectNode = treeNode;
		showDel(treeNode);
	});
};

/**
 * 移开清除悬浮效果
 * @param treeId
 * @param treeNode
 */
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.id).unbind().remove();
	$("#updateBtn_" + treeNode.id).unbind().remove();
	$("#delBtn_" + treeNode.id).unbind().remove();
}

/**
 * 节点收起
 * @param event
 * @param treeId
 * @param treeNode
 */
function onCollapse(event, treeId, treeNode) {
	var nodes = treeNode.children;
	if (nodes != null && "undefined" != typeof(nodes)){
		for (var i = 0,  j = nodes.length; i < j; i ++){
			var curr_nodes = nodes[i];
			removeClass(curr_nodes, true);
		}
	}
}

/**
 * 节点展开
 * @param event
 * @param treeId
 * @param treeNode
 */
function onExpand(event, treeId, treeNode) {
	var nodes = treeNode.children;
	if (nodes != null && "undefined" != typeof(nodes)){
		for (var i = 0,  j = nodes.length; i < j; i ++){
			var curr_nodes = nodes[i];
			addClass(curr_nodes, true);
		}
	}
}

/**
 * 移除样式
 * @param treeNode
 * @param isChildNode
 */
function removeClass(treeNode, isChildNode){
	$("#url_" + treeNode.id).remove();
}

/**
 * 添加目录
 */
function showAdd(treeNode) {
	resertForm();
	//穿越框架锁屏
	//var throughBox = art.dialog.through;
	var title = "添加一级目录";
	if (treeNode)
		title = "添加'" + treeNode.name + "'下的模块";
	art.dialog({
		id : "a_d",
		title : title,
		lock: true,
	    content: document.getElementById('dialog')
	});
}

/**
 * 编辑
 * @param treeNode
 */
function showEdit(treeNode) {
	//穿越框架锁屏
	//var throughBox = art.dialog.through;
	art.dialog({
		id : "a_d",
		title : "修改'" + treeNode.name + "'",
		lock: true,
	    content: document.getElementById('dialog')
	});
	fillForm(treeNode);
}

/**
 * 删除
 * @param treeNode
 */
function showDel(treeNode) {
	var throughBox = art.dialog.through;
	throughBox({
		id : "a_d",
		title : "删除目录",
		content : "您确定要删除'" + treeNode.name + "'目录? (将同时删除该目录下的所有子目录)",
		lock: true,
		ok : del,
		cancel: true
	});
}

/**
 * 填充表单
 * @param node
 */
function fillForm(node) {
	$("#dir_str").val(node.str);
	$("#dir_name").val(node.name);
	$("#dir_url").val(node.url);
	$("#opBtn").val("修改");
}

/**
 * 重置表单
 */
function resertForm() {
	$("#dir_str").val("");
	$("#dir_name").val("");
	$("#dir_url").val("");
	$("#opBtn").val("添加");
}

/**
 * 新增
 */
function save() {
	var str = $("#dir_str").val();
	var name = $("#dir_name").val();
	var url = $("#dir_url").val();
	if (isEmpty(name)) {
		warnning("目录名称不能为空！");
		return;
	}
	if (isEmpty(str)) {
		warnning("目录字符串不能为空！");
		return;
	} else if (!isPattern(/^[a-zA-Z_]+$/, str)) {
		warnning("目录字符串只能是下划线或者字母！");
		return;
	}
	if (isEmpty(url)) {
		warnning("目录URL不能为空！");
		return;
	} 
	var params = {};
	params["str"] = str;
	params["name"] = name;
	params["url"] = url;
	params["pid"] = pid;
	//新增操作
	var _url = WEB_URL + "/views/system/dir_add.json";
	//修改操作
	var isUpdate = false;
	if (!isEmpty(id)) {
		params["id"] = id;
		_url = WEB_URL + "/views/system/dir_edit.json";
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
				closeBox("a_d");
				if (isUpdate) {
					success(MSG_SUCCESS_UPDATE);
					selectNode.name = name;
					selectNode.str = str;
					selectNode.url = url;
					zTreeObj.updateNode(selectNode);
				} else {
					success(MSG_SUCCESS_ADD);
					console.log(selectNode);
					var nodes = zTreeObj.addNodes(selectNode, {id:data.message, name:name, str:str, url:url, pId:pid});
					nodes = eval(nodes);
					addClass(nodes[0]);
				}
			}
		}
	});
}

/**
 * 删除
 */
function del() {
	var params = {};
	params["id"] = id;
	$.ajax({
		type : "POST",
		url : WEB_URL + "/views/system/dir_del.json",
		async : true,
		data : params,
		complete : function(response) {
			var data = eval("(" + response.responseText + ")");
			if (data.retCode != 200) {
				error(data.message);
			} else {
				success(MSG_SUCCESS_DEL);
				zTreeObj.removeNode(selectNode);
			}
		}
	});
}

function onClick(e, treeId, treeNode) {}



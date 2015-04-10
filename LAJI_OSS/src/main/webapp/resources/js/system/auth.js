$(document).ready(function() {
	//列表
	list();
	//权限树
	ztree_setting["async"].url = WEB_URL + "/views/system/auth_tree.json";
	//加载树节点
	zTreeObj = $.fn.zTree.init($("#tree"), ztree_setting);
	
	$("select").select2({
		width:100
	});
	$("select").change(function(){
		search();
	});
	
	//查询
	$("#searchBtn").click(function(){
		search();
	});
	
	$("#uploadBtn").click(showImport);
});

/**
 * 列表
 */
function list() {
	pageSize = 10;
	grid = grid("auth_tab", WEB_URL + "/views/system/auth_list.json", null, "pageTarget", "tabCallBack");
}

/**
 * 查询
 * @param params
 */
function search(params) {
	if (!params)
		params = {};
	var content = $("#content").val();
	var userfield = $("#authfield").select2("val");
	params["authfield"] = userfield;
	params["content"] = content;
	//从第一页开始搜索
	grid.setCurrPage(1);
	grid.reload(params);
} 

/**
 * 导入
 */
function showImport() {
	var selectNodes = zTreeObj.getSelectedNodes();
	if (selectNodes.length == 0 || selectNodes[0].id.length != 4) {
		warnning("请选择二级模块节点");
		return;
	}
	_showImport(selectNodes[0]);
}

function _showImport(node) {
	art.dialog({
		id : "as_d",
		title : "导入[" + node.name + "]新增权限",
		lock: true,
	    content: document.getElementById('upload'),
	    ok : function(){
	    	var f = $("#file").val();
	    	if (isEmpty(f)){
	    		warnning("请选择需要导入的文件");
	    		return false;
	    	}
	    	$.ajaxFileUpload({
	    		url : WEB_URL + "/views/system/auth_import.json",
	    		secureuri : false,
	    		fileElementId : 'file',
	    		dataType : 'json', 
	    		data : {"pid" : node.id},
	    		complete : function(response, status){
	    			var responseText = response.responseText;
	    			if (responseText.indexOf(">") != -1 && (responseText.indexOf("PRE") != -1 || responseText.indexOf("pre") != -1)){
	    				responseText = responseText.substring(responseText.indexOf(">") + 1, responseText.lastIndexOf("<"));
	            	}
	    			var data = eval("(" + responseText + ")");
	    			if (data.retCode != 200) {
	    				error(data.message);
	    			} else {
	    				search();
	    				closeBox("as_d");
	    				success("成功导入" + data.message + "条权限");
	    			}
	    		}
	    	});
	    	return false;
	    },
	    cancel: true
	});
}

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
		//第一个默认展开
		if (i == 0)
			childNodes[i].open = true;
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
function onAsyncSuccess(event, treeId, treeNode, msg) {}

/**
 * 节点收起
 * @param event
 * @param treeId
 * @param treeNode
 */
function onCollapse(event, treeId, treeNode) {}

/**
 * 节点展开
 * @param event
 * @param treeId
 * @param treeNode
 */
function onExpand(event, treeId, treeNode) {}

/**
 * 点击节点
 * @param e
 * @param treeId
 * @param treeNode
 */
function onClick(e, treeId, treeNode) {
	zTreeObj.expandNode(treeNode, !treeNode.open, true, true);
	var params = {};
	params["pcode"] = treeNode.id;
	search(params);
}

function addHoverDom(treeId, treeNode) {}

function removeHoverDom(treeId, treeNode) {}

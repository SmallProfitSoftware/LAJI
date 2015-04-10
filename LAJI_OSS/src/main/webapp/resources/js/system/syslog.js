var grid;
$(document).ready(function() {
	//列表
	list();
	//下拉框
	$("#status").select2({
		width : 100
	});
	$("#status").change(function(){
		search();
	});
	//查询
	$("#searchBtn").click(function(){
		search();
	});
});

/**
 * 列表
 */
function list() {
	grid = grid("syslog_tab", WEB_URL + "/views/system/syslog_list.json", null, "pageTarget", "tabCallBack");
}

/**
 * 查询
 * @param params
 */
function search(params) {
	if (!params)
		params = {};
	var operator = $("#operator").val();
	var status = $("#status").select2("val");
	params["operator"] = operator;
	params["status"] = status;
	//从第一页开始搜索
	grid.setCurrPage(1);
	grid.reload(params);
} 

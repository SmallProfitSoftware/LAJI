var MSG_SUCCESS_ADD = "添加成功";
var MSG_SUCCESS_UPDATE = "修改成功";
var MSG_SUCCESS_DEL = "删除成功";
var MSG_SUCCESS_RESET = "重置成功";
var MSG_SUCCESS_LOCK = "锁定成功";
var MSG_SUCCESS_UNLOCK = "解锁成功";

var page = 1;
var pageSize = 15;

$(document).keyup(function(event){
  if(event.keyCode == 13){
	  search();
  }
});

$("input").keyup(function(event){
  if(event.keyCode == 13){
	  search();
  }
});

//artDialog全局默认配置
(function (config) {
    config['lock'] = true;
    config['fixed'] = true;
    config['resize'] = false;
    config['okVal'] = '确定';
    config['cancelVal'] = '取消';
    config['background'] = '#000';
    config['opacity'] = 0.3;
})(art.dialog.defaults);

/**
 * 非空校验
 * @param val
 * @returns {Boolean}
 */
function isEmpty(val) {
	return val == null || "" == val;
}

/**
 * 比较2个值
 * @param val1
 * @param val2
 * @returns {Boolean}
 */
function eq(val1, val2) {
	return val1 == val2;
}

/**
 * 正则表达式
 * @param pattern
 * @param val
 * @returns
 */
function isPattern(pattern, val) {
	return pattern.test(val);
}

/**
 * 长度
 * @param val
 * @param len
 * @returns {Boolean}
 */
function lenOut(val, len) {
	return val.length >= len;
}

/**
 * 设置值到cookie中
 * @param key
 * @param val
 */
function setCookie(key, val) {
	$.cookie(key, val);
}

/**
 * 设置值到cookie中(包含有效期)
 * @param key
 * @param val
 * @param day
 */
function setCookie(key, val, day) {
	$.cookie(key, val, {
		path: "/", expiress: day 
	});
}

/**
 * 设置值
 * @param id
 * @param val
 */
function setVal(id, val) {
	$("#" + id).val(val);
}

/**
 * 提示
 * @param id
 * @param target
 * @param x
 * @param y
 * @returns
 */
function tooltip(id, target, x, y) {
	return $('#' + id).jBox('Tooltip', {
	    target: target,
	    position: {
	        x: x,
	        y: y
	    },
	    outside: 'x'
	});
}

/**
 * 消息提示
 * @param content
 */
function success(content) {
	showBox("消息", content, "succeed");
}

/**
 * 警告提示
 * @param content
 */
function warnning(content) {
	showBox("警告", content, "warning");
}

/**
 * 错误提示
 * @param content
 */
function error(content) {
	showBox("错误", content, "error");
}

/**
 * 提示框
 * @param content
 */
function showBox(title, content, icon) {
	var params = {};
	if (!isEmpty(icon)) {
		params["icon"] = icon;
	}
	params["title"] = title;
	params["content"] = content;
	params["ok"] = true;
	var throughBox = art.dialog.through;
	throughBox(params);
}

/**
 * 关闭提示框
 * @param id
 */
function closeBox(id) {
	if (art.dialog.list[id])
		art.dialog.list[id].close();
}

/**
 * 表哥单元格样式
 */
function tabCallBack() {
	$('.tablelist tbody tr:odd').addClass('odd');
}

/**
 * 真假
 * @param val
 * @returns {Boolean}
 */
function isTrue(val) {
	return "1" == val;
}

/**
 * 列表
 */
function grid(tabId, url, params, pageId, callback) {
	return $("#" + tabId).easyTable({
		type : "POST",  		 //请求类型，默认POST
		url : url,			 //ajax请求url
		showSerNum :  true,      //是否显示序号
		usePager : true,		 //使用分页
		params : params,
		pager : {
			pageTarget : "#" + pageId, //分页显示位置
			page : page,				 //当前页
			pageSize : pageSize			 //每页显示条数
		},
		callback : callback
	});
}

/**
 * 排序
 * @param field
 */
function sort(field) {
	var sort = "ASC";
	var params = {};
	params["dir"] = field;
	params["sort"] = sort;
	search(params);
}

/**
 * 
 * @param code
 * @returns {Boolean}
 */
function isSuper(code) {
	return "R001" == code;
}
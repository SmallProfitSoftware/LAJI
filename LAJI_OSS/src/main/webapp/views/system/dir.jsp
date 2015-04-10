<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/views/common/taglibs.jsp"%>
<%@include file="/views/common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<title>${oss_title }${oss_version }</title>
<link href="${ctx }/resources/css/ztree.css" rel="stylesheet">
<script type="text/javascript" src="${ctx }/resources/js/system/dir.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/common/ztree.config.js"></script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="${ctx }/views/index.jsp">首页</a></li>
			<li><a href="#">目录管理</a></li>
		</ul>
	</div>
	
	<div style="display: none;">
		<c:if test="${auths_map['f_dir_add']}">
			<span id="a_s" onfocus="this.blur();" style="color:#07b; cursor:pointer; margin-left:20px; " ></span>
		</c:if>
		<c:if test="${auths_map['f_dir_edit']}">
			<span id="u_s" onfocus="this.blur();" style="color:#07b; cursor:pointer; margin-left:20px; " ></span>
		</c:if>
		<c:if test="${auths_map['f_dir_del']}">
			<span id="d_s" onfocus="this.blur();" style="color:#07b; cursor:pointer; margin-left:20px; " ></span>
		</c:if>
	</div>
	
	<div class="rightinfo">
		<c:if test="${auths_map['f_dir_add']}">
			<div class="tools">
				<ul class="toolbar">
					<li class="click" id="addBtn"><span><img src="${ctx }/resources/images/t01.png"/></span>添加目录</li>
				</ul>
			</div>
		</c:if>
		<table class="tablelist">
			<thead>
				<tr>
					<th style="padding-left: 10px; width: 700px;">目录名称</th>
					<th>URL</th>
				</tr>
			</thead>
		</table>
		<div id="dirTree" class="ztree" style="padding-top: 10px;"></div>
	</div>
	
	<div class="formbody" id="dialog" style="display: none;">
	    <ul class="forminfo">
	    	<li><label>目录名称<b>*</b></label><input id="dir_name" type="text" class="dfinput" style="width: 200px;" title="请输入目录名称！" placeholder="请输入目录名称！"/><i>不能超过25个字符</i></li>
		    <li><label>目录字符串<b>*</b></label><input id="dir_str" type="text" class="dfinput" style="width: 200px;" maxlength="" title="请输入目录字符串！" placeholder="请输入目录字符串！"/><i>不能超过25个字符</i></li>
		    <li><label>目录URL<b>*</b></label><input id="dir_url" type="text" class="dfinput" title="请输入目录URL！" placeholder="请输入目录URL！"/><i>不能超过40个字符</i></li>
		    <li><label>&nbsp;</label><input type="button" id="opBtn" class="btn" value="添加" style="width: 96px;"/><input type="button" id="cancelBtn" class="cancel" value="取消" style="margin-left: 10px;"></li>
	    </ul>
   	</div>
</body>
</html>
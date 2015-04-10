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
<script type="text/javascript" src="${ctx }/resources/js/system/auth.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/common/ztree.config.js"></script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="${ctx }/views/index.jsp">首页</a></li>
			<li><a href="#">权限管理</a></li>
		</ul>
	</div>
	<div id="upload" style="display: none">
		<form method="post" enctype="multipart/form-data">
			<input type="file" title="选择文件" id="file" name="file"/>
			<a href="${ctx }/template/auth.xls" style="float: left; color:blue;">下载标准模板</a>
		</form>
	</div>
	<div class="rightinfo">
		<div class="tabson">
			<ul class="seachform" style="height: 30px;">
				<li>
					<div class="vocation">
						<select id="authfield">
							<option value="code">权限编码</option>
							<option value="str">权限字符串</option>
							<option value="name">权限名称</option>
							<option value="url">权限URL</option>
						</select>
					</div></li>
				<li><input id="content" type="text"
					class="scinput" style="height: 26px; line-height: 26px;" placeholder="请输入关键字查询"/>
				</li>
				<li><label>&nbsp;</label><input name="" type="button"
					class="scbtn" value="查询" style="height: 26px;" id="searchBtn"/></li>
				<c:if test="${auths_map['f_auth_import']}">
					<ul id="uploadBtn" class="toolbar1" style="cursor: pointer; "><li><span><img src="${ctx }/resources/images/ico02.png" width="24" height="24" /></span>导入</li></ul>
				</c:if>
			</ul>
		</div>
		<div style="width: 13%; float: left; border:1px #ccc solid; height: 100%;">
			<div id="tree" class="ztree" style="padding: 0px;"></div>
		</div>
		<div style="width: 86%; overflow: hidden; float: left; margin-top:-1px;">
			<!-- 表格 -->
			<div style="margin-left:10px; padding: 0">
				<table class="tablelist" id="auth_tab">
					<thead>
						<tr>
							<!-- <th><input name="" type="checkbox" value="" checked="checked" /></th> -->
							<th width="100" field="code">权限编码</th>
							<th width="100" field="str">权限字符串</th>
							<th width="100" field="name">权限名称</th>
							<th width="100" field="url">权限URL</th>
						</tr>
					</thead>
				</table>
			</div>
			<!-- 分页 -->
			<div class="easy-page clearfix" style="margin-left:10px;" id="pageTarget"></div>
		</div>
	</div>
	
</body>
</html>
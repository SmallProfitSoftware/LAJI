<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/views/common/taglibs.jsp"%>
<%@include file="/views/common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<title>${oss_title }${oss_version }</title>
<script type="text/javascript"
	src="${ctx }/resources/js/system/syslog.js"></script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="${ctx }/views/index.jsp">首页</a></li>
			<li><a href="#">日志管理</a></li>
		</ul>
	</div>
	<div class="rightinfo">
		<div class="tabson">
			<ul class="seachform" style="height: 30px;">
				<li><label style="line-height: 26px;">操作账号：</label><input id="operator" type="text"
					class="scinput" style="height: 26px; line-height: 26px;" placeholder="请输入操作账号查询"/>
				</li>
				<li>
					<div class="vocation">
						<select id="status">
							<option value="">全部状态</option>
							<option value="success">成功</option>
							<option value="fail">失败</option>
						</select>
					</div></li>
				<li><label>&nbsp;</label><input name="" type="button"
					class="scbtn" value="查询" style="height: 26px;" id="searchBtn"/></li>
			</ul>
		</div>

		<!-- 表格 -->
		<div>
			<table class="tablelist" id="syslog_tab">
				<thead>
					<tr>
						<!-- <th><input name="" type="checkbox" value="" checked="checked" /></th> -->
						<th width="100" field="operator">操作人</th>
						<th width="100" field="module">操作目录</th>
						<th width="80" field="act">动作</th>
						<th width="150" field="operateDate">操作时间<a
							href="javascript:sort('operateDate');"><i class="sort"><img
									src="${ctx }/resources/images/px.gif" /></i></a></th>
						<th field="message">内容</th>
					</tr>
				</thead>
			</table>
		</div>
		<!-- 分页 -->
		<div class="easy-page clearfix" id="pageTarget"></div>
	</div>
</body>
</html>
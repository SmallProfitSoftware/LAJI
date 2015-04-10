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
	src="${ctx }/resources/js/system/user.js"></script>
</head>
<body>

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="${ctx }/views/index.jsp">首页</a></li>
			<li><a href="#">账号管理</a></li>
		</ul>
	</div>
	<div style="display: none;">
		<c:if test="${auths_map['f_user_add']}">
			<span id="a_s"></span>
		</c:if>
		<c:if test="${auths_map['f_user_edit']}">
			<span id="u_s"></span>
		</c:if>
	</div>
	
	<!--  -->
	<div style="display: none;">
		<a href="javascript:void(0);" class="tablelink" id="viewBtn">查看</a>
		<c:if test="${auths_map['f_user_lock']}">
			<a href="javascript:void(0);" class="tablelink" style="margin-left: 10px;" id="lockBtn">锁定</a>
		</c:if>
		<c:if test="${auths_map['f_user_pwd_reset']}">
			<a href="javascript:void(0);" class="tablelink" style="margin-left: 10px;" id="resetBtn">密码重置</a>
		</c:if>
	</div>

	<div class="formbody">
		<div id="usual1" class="usual">
			<div class="itab">
				<ul>
					<li><a href="#tab1" class="selected">列表</a></li>
					<li><a href="#tab2">查看</a></li>
				</ul>
			</div>

			<div id="tab1" class="tabson">
				<ul class="seachform" style="height: 30px;">
					<li>
						<div class="vocation">
							<select id="roles">
								<option value="">所有角色</option>
					    		<c:forEach var="role" items="${all_roles }">
					    			<option value="${role.code }">${role.name }</option>
					    		</c:forEach>
							</select>
							<div class="line-box"></div>
							<select id="userfield">
								<option value="userId">帐号ID</option>
								<option value="userName">帐号名称</option>
							</select>
							<input id="content" type="text"
						class="scinput" style="height: 26px; line-height: 26px;" placeholder="请输入关键字查询"/>
						</div></li>
					<li><label>&nbsp;</label><input name="" type="button"
						class="scbtn" value="查询" style="height: 26px;" id="searchBtn"/></li>
				</ul>
				<!-- 表格 -->
				<div>
					<table class="tablelist" id="user_tab">
						<thead>
							<tr>
								<!-- <th><input name="" type="checkbox" value="" checked="checked" /></th> -->
								<th width="100" field="userId" render="userTD">账号ID</th>
								<th width="100" field="roleName">所属角色</th>
								<th width="100" field="userName">账号名称</th>
								<th width="150" field="createDate">创建时间</th>
								<th width="80" field="loginCount">登录次数</th>
								<th width="150" field="lastLoginDate">最后登录时间</th>
								<th width="80" field="lastLoginIp">最后登录IP</th>
								<th render="operateTD">操作</th>
							</tr>
						</thead>
					</table>
				</div>
				<!-- 分页 -->
				<div class="easy-page clearfix" id="pageTarget"></div>
			</div>

			<div id="tab2" class="tabson">
				<div class="formbody">
				 	<div class="formtitle"><span>基本信息</span></div>
				    <ul class="forminfo">
				    <li><label>账号ID<b>*</b></label><input id="userId" type="text" class="dfinput" placeholder="请输入账号ID" title="请输入账号ID" maxlength="32"/><i>只能输入数字或者字母，不能超过32个字符</i></li>
				    <li><label>账号名称<b>*</b></label><input id="userName" type="text" class="dfinput" placeholder="请输入账号名称" title="请输入账号名称" maxlength="16"/><i>不能超过16个字符</i></li>
				    <li><label>所属角色<b>*</b></label>
				    	<select id="role" name="role">
				    		<c:forEach var="role" items="${all_roles }">
				    			<option value="${role.code }">${role.name }</option>
				    		</c:forEach>
						</select>
				    </li>
				    <c:if test="${auths_map['f_user_add'] or auths_map['f_user_edit']}">
				    	<li><label>&nbsp;</label><input id="operateBtn" type="button" class="btn" value="添加"/></li>
				    </c:if>
				    </ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

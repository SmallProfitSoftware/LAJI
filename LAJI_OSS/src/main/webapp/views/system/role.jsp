<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/views/common/taglibs.jsp"%>
<%@include file="/views/common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<title>${oss_title }${oss_version }</title>
<link href="${ctx }/resources/css/auth.css" rel="stylesheet">
<script type="text/javascript" src="${ctx }/resources/js/system/role.js"></script>
</head>
<body>

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="${ctx }/views/index.jsp">首页</a></li>
			<li><a href="#">角色管理</a></li>
		</ul>
	</div>

	<!--  -->
	<div style="display: none;">
		<a href="javascript:void(0);" class="tablelink" id="viewBtn">查看</a> 
	</div>
	
	<div id="roleForm" class="tabson" style="display: none;">
		<div class="formbody">
			<ul class="forminfo">
				<li><label>角色名称<b>*</b></label><input id="roleName"
					type="text" class="dfinput" placeholder="请输入角色名称" title="请输入角色名称"
					maxlength="16" style="width: 250px;" /><i>不能超过16个字符</i></li>
				<c:if test="${auths_map['f_role_add'] or auths_map['f_role_edit']}">	
					<li><label>&nbsp;</label><input id="operateBtn" type="button"
						class="btn" value="添加" style="width: 96px;"/><input type="button" id="cancelBtn" class="cancel" value="取消" style="margin-left: 10px;"></li>
				</c:if>
			</ul>
		</div>
	</div>

	<div class="formbody">
		<div id="usual1" class="usual">
			<div class="itab">
				<ul>
					<li><a href="#tab1" class="selected">列表</a></li>
					<li><a href="#tab2">权限分配</a></li>
				</ul>
			</div>

			<div id="tab1" class="tabson">
				<c:if test="${auths_map['f_role_add']}">
					<div class="tools">
						<ul class="toolbar">
							<li class="click" id="addBtn"><span><img src="${ctx }/resources/images/t01.png"/></span>添加</li>
						</ul>
					</div>
				</c:if>
				<!-- 表格 -->
				<div>
					<table class="tablelist" id="role_tab">
						<thead>
							<tr>
								<!-- <th><input name="" type="checkbox" value="" checked="checked" /></th> -->
								<th width="100" field="code">角色编码</th>
								<th width="100" field="name">角色名称</th>
								<th render="usersTD">角色人员</th>
							</tr>
						</thead>
					</table>
				</div>
				<!-- 分页 -->
				<div class="easy-page clearfix" id="pageTarget"></div>
			</div>

			<div id="tab2" class="tabson">
				<div class="formbody">
					<div class="formtitle">
						<span>权限分配</span>
					</div>

					<div class="qxfb-box" style="padding: 0px;">
						<c:if test="${auths_map['f_role_auth_setting']}">
							<li><label>&nbsp;</label><input name="saveBtn" type="button"
							class="btn" value="保存" /></li>
						</c:if>
						<h2 style="padding-top: 10px;">
							<b>设置角色权限</b><span style="margin-left: 0px; color: #f60;">(双击展开/折叠)</span>
							<span style="margin-left:20px;">角色名称：</span>
							<select id="roles">
								<option value="">请选择角色</option>
							</select>
						</h2>
						<ul class="qxfb-ul">
							<li><input name="" type="checkbox" value=""
								id="auth_chk_all"/><span>全选</span></li>
							<c:forEach var="dir" items="${dir_auths}">
								<li><input name="" type="checkbox"
									id="dir_chk_${dir.code }" code="${dir.code }" /><span id="dir_span_${dir.code }" code="${dir.code }" style="cursor: pointer;"><b>${dir.name}</b></span></li>
									<div id="dir_${dir.code }">
										<c:forEach var="module" items="${dir.auths}">
											<li class="list-01"><input name="" type="checkbox"
										id="module_chk_${module.code }" code="${module.code }" /><span id="module_span_${module.code }" code="${module.code }" style="cursor: pointer;">${module.name }</span></li>
											<div id="module_${module.code }">
												<c:forEach var="func" items="${module.auths}" varStatus="status">
													<c:choose>
														<c:when test="${status.count % 5 == 1}"> 
															<li class="list-02">
																<div><input name="" type="checkbox"
										id="func_chk_${func.code }" code="${func.code }" /><span>${func.name }</span></div>
														</c:when>
														<c:otherwise>
		    					 							<div><input name="" type="checkbox"
										id="func_chk_${func.code }"	code="${func.code }" /><span>${func.name }</span></div>
		    											 </c:otherwise>
													</c:choose>
													<c:if test="${status.count % 5 == 0 || status.last}"> 
							    						</li>
							   						</c:if>
												</c:forEach>
											</div>
										</c:forEach>
									</div>
							</c:forEach>
						</ul>
						<c:if test="${auths_map['f_role_auth_setting']}">
							<li style="padding-top: 10px;"><label>&nbsp;</label><input name="saveBtn" type="button"
							class="btn" value="保存" /></li>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

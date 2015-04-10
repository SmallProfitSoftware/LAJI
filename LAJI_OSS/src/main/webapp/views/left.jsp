<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/views/common/taglibs.jsp"%>
<%@include file="/views/common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<title>${oss_title }${oss_version }</title>
<script type="text/javascript" src="/resources/js/main/left.js"></script>
</head>
<body style="background: #f0f9fd;">
	<div class="lefttop">
		<span></span>目录
	</div>

	<dl class="leftmenu">
		<c:forEach items="${user_dir_auths }" var="dir">
			<dd>
				<div class="title">
					<span>
						<c:choose>
							<c:when test="${dir.code eq '01' }">
								<img src="${ctx}/resources/images/leftico01.png" />
							</c:when>
							<c:otherwise>
								<img src="${ctx}/resources/images/leftico02.png" />
							</c:otherwise>
						</c:choose>
					</span>${dir.name }
				</div>
				<ul class="menuson">
					<c:forEach items="${user_module_auths }" var="module">
						<c:if test="${fn:substring(module.code, 0, 2) eq dir.code}">
							<li><cite></cite><a href="${ctx}/${module.url }" target="rightFrame">${module.name }</a><i></i></li>
						</c:if>
					</c:forEach>
				</ul>
			</dd>
		</c:forEach>
	</dl>
</body>
</html>
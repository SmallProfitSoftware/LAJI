<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/views/common/taglibs.jsp"%>
<%@include file="/views/common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${oss_title }</title>
<link href="/resources/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/resources/js/jquery-1.8.3.min.js"></script>
<script language="javascript">
	$(function() {
		$('.error').css({
			'position' : 'absolute',
			'left' : ($(window).width() - 490) / 2
		});
		$(window).resize(function() {
			$('.error').css({
				'position' : 'absolute',
				'left' : ($(window).width() - 490) / 2
			});
		})
	});
</script>
</head>

<body style="background: #edf6fa;">
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="${ctx }/main.html">首页</a></li>
			<li><a href="javascript:void(0);">500错误提示</a></li>
		</ul>
	</div>
	<div class="error" style="background:url(/resources/images/500.png) no-repeat;">
		<h2 style="width: 500px;">${ERROR }</h2>
		<div class="reindex">
			<a href="javascript:history.go(-1);" target="_parent">返回上一页</a>
		</div>
	</div>
</body>

</html>

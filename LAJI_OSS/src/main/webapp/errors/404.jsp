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
			<li><a href="javascript:void(0);">404错误提示</a></li>
		</ul>
	</div>
	<div class="error">
		<h2>非常遗憾，您访问的页面不存在！</h2>
		<p>看到这个提示，就自认倒霉吧!</p>
		<div class="reindex">
			<a href="javascript:history.go(-1);" target="_parent">返回上一页</a>
		</div>
	</div>
</body>

</html>

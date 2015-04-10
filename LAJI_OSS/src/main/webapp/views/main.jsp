<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/views/common/taglibs.jsp"%>
<%@include file="/views/common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${oss_title }${oss_version }</title>
<style>
* { padding:0; margin:0; }
html, body { height:100%; border:none 0; }
#iframe { width:100%; height:100%; border:none 0; }
</style>
</head>
<body>
<iframe id="iframe" src="${ctx }/views/iframe.jsp"></iframe>
</body>
</html>

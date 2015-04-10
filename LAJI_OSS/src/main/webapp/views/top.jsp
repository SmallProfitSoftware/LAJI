<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/views/common/taglibs.jsp"%>
<%@include file="/views/common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
<title>${oss_title }${oss_version }</title>
<script type="text/javascript" src="/resources/js/common/time.js"></script>
<script type="text/javascript" src="/resources/js/main/top.js"></script>
<script type="text/javascript">
$(function(){	
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected")
		$(this).addClass("selected");
	});	
});	
</script>


</head>

<body style="background:url(${ctx}/resources/images/topbg.gif) repeat-x;">

    <div class="topleft">
    <a href="main.jsp" target="_parent"><img src="${ctx}/resources/images/logo.png" title="系统首页" /></a>
    </div>
    
    <div class="formbody" id="pwdDialog" style="display: none;">
	    <ul class="forminfo">
	    	<li><label>旧密码<b>*</b></label><input type="text" class="dfinput" style="width: 200px;" title="请输入旧密码！" placeholder="请输入旧密码！" maxlength="32" /></li>
		    <li><label>新密码<b>*</b></label><input type="text" class="dfinput" style="width: 200px;" maxlength="" title="请输入新密码！" placeholder="请输入新密码！" maxlength="32" /></li>
		    <li><label>重复新密码<b>*</b></label><input type="text" class="dfinput" title="请重复输入新密码！" placeholder="请重复输入新密码！" maxlength="32" /></li>
		    <!-- <li><label>&nbsp;</label><input type="button" class="btn" value="添加" style="width: 96px;"/><input type="button" class="cancel" value="取消" style="margin-left: 10px;"></li> -->
	    </ul>
   	</div>
        
    <ul class="nav">
    <%--
    <li><a href="${ctx }/views/system/main.html" target="rightFrame" class="selected"><img src="${ctx}/resources/images/icon01.png" title="垃圾网" /><h2>垃圾网</h2></a></li> 
    <li><a href="default.html" target="rightFrame" class="selected"><img src="${ctx}/resources/images/icon01.png" title="工作台" /><h2>工作台</h2></a></li>
    <li><a href="imgtable.html" target="rightFrame"><img src="${ctx}/resources/images/icon02.png" title="模型管理" /><h2>模型管理</h2></a></li>
    <li><a href="imglist.html"  target="rightFrame"><img src="${ctx}/resources/images/icon03.png" title="模块设计" /><h2>模块设计</h2></a></li>
    <li><a href="tools.html"  target="rightFrame"><img src="${ctx}/resources/images/icon04.png" title="常用工具" /><h2>常用工具</h2></a></li>
    <li><a href="computer.html" target="rightFrame"><img src="${ctx}/resources/images/icon05.png" title="文件管理" /><h2>文件管理</h2></a></li>
    <li><a href="tab.html"  target="rightFrame"><img src="${ctx}/resources/images/icon06.png" title="系统设置" /><h2>系统设置</h2></a></li> --%>
    </ul>
            
    <div class="topright">    
    <ul>
    <%-- <li><span><img src="${ctx}/resources/images/help.png" title="帮助"  class="helpimg"/></span><a href="#">帮助</a></li> --%>
   <!--  <li><a href="#">关于</a></li> -->
    <li><a href="javascript:void(0);" id="changePwd">修改密码</a></li>
    <li><a href="javascript:void(0);" target="_parent" id="exitBtn">退出</a></li>
    </ul>
     
    <div class="user">
    <span>${user.userId }</span>
    <!-- <i>消息</i>
    <b>5</b> -->
    <i id="currTimes"></i>
    </div>    
    
    </div>

</body>
</html>

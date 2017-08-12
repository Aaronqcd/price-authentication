<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>价格认证管理系统</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
<link href="${pageContext.request.contextPath}/css/style_pe.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/mian_pe.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
function loadLeftFrame(id){
}

function updatePass(){ 
	 var url="${pageContext.request.contextPath}/common/inintAction.action" ;
	 window.showModalDialog(url,"","");
}
</script>

<script type="text/javascript">
//SuckerTree Vertical Menu (Aug 4th, 06)
//By Dynamic Drive: http://www.dynamicdrive.com/style/

var menuids=["suckertree1"] //Enter id(s) of SuckerTree UL menus, separated by commas

function buildsubmenus(){
for (var i=0; i<menuids.length; i++){
	if(document.getElementById(menuids[i])){
		var ultags = document.getElementById(menuids[i]).getElementsByTagName("ul");
	    for (var t=0; t<ultags.length; t++){
		    ultags[t].parentNode.getElementsByTagName("a")[0].className="subfolderstyle";
		    ultags[t].parentNode.onmouseover=function(){
		   		this.getElementsByTagName("ul")[0].style.display="block";
	    	};
	   	 	ultags[t].parentNode.onmouseout=function(){
		   		this.getElementsByTagName("ul")[0].style.display="none";
		    };
	    }
	}
  }
}

if (window.addEventListener){
	window.addEventListener("load", buildsubmenus, false);
}
else if (window.attachEvent){
	window.attachEvent("onload", buildsubmenus);
}


$(function(){
	$("#mainframe").load(function(){
		var frame = $("#mainframe");
	    var mainframe = frame[0].contentWindow;
		var dom = mainframe.document;
		if($.browser.msie){//IE浏览器
			$(this).height(dom.body.scrollHeight+30);
		}else{//其他浏览器
			$(this).height(dom.documentElement.offsetHeight);
		}
	});
});
</script>
</head>
<body>
<!--  头部   -->
<div class="top">
	<div class="left">
		<div class="toplogo">
			<img src="${pageContext.request.contextPath}/css/images/toplogo.jpg">
			<p>欢迎 <s:property value="webUser.name"/> <a href="${pageContext.request.contextPath}/login/loginOut.action">退出</a></p>
		</div>

		<div class="nav">
			<div class="box">
				<ul>
					 <li><a href="${pageContext.request.contextPath}/agency/agencyList.action" target="mainframe">首页</a></li>
					<s:iterator value="nodes">
						<li>
						<a <s:if test="url!=null">href="${pageContext.request.contextPath}/${url}" target="mainframe"</s:if><s:else>href="#"</s:else>><s:property value="title"/></a>
						<s:if test="children!=null">
							<ul>
							<s:iterator value="children">
								<li><a <s:if test="url!=null">href="${pageContext.request.contextPath}/${url}" target="mainframe"</s:if><s:else>href="#"</s:else> onclick="loadLeftFrame(<s:property value="id"/>)"><s:property value="title"/></a>
								<s:if test="children!=null">
									<ul>
									<s:iterator value="children">
										<li><a <s:if test="url!=null">href="${pageContext.request.contextPath}/${url}" target="mainframe"</s:if><s:else>href="#"</s:else>><s:property value="title"/></a></li>
									</s:iterator>
									</ul>
								</s:if>
								</li>
							</s:iterator>
							</ul>
						</s:if>
						</li>
					</s:iterator>
				</ul>
			</div>
		</div>
	</div>
	<div class="toppic"><img src="${pageContext.request.contextPath}/css/images/toppic.jpg"></div>
</div>
<!-- top end -->
<div class="clear"></div>
		<iframe width="100%" height="100%" id="mainframe" name="mainframe" src="${pageContext.request.contextPath}/agency/agencyList.action" frameborder="0"></iframe>
</body>
</html>
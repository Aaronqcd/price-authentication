<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/left.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/right.css" />
<jsp:include page="../included/all_js.jsp"></jsp:include>
</head>
<body>
	<div class="clear"></div>
	<div>
	<div class="left_menu" style="width: 100%;height: 100%">
		<div class="left_menubt" style="width: 100%;height: 100%"><p>快捷菜单</p><a href="#">[收缩]</a></div>
		<ul>
			<s:iterator value="result" status="u">
				<li><a href="${pageContext.request.contextPath}${actnId }"><s:property value="nm"/></a></li>
		    </s:iterator>
		</ul>
		</div>
	</div>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>"  target=_self>
    
    <title>My JSP 'changepwdok.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%@ include file="../included/all_css.jsp" %>
	<%@ include file="../included/all_js.jsp" %>
  </head>
  
  <body>
  	<center>
    <div class="boxwenzi"><p>修改密码</p>
	  <table>
		  <tr>
		    <th><img src="images/tick.png" width="16" height="16" />密码修改成功！</th>
		  </tr>
	  </table>
	</div>
	</center>
  </body>
</html>

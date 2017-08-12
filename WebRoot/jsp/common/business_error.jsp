<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信息提示</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style_pe.css" media="screen" />
<style>
ul{ list-style:none;}
</style>
</head>
<body>
<div class="tanchukk">
	<div class="tanchuk2">
	  <div class="tabchuk2_wz">
	  				<s:actionmessage/>
	                <s:actionerror/>
	                <s:fielderror />
	</div>
	</div>
</div>
</body>
</html>

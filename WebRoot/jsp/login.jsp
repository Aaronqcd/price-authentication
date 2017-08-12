<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户登录</title>
<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/css.css" />
<script type="text/javascript">

$(document).ready(function(){
	$("#username").addClass("validate[required,maxSize[20]]");
	$("#password").addClass("validate[required]");
	jQuery("#formId").validationEngine();
	//刷新父窗口
	if(window.parent.location.href != window.location.href){
		parent.location.reload();
	}
	document.getElementById("username").focus();
});

  function login(){
	if (!jQuery("#formId").validationEngine('validate')) {return;}
	document.formId.action="${pageContext.request.contextPath}/login/doLogin.action";
    document.formId.submit();
  }

	$(document).keydown(
		function(event){
			if(event.keyCode == 13){
				login(); //调用登录按钮的登录事件  
				return false;
			}	
		}
	);
</script>
 	
</head>

<body >
<Div class="logo"><img src="${pageContext.request.contextPath}/images//logo.jpg" /></Div>
<div class="login">
<div class="login_pic"><img src="${pageContext.request.contextPath}/images//pic.jpg" /></div>
<div class="login_wz">
<form action="" name="formId" id="formId" method="post">
	<table>
	  <tr>
	    <td>用户名：<s:if test="error_msg!=null && error_msg=='error'"><font color="red">用户名或密码错误</font></s:if></td>
	  </tr>
	  <tr>
	    <td><label>
	      <input name="username" id="username" type="text" class="input1" />
	    </label></td>
	  </tr>
	  <tr>
	    <td>密码：</td>
	  </tr>
	  <tr>
	    <td><input name="password" type="password" id="password"  class="input2"/></td>
	  </tr>
	  <tr>
	    <td align="right"><input name="Submit" type="submit" class="anniu" id="button" value="登 录" onclick="login()"/></td>
	  </tr>
	</table>
	</form>
</div>
</div>
</body>
</html>

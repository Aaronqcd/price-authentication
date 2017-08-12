<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>修改密码</title>
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
	 
  <script language="javascript" type="text/javascript">
 $(document).ready(function(){
		$("#originalPswd").addClass("validate[required,maxSize[18]]");
		$("#pswd").addClass("validate[required,minSize[8],maxSize[18],custom[checkpass]]");
		$("#confirmPswd").addClass("validate[required,equals[pswd]]");
		jQuery("#formId").validationEngine();
	})
	</script>
	</head> 
	<body>
	<h3>修改密码</h3>
		<!--下面内容开始-->
		<div class="box2" id="searchCloseDiv">
		<div class="kuang1" style="width:500px;text-align: center;margin-left: auto;margin-right: auto;">
		<s:form action="changePassWordAction" method="post" namespace="/common" id="formId"> 
			<table>
				<tr>
		    		<th>原&nbsp;&nbsp;密&nbsp;&nbsp;码：<s:password name="oldPwd" cssClass="input" id="originalPswd"></s:password></th>
		    		<td></td>
		    	</tr>
		  		<tr>
		    		<th>新&nbsp;&nbsp;密&nbsp;&nbsp;码：<s:password name="userInfo.password" cssClass="input" id="pswd"></s:password></th>
		    		<td></td>
		    	</tr>
		  		<tr>
		    		<th>确认新密码：<input name="text295" type="password" class="input" id="confirmPswd"/></th>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td><input name="Submit2" type="submit" class="anniu" value="提交">
		    		&nbsp;&nbsp;<input name="Submit252322" type="button" class="anniu" value="返回" onclick="history.back();"></td>
		    	</tr>
		</table>
		</s:form>
		</div>
		</div>
	<div class="clear"></div>
</body>
</html>
